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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yunjoon_soh
 */
@Entity
@Table(name = "GroupPlus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupPlus.findAll", query = "SELECT g FROM GroupPlus g")
    , @NamedQuery(name = "GroupPlus.findByGroupId", query = "SELECT g FROM GroupPlus g WHERE g.groupId = :groupId")
    , @NamedQuery(name = "GroupPlus.findByGroupName", query = "SELECT g FROM GroupPlus g WHERE g.groupName = :groupName")
    , @NamedQuery(name = "GroupPlus.findByType", query = "SELECT g FROM GroupPlus g WHERE g.type = :type")})
public class GroupPlus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GroupId")
    private Integer groupId;
    @Size(max = 50)
    @Column(name = "GroupName")
    private String groupName;
    @Size(max = 28)
    @Column(name = "Type")
    private String type;
    @ManyToMany(mappedBy = "groupPlusCollection")
    private Collection<UserPlus> userPlusCollection;
    @JoinTable(name = "HasAGroupPage", joinColumns = {
        @JoinColumn(name = "GroupId", referencedColumnName = "GroupId")}, inverseJoinColumns = {
        @JoinColumn(name = "GroupPageId", referencedColumnName = "PageId")})
    @ManyToMany
    private Collection<GroupPage> groupPageCollection;
    @ManyToMany(mappedBy = "groupPlusCollection1")
    private Collection<UserPlus> userPlusCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupPlus")
    private Collection<HasAccessToGroup> hasAccessToGroupCollection;
    @OneToMany(mappedBy = "groupId")
    private Collection<GroupPage> groupPageCollection1;
    @JoinColumn(name = "Owner", referencedColumnName = "UserId")
    @ManyToOne
    private UserPlus owner;

    public GroupPlus() {
    }

    public GroupPlus(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<UserPlus> getUserPlusCollection() {
        return userPlusCollection;
    }

    public void setUserPlusCollection(Collection<UserPlus> userPlusCollection) {
        this.userPlusCollection = userPlusCollection;
    }

    @XmlTransient
    public Collection<GroupPage> getGroupPageCollection() {
        return groupPageCollection;
    }

    public void setGroupPageCollection(Collection<GroupPage> groupPageCollection) {
        this.groupPageCollection = groupPageCollection;
    }

    @XmlTransient
    public Collection<UserPlus> getUserPlusCollection1() {
        return userPlusCollection1;
    }

    public void setUserPlusCollection1(Collection<UserPlus> userPlusCollection1) {
        this.userPlusCollection1 = userPlusCollection1;
    }

    @XmlTransient
    public Collection<HasAccessToGroup> getHasAccessToGroupCollection() {
        return hasAccessToGroupCollection;
    }

    public void setHasAccessToGroupCollection(Collection<HasAccessToGroup> hasAccessToGroupCollection) {
        this.hasAccessToGroupCollection = hasAccessToGroupCollection;
    }

    @XmlTransient
    public Collection<GroupPage> getGroupPageCollection1() {
        return groupPageCollection1;
    }

    public void setGroupPageCollection1(Collection<GroupPage> groupPageCollection1) {
        this.groupPageCollection1 = groupPageCollection1;
    }

    public UserPlus getOwner() {
        return owner;
    }

    public void setOwner(UserPlus owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupId != null ? groupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupPlus)) {
            return false;
        }
        GroupPlus other = (GroupPlus) object;
        if ((this.groupId == null && other.groupId != null) || (this.groupId != null && !this.groupId.equals(other.groupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "profile.GroupPlus[ groupId=" + groupId + " ]";
    }
    
}
