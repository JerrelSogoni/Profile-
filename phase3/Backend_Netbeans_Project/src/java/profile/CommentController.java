package profile;

import profile.util.JsfUtil;
import profile.util.PaginationHelper;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import profile.util.DataConnect;
import profile.util.PostValidator;
import profile.util.SessionUtils;

@Named("commentController")
@SessionScoped
public class CommentController implements Serializable {

    private Comment current;
    private DataModel items = null;
    @EJB
    private profile.CommentFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String inputContent = "";
    private int historyID;
    private boolean editing = false;

    public CommentController() {
    }

    public Comment getSelected() {
        if (current == null) {
            current = new Comment();
            selectedItemIndex = -1;
        }

        return current;
    }

    private CommentFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Comment) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Comment();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CommentCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        editing = true;
        current = (Comment)getItems().getRowData();
        System.out.println(current.getTheAuthorName());
        if(!(current.getTheAuthorId() == SessionUtils.getUserId())){
            current = null;
        }
        
        PaginationHelper ph = getPagination();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CommentUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        editing = true;
        current = (Comment) getItems().getRowData();
        
        PaginationHelper ph = getPagination();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        if(current.getTheAuthorId().intValue() == SessionUtils.getUserId() || SessionUtils.getPost().getPostId().intValue() == SessionUtils.getUserId()){
            deleteComment();
            
            
        }
        editing = false;
        return "/comment/CommentListPostViewer";
    }
    public void deleteComment(){
         Connection con = null;
            // insert post
            PreparedStatement ps = null;
            //find page id where it is posted


            try {
                con = DataConnect.getConnection();
                // give proper variables and propert format
                ps = con.prepareStatement("DELETE FROM Comment WHERE CommentId = ? AND AuthorId = ?;");
                ps.setInt(1, current.getCommentId());
                ps.setInt(2, current.getTheAuthorId());
                // print out the query statement
                JsfUtil.addErrorMessage(ps.toString());
                // Execute the delete Query
                ps.execute();


                JsfUtil.addSuccessMessage("Post successful to email: ");
                // update post count
                updatePostCommentCount();
                updateList();
                inputContent = "";

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Error occured while commenting" + ex.getMessage());

            } finally {
                DataConnect.close(con);
            }
        
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CommentDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if(items == null || !editing){
            HttpSession session = SessionUtils.getSession();
            Post postId = (Post) session.getAttribute("post");
            items = new ListDataModel(getCommentsFromPost(postId));
            JsfUtil.addErrorMessage("Logged in as " + postId);
            return items;
        }
        
        else{
            return items;
        }
       
     
    }

    public List<Comment> getCommentsFromPost(Post postId) {
        ArrayList<Comment> toRet = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM Comment C WHERE C.CommentId IN (SELECT C2.CommentId FROM CommentOn C2 WHERE C2.CommentId = C.CommentId AND C2.PostId = ? );");
            ps.setInt(1, postId.getPostId());

            ResultSet result = ps.executeQuery();
            while (result.next()) {
                Comment comment = new Comment();
                comment.setCommentId(result.getInt("commentId"));
                comment.setContent(result.getString("content"));
                comment.setTheAuthorId(result.getInt("authorId"));
                comment.setDateCreated(result.getDate("DateCreated"));
                ps2 = con.prepareStatement("SELECT \n"
                        + "    *\n"
                        + "FROM\n"
                        + "    UserPlus;\n");

                // print out the query statement
                //   JsfUtil.addErrorMessage(ps2.toString());
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    if (result.getString("AuthorId").equals(rs2.getString("UserId"))) {
                        comment.setTheAuthorName(rs2.getString("firstName") + " " + rs2.getString("lastName"));
                        break;
                    }
                    //  JsfUtil.addErrorMessage(ps2.toString());
                }
                toRet.add(comment);
            }
            return toRet;
        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());

            return null;
        }

    }

    private void recreateModel() {
        setItems(null);
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Comment getComment(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * @param items the items to set
     */
    public void setItems(DataModel items) {
        this.items = items;
    }

    /**
     * @return the inputContent
     */
    public String getInputContent() {
        return inputContent;
    }

    /**
     * @param inputContent the inputContent to set
     */
    public void setInputContent(String inputContent) {
        this.inputContent = inputContent;
    }

    /**
     * @return the historyID
     */
    public int getHistoryID() {
        return historyID;
    }

    /**
     * @param historyID the historyID to set
     */
    public void setHistoryID(int historyID) {
        this.historyID = historyID;
    }

    @FacesConverter(forClass = Comment.class)
    public static class CommentControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CommentController controller = (CommentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "commentController");
            return controller.getComment(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Comment) {
                Comment o = (Comment) object;
                return getStringKey(o.getCommentId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Comment.class.getName());
            }
        }

    }

    public void processModifyComment() {
        editing = true;
        if ( current != null && !current.getContent().isEmpty()  && editing ) {
            //Connect to server
            Connection con = null;
            // modify comment
            PreparedStatement ps = null;

            try {
                con = DataConnect.getConnection();
                // give proper variables and propert format
                ps = con.prepareStatement("UPDATE Comment SET Content = ?  WHERE CommentId = ?;");
                ps.setString(1, current.getContent());
                ps.setInt(2, current.getCommentId());

                // print out the query statement
                JsfUtil.addErrorMessage(ps.toString());
                // Execute the Insert Query
                ps.executeUpdate();
                // Find the most recent postID due to autoincrement

                ps.close();
                // prepare for getting page id 

                JsfUtil.addSuccessMessage("Post successful to email: ");
                updateList();
                inputContent = "";

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Error occured while commenting" + ex.getMessage());

            } finally {
                DataConnect.close(con);
                editing = false;
            }
        } else {
            inputContent = "";
            JsfUtil.addErrorMessage("Error occured while commenting");
        }

    }

    public void processNewComment() {
        if (items != null && !inputContent.isEmpty()) {
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
                ps = con.prepareStatement("INSERT INTO Comment(CommentID, DateCreated, Content, AuthorID) VALUES (?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setNull(1, java.sql.Types.INTEGER);
                ps.setTimestamp(2, java.sql.Timestamp.from(java.time.Instant.now()));
                ps.setString(3, inputContent);
                ps.setInt(4, SessionUtils.getUserId());

                // print out the query statement
                JsfUtil.addErrorMessage(ps.toString());
                // Execute the Insert Query
                ps.executeUpdate();
                // Find the most recent postID due to autoincrement
                ResultSet keyResultSet = ps.getGeneratedKeys();
                // initalized newestpost 
                int newestpostID = -1;
                while (keyResultSet.next()) {
                    // get newest post
                    newestpostID = keyResultSet.getInt(1);
                }
                //close connection to save on resources
                ps.close();
                // prepare for getting page id 

                ps2 = con.prepareStatement("INSERT INTO CommentOn(CommentID, PostID) VALUES (?, ?);");
                ps2.setInt(1, newestpostID);
                ps2.setInt(2, SessionUtils.getPost().getPostId());

                ps2.executeUpdate();

                ps2.close();

                JsfUtil.addSuccessMessage("Post successful to email: ");
                updatePostCommentCount();
                updateList();
                inputContent = "";

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Error occured while commenting" + ex.getMessage());

            } finally {
                DataConnect.close(con);
            }
        } else {
            inputContent = "";
            JsfUtil.addErrorMessage("Error occured while commenting");
        }

    }

    public void updateList() {
        items = null;
        setItems(getItems());
        current = null;
    }

    public void clearNewComment() {
        inputContent = "";
    }

    public String back() {
        items = null;
        current = null;
        return "/UserLevel/Post";
    }

    public void updatePostCommentCount() {
        Connection con = null;
        // insert post
        PreparedStatement ps = null;
        //find page id where it is posted
        PreparedStatement ps2 = null;
   

        try {
            con = DataConnect.getConnection();
            // give proper variables and propert format
            ps = con.prepareStatement("SELECT COUNT(*) FROM CommentOn WHERE PostID = ? ;");
            ps.setInt(1, SessionUtils.getPost().getPostId());
            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());
            // Execute the Insert Query
            int count = -1;
            // Find the most recent postID due to autoincrement
            ResultSet keyResultSet = ps.executeQuery();
            if (keyResultSet.next()) {
                count = keyResultSet.getInt(1);
            }
    
   
 
            ps2 = con.prepareStatement("UPDATE Post SET CommentCount = ? WHERE PostId = ?;");
            ps2.setInt(1, count);
            ps2.setInt(2, SessionUtils.getPost().getPostId());
            ps2.executeUpdate();

            //close connection to save on resources
            ps.close();
            ps2.close();

        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Error occured while commenting" + ex.getMessage());

        } finally {
            DataConnect.close(con);
        }
    }

}
