/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;
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
    
}
