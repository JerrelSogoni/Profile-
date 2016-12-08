package tmp;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import profile.GroupPlus;
import profile.UserPlus;
import profile.util.DataConnect;
import profile.util.JsfUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yunjoon_soh
 */
@Named("group")
@SessionScoped
public class Group implements Serializable {
    
    public String createGrp() {
        return "/groupPlus/Create";
    }
    
    public String deleteGrp() {
        return "/groupPlus/List";
    }
    
    public String renameGrp() {
        return "/groupPlus/List";
    }
    
    public String addToGrp() {        
        return "/GroupLevel/GroupList";
    }
    
    public String rmFromGroup() {
        return null;
    }
    
    public String join() {
        return null;
    }
    
    public String unjoin() {
        return null;
    }
    
    public String makePostGrp() {
        return null;
    }
    
    public String commentPostGrp() {
        return null;
    }
    
    public String removePostGrp() {
        return null;
    }
    
    public String removeCommentGrp() {
        return null;
    }
    
    public String likePostGrp() {
        return null;
    }
    
    public String likeCommentGrp() {
        return null;
    }
    
    public String unlikePostGrp() {
        return null;
    }
    
    public String unlikeCommentGrp() {
        return null;
    }
    
    public String editPostGrp() {
        return null;
    }
    
    public String editCommentGrp() {
        return null;
    }
    
    private UserPlus queryUserPlus;
    private GroupPlus queryGroupPlus;
    
    public UserPlus getQueryUserPlus() {
        return queryUserPlus;
    }
    
    public void setQueryUserPlus(UserPlus queryUserPlus) {
        this.queryUserPlus = queryUserPlus;
    }
    
    public GroupPlus getQueryGroupPlus() {
        return queryGroupPlus;
    }
    
    public void setQueryGroupPlus(GroupPlus queryGroupPlus) {
        this.queryGroupPlus = queryGroupPlus;
    }
    
    private List<UserGroupPair> userInGroupList, groupWithUsersList;
    
    public class UserGroupPair {
        
        private String userId, userName, groupId, groupName;
        
        public String getUserId() {
            return userId;
        }
        
        public void setUserId(String userId) {
            this.userId = userId;
        }
        
        public String getUserName() {
            return userName;
        }
        
        public void setUserName(String userName) {
            this.userName = userName;
        }
        
        public String getGroupId() {
            return groupId;
        }
        
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        
        public String getGroupName() {
            return groupName;
        }
        
        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }
        
    }
    
    public List<UserGroupPair> getUserInGroupList() {
        userInGroupList = new ArrayList<>();
        
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM IsIn I WHERE I.UserId = ?;");
            
            if (queryUserPlus != null) {
                ps.setInt(1, queryUserPlus.getUserId());
            }

            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                //result found, means valid inputs
                UserGroupPair added = new UserGroupPair();
                
                added.setGroupId(rs.getString("GroupId"));
                added.setUserId(rs.getString("UserId"));
                
                userInGroupList.add(added);
            }
            
        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            System.out.println("Login error -->" + ex.getMessage());
            
        } finally {
            DataConnect.close(con);
        }
        
        return userInGroupList;
    }
    
    public void setUserInGroupList(List<UserGroupPair> userInGroupList) {
        this.userInGroupList = userInGroupList;
    }
    
    public List<UserGroupPair> getGroupWithUsersList() {
        groupWithUsersList = new ArrayList<>();
        
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM IsIn I WHERE I.GroupId = ?;");
            
            if (queryGroupPlus != null) {
                ps.setInt(1, queryGroupPlus.getGroupId());
            }

            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                //result found, means valid inputs
                UserGroupPair added = new UserGroupPair();
                
                added.setGroupId(rs.getString("GroupId"));
                added.setUserId(rs.getString("UserId"));
                
                groupWithUsersList.add(added);
            }
            
        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            System.out.println("Login error -->" + ex.getMessage());
            
        } finally {
            DataConnect.close(con);
        }
        
        return groupWithUsersList;
    }
    
    public void setGroupWithUsersList(List<UserGroupPair> groupWithUsersList) {
        this.groupWithUsersList = groupWithUsersList;
    }
    
    public String addToGrpButton() {
        if (queryUserPlus != null && queryGroupPlus != null) {
            Connection con = null;
            PreparedStatement ps = null;
            
            try {
                con = DataConnect.getConnection();
                ps = con.prepareStatement("INSERT INTO IsIn VALUES(?, ?);");
                
                ps.setInt(1, queryUserPlus.getUserId());
                ps.setInt(2, queryGroupPlus.getGroupId());

                // print out the query statement
                JsfUtil.addErrorMessage(ps.toString());
                
                ps.execute();
                
            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
                System.out.println("Login error -->" + ex.getMessage());
                
            } finally {
                DataConnect.close(con);
            }
        }
        
        return "/GroupLevel/GroupList";
    }
    
    public String rmFromGrpButton(){
        if (queryUserPlus != null && queryGroupPlus != null) {
            Connection con = null;
            PreparedStatement ps = null;
            
            try {
                con = DataConnect.getConnection();
                ps = con.prepareStatement("DELETE FROM IsIn WHERE UserId = ? AND GroupId = ?;");
                
                ps.setInt(1, queryUserPlus.getUserId());
                ps.setInt(2, queryGroupPlus.getGroupId());

                // print out the query statement
                JsfUtil.addErrorMessage(ps.toString());
                
                ps.execute();
                
            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
                System.out.println("Login error -->" + ex.getMessage());
                
            } finally {
                DataConnect.close(con);
            }
        }
        
        return "/GroupLevel/GroupList";
    }
}
