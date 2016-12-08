package profile;

import profile.util.JsfUtil;
import profile.util.PaginationHelper;

import java.io.Serializable;
import java.sql.Connection;
import static java.sql.JDBCType.NULL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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

@Named("postController")
@SessionScoped
public class PostController implements Serializable {

    private Post current;
    private DataModel items = null;
    @EJB
    private profile.PostFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String postContent = "";
    private String postTo;
  

    public PostController() {
    }

    public Post getSelected() {
        if (current == null) {
            current = new Post();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PostFacade getFacade() {
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
        current = (Post) getItems().getRowData();
        PaginationHelper ph = getPagination();
        selectedItemIndex = ph.getPageFirstItem() + getItems().getRowIndex();
        return "/post/PostOwner";
    }

    public String prepareViewFromNewfeed() {
        current = (Post) getItems().getRowData();
        PaginationHelper ph = getPagination();
        selectedItemIndex = ph.getPageFirstItem() + getItems().getRowIndex();
        return "/post/PostOwner";
    }

    public String prepareCreate() {
        current = new Post();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PostCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Post) getItems().getRowData();
        PaginationHelper ph = getPagination();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PostUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Post) getItems().getRowData();
        PaginationHelper ph = getPagination();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();

        return deletePost();
 
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

//    public DataModel getItems() {
//        if (items == null) {
//            items = getPagination().createPageDataModel();
//        }
//        return items;
//    }
    private void recreateModel() {
        items = null;
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

    public Post getPost(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * @return the postContent
     */
    public String getPostContent() {
        return postContent;
    }

    /**
     * @param postContent the postContent to set
     */
    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    /**
     * @return the postTo
     */
    public String getPostTo() {
        return postTo;
    }

    /**
     * @param postTo the postTo to set
     */
    public void setPostTo(String postTo) {
        this.postTo = postTo;
    }

    @FacesConverter(forClass = Post.class)
    public static class PostControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PostController controller = (PostController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "postController");
            return controller.getPost(getKey(value));
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
            if (object instanceof Post) {
                Post o = (Post) object;
                return getStringKey(o.getPostId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Post.class.getName());
            }
        }

    }

    /*Daniel Added*/
    private DataModel myItems;

    public void setMyItems(DataModel myItems) {
        this.myItems = myItems;
    }

    public DataModel getItems() {
         
        if (myItems == null) {
            HttpSession session = SessionUtils.getSession();
            String username = String.valueOf(session.getAttribute("userid"));
            myItems = new ListDataModel(getPostsBy(username));
            JsfUtil.addErrorMessage("Logged in as " + username);
            return myItems;
        }
        else{
            return myItems;
        }
    }

    public List<Post> getPostsBy(String user) {
        ArrayList<Post> toRet = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    Post\n"
                    + "WHERE\n"
                    + "    Post.PostId IN (SELECT \n"
                    + "            PostId\n"
                    + "        FROM\n"
                    + "            PostedTo\n"
                    + "        WHERE\n"
                    + "            PostedTo.PageId = (SELECT \n"
                    + "                    PersonalPage.PageId\n"
                    + "                FROM\n"
                    + "                    PersonalPage\n"
                    + "                WHERE\n"
                    + "                    PersonalPage.ownerid = (SELECT \n"
                    + "                            UserId\n"
                    + "                        FROM\n"
                    + "                            UserPlus\n"
                    + "                        WHERE\n"
                    + "                            UserId = ?))) ;");

            // print out the query statement
            ps.setString(1, user);
            //   JsfUtil.addErrorMessage(ps.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //result found, means valid inputs
                Post added = new Post();
                added.setContent(rs.getString("Content"));
                added.setPostId(rs.getInt("postId"));
                added.setTheauthorId(rs.getInt("AuthorId"));
                added.setDateCreated(rs.getDate("DateCreated"));


                ps2 = con.prepareStatement("SELECT \n"
                        + "    *\n"
                        + "FROM\n"
                        + "    UserPlus;\n");
                ps3 = con.prepareStatement("SELECT \n"
                        + "    *\n"
                        + "FROM\n"
                        + "    LikesPost WHERE PostId = ? AND UserId = ?;");
                ps3.setInt(1, rs.getInt("postId"));
                ps3.setInt(2, SessionUtils.getUserId());
                

                // print out the query statement
                //   JsfUtil.addErrorMessage(ps2.toString());
                ResultSet rs2 = ps2.executeQuery();
                ResultSet rs3 = ps3.executeQuery();

                while (rs2.next()) {
                    if (rs.getString("AuthorId").equals(rs2.getString("UserId"))) {
                        added.setAuthorName(rs2.getString("firstName") + " " + rs2.getString("lastName"));
                        added.setAuthorEmail(rs2.getString("email"));
                        break;
                    }
                    //  JsfUtil.addErrorMessage(ps2.toString());
                }
                if(rs3.next()){
                    added.setLikeView("You Have Liked Post");
                }
                else{
                    added.setLikeView("Like");
                }
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

    public String createPost() {
        boolean valid = PostValidator.validate(postTo);
        if (valid && !postContent.isEmpty()) {
            
            createPostToServer(postTo, postContent);
            JsfUtil.addSuccessMessage("Post successful to email: " + postTo);
            updateList();
            return "/UserLevel/Post";
        } else {
             postTo = "";
             JsfUtil.addErrorMessage("Invalid Email Address OR No input");
            return "/UserLevel/Post";
            
        }
    }
    
    public void createPostToServer(String email, String content) {
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
            ps = con.prepareStatement("INSERT INTO POST(PostID, DateCreated, Content, CommentCount, AuthorID) values (?, ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setNull(1, java.sql.Types.INTEGER);
            ps.setTimestamp(2, java.sql.Timestamp.from(java.time.Instant.now()));
            ps.setString(3, content);
            ps.setInt(4, 0);
            ps.setInt(5, SessionUtils.getUserId());

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
            ps3 = con.prepareStatement("SELECT * FROM PersonalPage WHERE OwnerId = ?");
            // invoke method to find postID
            int ownerId = PostValidator.getPostToID(email);

            ps3.setInt(1, ownerId);
            ResultSet ownerID = ps3.executeQuery();
            int owner = -1;
            while (ownerID.next()) {
                owner = ownerID.getInt("PageId");
            }

            ps2 = con.prepareStatement("INSERT INTO PostedTo(PostID, PageID) values (?, ?);");
            ps2.setInt(1, newestpostID);
            ps2.setInt(2, owner);

            ps2.executeUpdate();
   

            ps3.close();
            ps2.close();
            resetInputBoxes();
            JsfUtil.addSuccessMessage("Post successful to email: " + email);

        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Error occured while posting" + ex.getMessage());

        } finally {
            DataConnect.close(con);
        }

    }

    public void resetInputBoxes() {
        postContent = "";
        postTo = "";

    }
    public String modifyPost(){
        if(current != null){

            
            //Connect to server
            Connection con = null;
            // modify post
            PreparedStatement ps = null;


            try {
                con = DataConnect.getConnection();
                // give proper variables and propert format
                ps = con.prepareStatement("UPDATE Post SET DateCreated = ?, Content = ? WHERE PostId = ?;");
                ps.setTimestamp(1, java.sql.Timestamp.from(java.time.Instant.now()));
                ps.setString(2, getSelected().getContent());
                ps.setInt(3, getSelected().getPostId());

                // print out the query statement
                JsfUtil.addErrorMessage(ps.toString());
                // Execute the Insert Query
                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Error occured while posting" + ex.getMessage());

            } finally {
                DataConnect.close(con);
       
            }
            
            
        }
        updateList();
        return "/UserLevel/Post";
        
    }
    public String deletePost(){
           Connection con = null;
            // modify post
            PreparedStatement ps = null;


            try {
                con = DataConnect.getConnection();
                // give proper variables and propert format
                ps = con.prepareStatement("DELETE FROM Post WHERE PostId = ?");
                ps.setInt(1, getSelected().getPostId());
                ps.execute();
                // print out the query statement
                JsfUtil.addErrorMessage(ps.toString());
                // Execute the Insert Query

                ps.close();

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Error occured while deleting" + ex.getMessage());

            } finally {
                DataConnect.close(con);
           
            }
            
        updateList();
        return "/UserLevel/Post";
        
        }
    public void updateList(){
        myItems = null;
        setMyItems(getItems());
        current = null;
        
        
    }
    public String goToComment(){
   
        current = (Post) getItems().getRowData();
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("post", current);
     
        return "/comment/CommentListPostViewer";
    }
    public void clearSelectedInput(){
        if(current != null){
            postContent = "";
        }
    }
    public void goLikeorUnlikePost(){
        current = (Post) getItems().getRowData();
        PaginationHelper ph = getPagination();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        if(current != null){
           //Connect to server
            Connection con = null;
            // modify post
            PreparedStatement ps = null;
            PreparedStatement ps2 = null;
            PreparedStatement ps3 = null;
     

            try {
                con = DataConnect.getConnection();
                // give proper variables and propert format
                // check if user liked this specific post
                ps = con.prepareStatement("SELECT * FROM LikesPost WHERE PostId = ? AND UserId = ?");
                ps.setInt(1, getSelected().getPostId());
                ps.setInt(2, SessionUtils.getUserId());
                
                ResultSet set = ps.executeQuery();
                if(!set.next()){
                    //if passed then User did not like this post
                    //ACTION: like
                    ps2 = con.prepareStatement("INSERT INTO LikesPost(PostId,UserId) VALUES(?,?)");
                    ps2.setInt(1, getSelected().getPostId());
                    ps2.setInt(2, SessionUtils.getUserId());
                    //User will like the Post
                    JsfUtil.addErrorMessage("You Have Liked Post");
                // Execute the Insert Query
                    ps2.execute();
                    //change to Liked
                    current.setLikeView("You Have Liked Post");
                    ps2.close();
                }
                else{
                   ps3 = con.prepareStatement("DELETE FROM LikesPost WHERE PostId = ? AND UserId = ?");
                   ps3.setInt(1, getSelected().getPostId());
                   ps3.setInt(2, SessionUtils.getUserId());
                   JsfUtil.addErrorMessage("You unliked the Post");
                   ps3.execute();
                   // change status
                   current.setLikeView("Like");
                   ps3.close();
                }


                ps.close();

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Error occured while liking posting" + ex.getMessage());

            } finally {
                DataConnect.close(con);
                updateList();
       
            }
        }
        
    }
        

}
