package profile;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import profile.util.DataConnect;
import profile.util.JsfUtil;
import profile.util.PostValidator;
import profile.util.SessionUtils;

@Named("TheMessageController")
@SessionScoped
public class TheMessageController implements Serializable {

    private UserPlusMessage current;
    private DataModel items = null;
    private String emailInput;
    private String subjectInput;
    private String contentInput;

    public TheMessageController() {
    }

    public List<UserPlusMessage> getMessage() {
        ArrayList<UserPlusMessage> toRet = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "Message WHERE SenderId = ? OR ReceiverId = ?");

            // print out the query statement
            ps.setInt(1, SessionUtils.getUserId());
            ps.setInt(2, SessionUtils.getUserId());
            //   JsfUtil.addErrorMessage(ps.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //result found, means valid inputs
                UserPlusMessage added = new UserPlusMessage();
                added.setContent(rs.getString("Content"));
                added.setSubject(rs.getString("Subject"));
                added.setMessageId(rs.getInt("MessageId"));
                added.setDateSent(rs.getDate("DateSent"));
                added.setReceiverId(rs.getInt("ReceiverId"));
                added.setSenderId(rs.getInt("SenderId"));
                added.setSenderName(PostValidator.getNamesFromID(rs.getInt("SenderId")));
                added.setReceiverName(PostValidator.getNamesFromID(rs.getInt("ReceiverId")));
                toRet.add(added);

                // JsfUtil.addErrorMessage("Added to return list " + rs.getString("Content") + "authorName: " + added.getAuthorName());
            }
        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());

            return null;

        } finally {
            DataConnect.close(con);
        }
        return toRet;
    }

    public String back() {
        setItems(null);
        current = null;
        subjectInput = "";
        contentInput = "";
        return "/personalPage/MainPage";
    }

    /**
     * @return the subjectInput
     */
    public String getSubjectInput() {
        return subjectInput;
    }

    /**
     * @param subjectInput the subjectInput to set
     */
    public void setSubjectInput(String subjectInput) {
        this.subjectInput = subjectInput;
    }

    /**
     * @return the contentInput
     */
    public String getContentInput() {
        return contentInput;
    }

    /**
     * @param contentInput the contentInput to set
     */
    public void setContentInput(String contentInput) {
        this.contentInput = contentInput;
    }

    /**
     * @return the current
     */
    public UserPlusMessage getCurrent() {
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(UserPlusMessage current) {
        this.current = current;
    }

    /**
     * @return the items
     */
    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel(getMessage());
            return items;
        }
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(DataModel items) {
        this.items = items;
    }

    /**
     * @return the emailInput
     */
    public String getEmailInput() {
        return emailInput;
    }

    /**
     * @param emailInput the emailInput to set
     */
    public void setEmailInput(String emailInput) {
        this.emailInput = emailInput;
    }

    public void resetInputBoxes() {
        emailInput = "";
        subjectInput = "";
        contentInput = "";

    }

    public void insertMessage() {
        if (PostValidator.validate(emailInput) && !emailInput.equals(SessionUtils.getUserEmail()) && !subjectInput.isEmpty() && !contentInput.isEmpty()) {
            //Connect to server
            Connection con = null;
            // insert post
            PreparedStatement ps = null;
            //find page id where it is posted
            PreparedStatement ps2 = null;
            // insert to posted To
            PreparedStatement ps3 = null;

            try {
                con = DataConnect.getConnection();
                // give proper variables and propert format
                ps = con.prepareStatement("INSERT INTO Message(MessageId, DateSent, ReceiverId, SenderId, Subject, Content) values (?,?,?,?,?,?);", PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setNull(1, java.sql.Types.INTEGER);
                ps.setDate(2, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
                ps.setInt(3, PostValidator.getSearchID(emailInput));
                ps.setInt(4, SessionUtils.getUserId());
                ps.setString(5, subjectInput);
                ps.setString(6, contentInput);

                // print out the query statement
                JsfUtil.addErrorMessage(ps.toString());
                // Execute the Insert Query
                ps.executeUpdate();
                // Find the most recent postID due to autoincrement
               JsfUtil.addSuccessMessage("Message Sent");
                resetInputBoxes();
                

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Error occured while posting" + ex.getMessage());

            } finally {
                DataConnect.close(con);
                updateList();
            }

        }
        resetInputBoxes();
    }
    public void updateList(){
        items = null;
        current = null;
        items = getItems();
    }
    public void deleteMessage(){
        current = (UserPlusMessage) items.getRowData();
        if(current != null){
             Connection con = null;
            // insert post
            PreparedStatement ps = null;
            //find page id where it is posted
            PreparedStatement ps2 = null;
            // insert to posted To
            PreparedStatement ps3 = null;

            try {
                con = DataConnect.getConnection();
                // give proper variables and propert format
                ps = con.prepareStatement("DELETE FROM Message WHERE MessageId = ?");
                ps.setInt(1, current.getMessageId());
                // print out the query statement
                JsfUtil.addErrorMessage(ps.toString());
                // Execute the Insert Query
                ps.execute();
                // Find the most recent postID due to autoincrement
                JsfUtil.addSuccessMessage("Message Deleted");
                resetInputBoxes();
                

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Error occured while posting" + ex.getMessage());

            } finally {
                DataConnect.close(con);
                updateList();
            }

        }
        resetInputBoxes();
            
    }

}
