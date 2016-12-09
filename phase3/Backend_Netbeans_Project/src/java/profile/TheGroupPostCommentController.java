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
@Named("TheGroupPostCommentController")
@SessionScoped
public class TheGroupPostCommentController implements Serializable{
    private TheGroupPostComments current;
    private DataModel items = null;
    private String inputContent = "";
    
    public TheGroupPostCommentController(){
        
    }

    /**
     * @return the current
     */
    public TheGroupPostComments getCurrent() {
        if(current != null){
            return current;
        }
        TheGroupPostComments lol = new TheGroupPostComments();
        lol.setContent("");
        return lol;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(TheGroupPostComments current) {
        this.current = current;
    }

    /**
     * @return the items
     */
    public DataModel getItems() {
        if(items == null){
            HttpSession session = SessionUtils.getSession();
            TheGroupPost postId = (TheGroupPost) session.getAttribute("groupPost");
            items = new ListDataModel(getCommentsFromPost(postId));
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


    public List<TheGroupPostComments> getCommentsFromPost(TheGroupPost postId) {
        ArrayList<TheGroupPostComments> toRet = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM Comment C WHERE C.CommentId IN (SELECT C2.CommentId FROM CommentOn C2 WHERE C2.CommentId = C.CommentId AND C2.PostId = ? );");
            ps.setInt(1, postId.getPostId());

            ResultSet result = ps.executeQuery();
            while (result.next()) {
                TheGroupPostComments comment = new TheGroupPostComments();
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
                
                                ps3 = con.prepareStatement("SELECT \n"
                        + "    *\n"
                        + "FROM\n"
                        + "    LikesComment WHERE CommentId = ? AND UserId = ?;");
                ps3.setInt(1, result.getInt("CommentId"));
                ps3.setInt(2, SessionUtils.getUser().getUserId());
                ResultSet rs2 = ps2.executeQuery();
                ResultSet rs3 = ps3.executeQuery();

                while (rs2.next()) {
                    if (result.getString("AuthorId").equals(rs2.getString("UserId"))) {
                        comment.setTheAuthorName(rs2.getString("firstName") + " " + rs2.getString("lastName"));
                        break;
                    }
                    //  JsfUtil.addErrorMessage(ps2.toString());
                }
                if(rs3.next()){
                    comment.setLikeView("You Have Liked Comment");
                }
                else{
                    comment.setLikeView("Like");
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
     public void processModifyComment() {
  
        if ( current != null && !current.getContent().isEmpty()) {
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
                setInputContent("");

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Error occured while commenting" + ex.getMessage());

            } finally {
                DataConnect.close(con);
       
            }
        } else {
            setInputContent("");
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
                ps.setString(3, getInputContent());
                ps.setInt(4, SessionUtils.getUser().getUserId());

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
                ps2.setInt(2, SessionUtils.getGroupPost().getPostId());

                ps2.executeUpdate();

                ps2.close();

                JsfUtil.addSuccessMessage("Post successful to email: ");
                updatePostCommentCount();
                updateList();
                setInputContent("");

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Error occured while commenting" + ex.getMessage());

            } finally {
                DataConnect.close(con);
            }
        } else {
            setInputContent("");
            JsfUtil.addErrorMessage("Error occured while commenting");
        }

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
            ps.setInt(1, SessionUtils.getGroupPost().getPostId());
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
            ps2.setInt(2, SessionUtils.getGroupPost().getPostId());
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
       public void goLikeorUnlikeComment(){
        current = (TheGroupPostComments) getItems().getRowData();

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
                ps = con.prepareStatement("SELECT * FROM LikesComment WHERE CommentId = ? AND UserId = ?");
                ps.setInt(1, current.getCommentId());
                ps.setInt(2, SessionUtils.getUser().getUserId());
                
                ResultSet set = ps.executeQuery();
                if(!set.next()){
                    //if passed then User did not like this post
                    //ACTION: like
                    ps2 = con.prepareStatement("INSERT INTO LikesComment(CommentId,UserId) VALUES(?,?)");
                    ps2.setInt(1, current.getCommentId());
                    ps2.setInt(2, SessionUtils.getUser().getUserId());
                    //User will like the Post
                    JsfUtil.addErrorMessage("You have liked the Comment");
                // Execute the Insert Query
                    current.setLikeView("You Have Liked Comment");
                    ps2.execute();
                    ps2.close();
                    
                }
                else{
                   ps3 = con.prepareStatement("DELETE FROM LikesComment WHERE CommentId = ? AND UserId = ?");
                   ps3.setInt(1, current.getCommentId());
                   ps3.setInt(2, SessionUtils.getUser().getUserId());
                   JsfUtil.addErrorMessage("You unliked the Comment");
                   current.setLikeView("Like");
                   ps3.execute(); 
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
       
        public void updateList() {
        items = null;
        setItems(getItems());
        current = null;
    }
        
            
      public String prepareEdit(){
          current = (TheGroupPostComments) items.getRowData();
          return "/groupPage/GroupCommentListPostViewer";
      }
      public String preparedDelete(){
            current = (TheGroupPostComments) items.getRowData();
             deleteComment();
            return "/groupPage/GroupCommentListPostViewer";
          
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
                setInputContent("");

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Error occured while commenting" + ex.getMessage());

            } finally {
                DataConnect.close(con);
            }
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
        
    public void clearComment(){
            inputContent = "";
    }
    public String goBack(){
        items = null;
        current = null;
        inputContent = "";
       
        return "/groupPage/GroupPost";
        
    }


}
