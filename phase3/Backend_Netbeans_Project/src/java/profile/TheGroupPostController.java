/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import profile.util.DataConnect;
import profile.util.JsfUtil;
import profile.util.SessionUtils;

/**
 *
 * @author Jerrel
 */
@Named("TheGroupPostController")
@SessionScoped
public class TheGroupPostController implements Serializable {

    private TheGroupPost current;
    private DataModel items = null;
    private String postContent = "";

    public TheGroupPostController() {

    }

    /**
     * @return the current
     */
    public TheGroupPost getCurrent() {
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(TheGroupPost current) {
        this.current = current;
    }

    /**
     * @return the items
     */
    public DataModel getItems() {
        if(items == null){
             items = new ListDataModel(getPostsBy());
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


    public List<TheGroupPost> getPostsBy() {
        ArrayList<TheGroupPost> toRet = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM POST WHERE POST.PostId in "
                    + "(SELECT T.PostId FROM PostedTo T WHERE T.PageId IN (SELECT P.PageId FROM ISIN I,"
                    + " GroupPlus G, GroupPage P WHERE I.GroupId = G.GroupId AND G.GroupId = P.GroupId AND "
                    + "I.UserId = ? AND G.GroupId = ?));");

            // print out the query statement
            ps.setInt(1, SessionUtils.getUserId());
            ps.setInt(2, SessionUtils.getGroupId());

            //   JsfUtil.addErrorMessage(ps.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //result found, means valid inputs
                TheGroupPost added = new TheGroupPost();
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
                ps3.setInt(2, SessionUtils.getUser().getUserId());

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
                if (rs3.next()) {
                    added.setLikeView("You Have Liked Post");
                } else {
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

        if (!postContent.isEmpty()) {

            if (createPostToServer(getPostContent())) {
     
                updateList();
            }
            return "/groupPage/GroupPost";
        } else {
            JsfUtil.addErrorMessage("Invalid Email Address OR No input");
            return "/groupPage/GroupPost";

        }
    }

    public boolean createPostToServer(String content) {
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
            ps.setInt(5, SessionUtils.getUser().getUserId());

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
            ps3 = con.prepareStatement("SELECT * FROM GroupPage WHERE GroupId = ?");
            ps3.setInt(1, SessionUtils.getGroupId());
            // invoke method to find postID
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
            JsfUtil.addSuccessMessage("Post successful  ");
            return true;

        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Error occured while posting" + ex.getMessage());

        } finally {
            DataConnect.close(con);
        }
        return false;

    }

    public void clearSelectedInput() {
        if (current != null) {
            setPostContent("");
        }
    }

    public void updateList() {
        items = null;
        setItems(getItems());
        current = null;

    }

    public void resetInputBoxes() {
        setPostContent("");

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
                ps.setString(2, current.getContent());
                ps.setInt(3, current.getPostId());

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
        return  "/groupPage/GroupPost";
        
    }
      public String deletePost(){
           Connection con = null;
            // modify post
            PreparedStatement ps = null;


            try {
                con = DataConnect.getConnection();
                // give proper variables and propert format
                ps = con.prepareStatement("DELETE FROM Post WHERE PostId = ?");
                ps.setInt(1, current.getPostId());
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
        return "/groupPage/GroupPost";
        
        }
      
        public String goToComment(){
   
        current = (TheGroupPost) getItems().getRowData();
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("groupPost", current);
     
        return "/groupPage/GroupCommentListPostViewer";
    }
        
      public void goLikeorUnlikePost(){
        current = (TheGroupPost) getItems().getRowData();
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
                ps.setInt(1, current.getPostId());
                ps.setInt(2, SessionUtils.getUser().getUserId());
                
                ResultSet set = ps.executeQuery();
                if(!set.next()){
                    //if passed then User did not like this post
                    //ACTION: like
                    ps2 = con.prepareStatement("INSERT INTO LikesPost(PostId,UserId) VALUES(?,?)");
                    ps2.setInt(1, current.getPostId());
                    ps2.setInt(2, SessionUtils.getUser().getUserId());
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
                   ps3.setInt(1, current.getPostId());
                   ps3.setInt(2, SessionUtils.getUser().getUserId());
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
    
      public String prepareEdit(){
          current = (TheGroupPost) items.getRowData();
          return "/groupPage/GroupPost";
      }
      public String preparedDelete(){
            current = (TheGroupPost) items.getRowData();
            deletePost();
            return "/groupPage/GroupPost";
          
      }
      public String goBack(){
        items = null;
        current = null;
        postContent = "";
       
        return "/GroupLevel/UserGroupList";
        
      }
        


}
