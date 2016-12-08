package profile;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;

@Named("TheGroupUserPlusController")
@SessionScoped
public class TheGroupUserPlusController implements Serializable {
    public TheGroupUserPlusController(){
        
    }
    private TheUserPlusFriend current;
    private DataModel items = null;

    private String friendInput;

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


}
