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
@Table(name = "PersonalPage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonalPage.findAll", query = "SELECT p FROM PersonalPage p")
    , @NamedQuery(name = "PersonalPage.findByPageId", query = "SELECT p FROM PersonalPage p WHERE p.pageId = :pageId")})
public class PersonalPage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PageId")
    private Integer pageId;
    @ManyToMany(mappedBy = "personalPageCollection")
    private Collection<UserPlus> userPlusCollection;
    @JoinColumn(name = "OwnerId", referencedColumnName = "UserId")
    @ManyToOne
    private UserPlus ownerId;

    public PersonalPage() {
    }

    public PersonalPage(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    @XmlTransient
    public Collection<UserPlus> getUserPlusCollection() {
        return userPlusCollection;
    }

    public void setUserPlusCollection(Collection<UserPlus> userPlusCollection) {
        this.userPlusCollection = userPlusCollection;
    }

    public UserPlus getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UserPlus ownerId) {
        this.ownerId = ownerId;
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
        if (!(object instanceof PersonalPage)) {
            return false;
        }
        PersonalPage other = (PersonalPage) object;
        if ((this.pageId == null && other.pageId != null) || (this.pageId != null && !this.pageId.equals(other.pageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "profile.PersonalPage[ pageId=" + pageId + " ]";
    }
    
}
