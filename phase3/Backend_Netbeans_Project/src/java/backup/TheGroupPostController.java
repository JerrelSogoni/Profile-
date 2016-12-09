/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;

import profile.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;
import profile.util.DataConnect;
import profile.util.JsfUtil;
import profile.util.PaginationHelper;
import profile.util.SessionUtils;

/**
 *
 * @author Jerrel
 */
@Named("TheGroupdpsifjsdijPostController")
@SessionScoped
public class TheGroupPostController implements Serializable{
    private TheGroupPost current;
    private DataModel items = null;
    private String postContent = "";
    private String postTo;
    
    public TheGroupPostController(){
        
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
     public List<Post> getPostsBy() {
        ArrayList<Post> toRet = new ArrayList<>();
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

}
