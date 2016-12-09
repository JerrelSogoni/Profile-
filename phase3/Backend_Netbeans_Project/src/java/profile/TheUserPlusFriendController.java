package profile;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;
import profile.util.DataConnect;
import profile.util.JsfUtil;
import profile.util.PostValidator;
import profile.util.SessionUtils;

@Named("TheUserPlusFriendController")
@SessionScoped
public class TheUserPlusFriendController implements Serializable {

    private TheUserPlusFriend current;
    private DataModel items = null;

    private String friendInput;

    public TheUserPlusFriendController() {
    }

    public List<TheUserPlusFriend> getPostsBy(String user) {
        ArrayList<TheUserPlusFriend> toRet = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "UserPlus U WHERE U.UserId IN (SELECT * FROM FriendsWith F WHERE F.FriendId = ?;");

            // print out the query statement

            ps.setInt(1, SessionUtils.getUser().getUserId());
            //   JsfUtil.addErrorMessage(ps.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //result found, means valid inputs
                TheUserPlusFriend added = new TheUserPlusFriend();
                added.setFriendEmail(rs.getString("email"));
                added.setFriendId(rs.getInt("UserId"));
                added.setFriendName(rs.getString("firstName") + " " + rs.getString("lastName"));

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

    public DataModel getItems() {
        if (items == null) {
            HttpSession session = SessionUtils.getSession();
            String username = String.valueOf(session.getAttribute("email"));
            items = new ListDataModel(getFriends(username));
            JsfUtil.addErrorMessage("Logged in as " + username);
            return items;
        }
        return items;
    }

    /**
     * @return the friendInput
     */
    public String getFriendInput() {
        return friendInput;
    }

    /**
     * @param friendInput the friendInput to set
     */
    public void setFriendInput(String friendInput) {
        this.friendInput = friendInput;
    }

    /**
     * @return the current
     */
    public TheUserPlusFriend getCurrent() {
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(TheUserPlusFriend current) {
        this.current = current;
    }

    /**
     * @param items the items to set
     */
    public void setItems(DataModel items) {
        this.items = items;
    }

    public List getFriends(String username) {
        ArrayList<TheUserPlusFriend> toRet = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    UserPlus WHERE UserId in(Select FriendId FROM FriendsWith WHERE UserId = ?);");

            // print out the query statement
            ps.setInt(1, SessionUtils.getUser().getUserId());
            //   JsfUtil.addErrorMessage(ps.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //result found, means valid inputs
                TheUserPlusFriend added = new TheUserPlusFriend();
                added.setFriendEmail(rs.getString("email"));
                added.setFriendId(rs.getInt("UserId"));
                added.setFriendName(rs.getString("FirstName") + " " + rs.getString("LastName"));
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

    public void updateList() {
        items = null;
        setItems(getItems());
        current = null;

    }

    public void addFriend() {
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        if ((!friendInput.isEmpty() && !friendInput.equals(SessionUtils.getUser().getEmail())) && PostValidator.validate(friendInput)) {

            try {
                con = DataConnect.getConnection();
                ps = con.prepareStatement("INSERT INTO FriendsWith(UserId,FriendId) VALUES(? ,? );");
                // print out the query statement
                ps.setInt(1, SessionUtils.getUser().getUserId());
                ps.setInt(2, PostValidator.getSearchID(friendInput));
                ps2 = con.prepareStatement("INSERT INTO FriendsWith(UserId,FriendId) VALUES(? ,? );");
                ps2.setInt(2, SessionUtils.getUser().getUserId());
                ps2.setInt(1, PostValidator.getSearchID(friendInput));
                //   JsfUtil.addErrorMessage(ps.toString());
                ps.execute();
                ps2.execute();
               

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());

            } finally {
                DataConnect.close(con);
                updateList();
                friendInput = "";
            }

        }
        friendInput = "";

    }

    public void removeFriend() {
        current = (TheUserPlusFriend) items.getRowData();
        if (current != null) {
            Connection con = null;
            PreparedStatement ps = null;
            PreparedStatement ps2 = null;

            try {
                con = DataConnect.getConnection();
                ps = con.prepareStatement("DELETE FROM FriendsWith WHERE UserId = ? AND FriendId = ?");
                ps2 = con.prepareStatement("DELETE FROM FriendsWith WHERE UserId = ? AND FriendId = ?");
                // print out the query statement
                ps.setInt(1, SessionUtils.getUser().getUserId());
                ps.setInt(2, current.getFriendId());
                ps2.setInt(2, SessionUtils.getUser().getUserId());
                ps2.setInt(1, current.getFriendId());
               
                //   JsfUtil.addErrorMessage(ps.toString());
                ps.execute();
                ps2.execute();

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());

            } finally {
                DataConnect.close(con);
                updateList();
                friendInput = "";
            }

        }

    }
    public String back() {
        items = null;
        current = null;
        return "/personalPage/MainPage";
    }

}
