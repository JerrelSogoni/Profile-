/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;
import profile.util.DataConnect;
import profile.util.JsfUtil;
import profile.util.PostValidator;
import profile.util.SessionUtils;
/*
Jane 
the purpose of this controller is to load the the groups by the user
*/
/**
 *
 * @author Jerrel
 */
@Named("theGroupController")
@SessionScoped
public class TheGroupController implements Serializable {
    private TheUserPlusFriend current;
    private DataModel items = null;

    public TheGroupController() {
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
    public void createGroup(){
                Connection con = null;
        PreparedStatement ps = null;
       

            try {
                con = DataConnect.getConnection();
                ps = con.prepareStatement("INSERT INTO FriendsWith(UserId,FriendId) VALUES(? ,? );");
                // print out the query statement
                ps.setInt(1, SessionUtils.getUserId());
      
                //   JsfUtil.addErrorMessage(ps.toString());
                ps.execute();

            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());

            } finally {
                DataConnect.close(con);
  
            }

        }
    
    public void getGroupList(){
        
    }

    
}
