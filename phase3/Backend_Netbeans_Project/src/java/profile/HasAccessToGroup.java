/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yunjoon_soh
 */
@Entity
@Table(name = "HasAccessToGroup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HasAccessToGroup.findAll", query = "SELECT h FROM HasAccessToGroup h")
    , @NamedQuery(name = "HasAccessToGroup.findByUserId", query = "SELECT h FROM HasAccessToGroup h WHERE h.hasAccessToGroupPK.userId = :userId")
    , @NamedQuery(name = "HasAccessToGroup.findByPageId", query = "SELECT h FROM HasAccessToGroup h WHERE h.hasAccessToGroupPK.pageId = :pageId")
    , @NamedQuery(name = "HasAccessToGroup.findByGroupId", query = "SELECT h FROM HasAccessToGroup h WHERE h.hasAccessToGroupPK.groupId = :groupId")})
public class HasAccessToGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HasAccessToGroupPK hasAccessToGroupPK;
    @JoinColumn(name = "UserId", referencedColumnName = "UserId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UserPlus userPlus;
    @JoinColumn(name = "PageId", referencedColumnName = "PageId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PagePlus pagePlus;
    @JoinColumn(name = "GroupId", referencedColumnName = "GroupId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GroupPlus groupPlus;
    @JoinColumn(name = "AdderId", referencedColumnName = "UserId")
    @ManyToOne
    private UserPlus adderId;

    public HasAccessToGroup() {
    }

    public HasAccessToGroup(HasAccessToGroupPK hasAccessToGroupPK) {
        this.hasAccessToGroupPK = hasAccessToGroupPK;
    }

    public HasAccessToGroup(int userId, int pageId, int groupId) {
        this.hasAccessToGroupPK = new HasAccessToGroupPK(userId, pageId, groupId);
    }

    public HasAccessToGroupPK getHasAccessToGroupPK() {
        return hasAccessToGroupPK;
    }

    public void setHasAccessToGroupPK(HasAccessToGroupPK hasAccessToGroupPK) {
        this.hasAccessToGroupPK = hasAccessToGroupPK;
    }

    public UserPlus getUserPlus() {
        return userPlus;
    }

    public void setUserPlus(UserPlus userPlus) {
        this.userPlus = userPlus;
    }

    public PagePlus getPagePlus() {
        return pagePlus;
    }

    public void setPagePlus(PagePlus pagePlus) {
        this.pagePlus = pagePlus;
    }

    public GroupPlus getGroupPlus() {
        return groupPlus;
    }

    public void setGroupPlus(GroupPlus groupPlus) {
        this.groupPlus = groupPlus;
    }

    public UserPlus getAdderId() {
        return adderId;
    }

    public void setAdderId(UserPlus adderId) {
        this.adderId = adderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hasAccessToGroupPK != null ? hasAccessToGroupPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HasAccessToGroup)) {
            return false;
        }
        HasAccessToGroup other = (HasAccessToGroup) object;
        if ((this.hasAccessToGroupPK == null && other.hasAccessToGroupPK != null) || (this.hasAccessToGroupPK != null && !this.hasAccessToGroupPK.equals(other.hasAccessToGroupPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "profile.HasAccessToGroup[ hasAccessToGroupPK=" + hasAccessToGroupPK + " ]";
    }
    
}
