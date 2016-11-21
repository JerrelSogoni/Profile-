/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yunjoon_soh
 */
@Entity
@Table(name = "PagePlus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagePlus.findAll", query = "SELECT p FROM PagePlus p")
    , @NamedQuery(name = "PagePlus.findByPageId", query = "SELECT p FROM PagePlus p WHERE p.pageId = :pageId")
    , @NamedQuery(name = "PagePlus.findByPostCount", query = "SELECT p FROM PagePlus p WHERE p.postCount = :postCount")})
public class PagePlus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PageId")
    private Integer pageId;
    @Column(name = "PostCount")
    private Integer postCount;
    @JoinTable(name = "PostedTo", joinColumns = {
        @JoinColumn(name = "PageId", referencedColumnName = "PageId")}, inverseJoinColumns = {
        @JoinColumn(name = "PostId", referencedColumnName = "PostId")})
    @ManyToMany
    private Collection<Post> postCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pagePlus")
    private Collection<HasAccessToGroup> hasAccessToGroupCollection;

    public PagePlus() {
    }

    public PagePlus(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    @XmlTransient
    public Collection<Post> getPostCollection() {
        return postCollection;
    }

    public void setPostCollection(Collection<Post> postCollection) {
        this.postCollection = postCollection;
    }

    @XmlTransient
    public Collection<HasAccessToGroup> getHasAccessToGroupCollection() {
        return hasAccessToGroupCollection;
    }

    public void setHasAccessToGroupCollection(Collection<HasAccessToGroup> hasAccessToGroupCollection) {
        this.hasAccessToGroupCollection = hasAccessToGroupCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pageId != null ? pageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagePlus)) {
            return false;
        }
        PagePlus other = (PagePlus) object;
        if ((this.pageId == null && other.pageId != null) || (this.pageId != null && !this.pageId.equals(other.pageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "profile.PagePlus[ pageId=" + pageId + " ]";
    }
    
}
