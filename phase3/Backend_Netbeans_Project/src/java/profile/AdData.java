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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yunjoon_soh
 */
@Entity
@Table(name = "AdData")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdData.findAll", query = "SELECT a FROM AdData a")
    , @NamedQuery(name = "AdData.findByAdId", query = "SELECT a FROM AdData a WHERE a.adId = :adId")
    , @NamedQuery(name = "AdData.findByType", query = "SELECT a FROM AdData a WHERE a.type = :type")
    , @NamedQuery(name = "AdData.findByCompany", query = "SELECT a FROM AdData a WHERE a.company = :company")
    , @NamedQuery(name = "AdData.findByItemName", query = "SELECT a FROM AdData a WHERE a.itemName = :itemName")
    , @NamedQuery(name = "AdData.findByContent", query = "SELECT a FROM AdData a WHERE a.content = :content")
    , @NamedQuery(name = "AdData.findByUnitPrice", query = "SELECT a FROM AdData a WHERE a.unitPrice = :unitPrice")
    , @NamedQuery(name = "AdData.findByNumOfAvaUnits", query = "SELECT a FROM AdData a WHERE a.numOfAvaUnits = :numOfAvaUnits")})
public class AdData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "AdId")
    private Integer adId;
    @Size(max = 20)
    @Column(name = "Type")
    private String type;
    @Size(max = 50)
    @Column(name = "Company")
    private String company;
    @Size(max = 50)
    @Column(name = "ItemName")
    private String itemName;
    @Size(max = 200)
    @Column(name = "Content")
    private String content;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "UnitPrice")
    private Float unitPrice;
    @Column(name = "NumOfAvaUnits")
    private Integer numOfAvaUnits;
    @JoinColumn(name = "EmpId", referencedColumnName = "SSN")
    @ManyToOne(optional = false)
    private Employee empId;
    @OneToMany(mappedBy = "adId")
    private Collection<Sales> salesCollection;

    public AdData() {
    }

    public AdData(Integer adId) {
        this.adId = adId;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getNumOfAvaUnits() {
        return numOfAvaUnits;
    }

    public void setNumOfAvaUnits(Integer numOfAvaUnits) {
        this.numOfAvaUnits = numOfAvaUnits;
    }

    public Employee getEmpId() {
        return empId;
    }

    public void setEmpId(Employee empId) {
        this.empId = empId;
    }

    @XmlTransient
    public Collection<Sales> getSalesCollection() {
        return salesCollection;
    }

    public void setSalesCollection(Collection<Sales> salesCollection) {
        this.salesCollection = salesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adId != null ? adId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdData)) {
            return false;
        }
        AdData other = (AdData) object;
        if ((this.adId == null && other.adId != null) || (this.adId != null && !this.adId.equals(other.adId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "profile.AdData[ adId=" + adId + " ]";
    }
    
}
