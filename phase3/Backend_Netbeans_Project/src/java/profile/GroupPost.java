/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

/**
 *
 * @author Jerrel
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yunjoon_soh
 */
@Entity
@Table(name = "groupPost")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p")
    , @NamedQuery(name = "Post.findByPostId", query = "SELECT p FROM Post p WHERE p.postId = :postId")
    , @NamedQuery(name = "Post.findByDateCreated", query = "SELECT p FROM Post p WHERE p.dateCreated = :dateCreated")
    , @NamedQuery(name = "Post.findByContent", query = "SELECT p FROM Post p WHERE p.content = :content")
    , @NamedQuery(name = "Post.findByCommentCount", query = "SELECT p FROM Post p WHERE p.commentCount = :commentCount")})
public class GroupPost implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PostId")
    private Integer postId;
    @Column(name = "DateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Size(max = 100)
    @Column(name = "Content")
    private String content;
    @Column(name = "CommentCount")
    private Integer commentCount;
    @JoinTable(name = "LikesPost", joinColumns = {
        @JoinColumn(name = "PostId", referencedColumnName = "PostId")}, inverseJoinColumns = {
        @JoinColumn(name = "UserId", referencedColumnName = "UserId")})
    @ManyToMany
    private Collection<UserPlus> userPlusCollection;
    @ManyToMany(mappedBy = "postCollection")
    private Collection<Comment> commentCollection;
    @ManyToMany(mappedBy = "postCollection")
    private Collection<PagePlus> pagePlusCollection;
    @JoinColumn(name = "AuthorId", referencedColumnName = "UserId")
    @ManyToOne
    private UserPlus authorId;
    private String authorName;
    private String authorEmail;
    private Integer theauthorId;
    

    public GroupPost() {
    }
    public void setAuthorName(String authorName){
        this.authorName = authorName;
    }
    public String getAuthorName(){
        return authorName;
    }
    public GroupPost(Integer postId) {
        this.postId = postId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    @XmlTransient
    public Collection<UserPlus> getUserPlusCollection() {
        return userPlusCollection;
    }

    public void setUserPlusCollection(Collection<UserPlus> userPlusCollection) {
        this.userPlusCollection = userPlusCollection;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    @XmlTransient
    public Collection<PagePlus> getPagePlusCollection() {
        return pagePlusCollection;
    }

    public void setPagePlusCollection(Collection<PagePlus> pagePlusCollection) {
        this.pagePlusCollection = pagePlusCollection;
    }

    public UserPlus getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UserPlus authorId) {
        this.authorId = authorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postId != null ? postId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.postId == null && other.getPostId() != null) || (this.postId != null && !this.postId.equals(other.getPostId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "profile.Post[ postId=" + postId + " ]";
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
}
