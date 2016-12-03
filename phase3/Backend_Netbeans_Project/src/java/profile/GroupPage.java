/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yunjoon_soh
 */
@Entity
@Table(name = "GroupPage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupPage.findAll", query = "SELECT g FROM GroupPage g")
    , @NamedQuery(name = "GroupPage.findByPageId", query = "SELECT g FROM GroupPage g WHERE g.pageId = :pageId")})
public class GroupPage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PageId")
    private Integer pageId;
    @ManyToMany(mappedBy = "groupPageCollection")
    private Collection<GroupPlus> groupPlusCollection;
    @JoinColumn(name = "GroupId", referencedColumnName = "GroupId")
    @ManyToOne
    private GroupPlus groupId;
    private Integer theGroupId;

    public GroupPage() {
    }

    public GroupPage(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    @XmlTransient
    public Collection<GroupPlus> getGroupPlusCollection() {
        return groupPlusCollection;
    }

    public void setGroupPlusCollection(Collection<GroupPlus> groupPlusCollection) {
        this.groupPlusCollection = groupPlusCollection;
    }

    public GroupPlus getGroupId() {
        return groupId;
    }

    public void setGroupId(GroupPlus groupId) {
        this.groupId = groupId;
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
        if (!(object instanceof GroupPage)) {
            return false;
        }
        GroupPage other = (GroupPage) object;
        if ((this.pageId == null && other.pageId != null) || (this.pageId != null && !this.pageId.equals(other.pageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "profile.GroupPage[ pageId=" + pageId + " ]";
    }

    /**
     * @return the theGroupId
     */
    public Integer getTheGroupId() {
        return theGroupId;
    }

    /**
     * @param theGroupId the theGroupId to set
     */
    public void setTheGroupId(Integer theGroupId) {
        this.theGroupId = theGroupId;
    }
    
}
