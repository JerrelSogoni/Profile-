/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;
import profile.util.PaginationHelper;

/**
 *
 * @author Jerrel
 */
@Named("TheGroupCommentController")
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

}
