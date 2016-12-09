/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;

import profile.*;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Jerrel
 */
@Named("TheGpdsijfsdpifjroupComment")
@SessionScoped
public class TheGroupPostComments implements Serializable {

    private Integer commentId;
    private Date dateCreated;
    private String content;
    private UserPlus authorId;
    private UserPlus authorName;
    private Integer theAuthorId;
    private String theAuthorName;
    private String likeView;

    /**
     * @return the commentId
     */
    public TheGroupPostComments(){
        
    }
    public Integer getCommentId() {
        return commentId;
    }

    /**
     * @param commentId the commentId to set
     */
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
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
    public UserPlus getAuthorName() {
        return authorName;
    }

    /**
     * @param authorName the authorName to set
     */
    public void setAuthorName(UserPlus authorName) {
        this.authorName = authorName;
    }

    /**
     * @return the theAuthorId
     */
    public Integer getTheAuthorId() {
        return theAuthorId;
    }

    /**
     * @param theAuthorId the theAuthorId to set
     */
    public void setTheAuthorId(Integer theAuthorId) {
        this.theAuthorId = theAuthorId;
    }

    /**
     * @return the theAuthorName
     */
    public String getTheAuthorName() {
        return theAuthorName;
    }

    /**
     * @param theAuthorName the theAuthorName to set
     */
    public void setTheAuthorName(String theAuthorName) {
        this.theAuthorName = theAuthorName;
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
