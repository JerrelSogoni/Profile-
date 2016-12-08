/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Jerrel
 */
@Named("TheGroupPost")
@SessionScoped
public class TheGroupPost implements Serializable {

    private Integer postId;
    private Date dateCreated;

    private String content;
    private Integer commentCount;
    private UserPlus authorId;
    private String authorName;
    private String authorEmail;
    private Integer theauthorId;
    // default value, Like = neutral , Liked == Liked, Unliked = Like
    private String likeView;

    /**
     * @return the postId
     */
    public Integer getPostId() {
        return postId;
    }

    /**
     * @param postId the postId to set
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the commentCount
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * @param commentCount the commentCount to set
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * @return the authorId
     */
    public UserPlus getAuthorId() {
        return authorId;
    }

    /**
     * @param authorId the authorId to set
     */
    public void setAuthorId(UserPlus authorId) {
        this.authorId = authorId;
    }

    /**
     * @return the authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * @param authorName the authorName to set
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * @return the authorEmail
     */
    public String getAuthorEmail() {
        return authorEmail;
    }

    /**
     * @param authorEmail the authorEmail to set
     */
    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    /**
     * @return the theauthorId
     */
    public Integer getTheauthorId() {
        return theauthorId;
    }

    /**
     * @param theauthorId the theauthorId to set
     */
    public void setTheauthorId(Integer theauthorId) {
        this.theauthorId = theauthorId;
    }

    /**
     * @return the likeView
     */
    public String getLikeView() {
        return likeView;
    }

    /**
     * @param likeView the likeView to set
     */
    public void setLikeView(String likeView) {
        this.likeView = likeView;
    }
    
}
