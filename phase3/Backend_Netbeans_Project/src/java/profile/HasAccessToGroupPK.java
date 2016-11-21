/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author yunjoon_soh
 */
@Embeddable
public class HasAccessToGroupPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "UserId")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PageId")
    private int pageId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GroupId")
    private int groupId;

    public HasAccessToGroupPK() {
    }

    public HasAccessToGroupPK(int userId, int pageId, int groupId) {
        this.userId = userId;
        this.pageId = pageId;
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) pageId;
        hash += (int) groupId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HasAccessToGroupPK)) {
            return false;
        }
        HasAccessToGroupPK other = (HasAccessToGroupPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.pageId != other.pageId) {
            return false;
        }
        if (this.groupId != other.groupId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "profile.HasAccessToGroupPK[ userId=" + userId + ", pageId=" + pageId + ", groupId=" + groupId + " ]";
    }
    
}
