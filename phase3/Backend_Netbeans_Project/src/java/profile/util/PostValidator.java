/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jerrel
 */
public class PostValidator {
     public static boolean validate(String email) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select * from UserPlus where email = ?");
            ps.setString(1, email);
            
            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //result found, means valid inputs
                return true;
            }
        } catch (SQLException ex) {
            
            // print out error message
            JsfUtil.addErrorMessage("Invalid: email Address" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
        }
        return false;
    }
     
     public static int getPostToID(String email){
                 Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select * from UserPlus where email = ?");
            ps.setString(1, email);
            
            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //result found, means valid inputs
                return rs.getInt("userId");
            }
        } catch (SQLException ex) {
            
            // print out error message
            JsfUtil.addErrorMessage("Invalid: email Address" + ex.getMessage());
            return -1;
        } finally {
            DataConnect.close(con);
        }
        return -1;
         
     }
    public static int getSearchID(String email) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select * from UserPlus where email = ?");
            ps.setString(1, email);
            
            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
               
                return rs.getInt("UserId");
            }
        } catch (SQLException ex) {
            
            // print out error message
            JsfUtil.addErrorMessage("Invalid: email Address" + ex.getMessage());
            return -1;
        } finally {
            DataConnect.close(con);
        }
        return -1;
    }
     
    
    
 
    
}
