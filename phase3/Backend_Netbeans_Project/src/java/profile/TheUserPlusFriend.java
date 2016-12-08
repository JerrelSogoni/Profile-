/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author yunjoon_soh
 */
@Entity
@Named("TheUserPlusFriend")
@SessionScoped
public class TheUserPlusFriend implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String friendName;
    private int friendId;
    private String friendEmail;

    /**
     * @return the friendName
     */
    public String getFriendName() {
        return friendName;
    }

    /**
     * @param friendName the friendName to set
     */
    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    /**
     * @return the friendId
     */
    public int getFriendId() {
        return friendId;
    }

    /**
     * @param friendId the friendId to set
     */
    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    /**
     * @return the friendEmail
     */
    public String getFriendEmail() {
        return friendEmail;
    }

    /**
     * @param friendEmail the friendEmail to set
     */
    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }
}
    