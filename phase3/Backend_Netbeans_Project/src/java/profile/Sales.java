/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yunjoon_soh
 */
@Entity
@Table(name = "Sales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sales.findAll", query = "SELECT s FROM Sales s")
    , @NamedQuery(name = "Sales.findByTransId", query = "SELECT s FROM Sales s WHERE s.transId = :transId")
    , @NamedQuery(name = "Sales.findByTransDate", query = "SELECT s FROM Sales s WHERE s.transDate = :transDate")
    , @NamedQuery(name = "Sales.findByTransTime", query = "SELECT s FROM Sales s WHERE s.transTime = :transTime")
    , @NamedQuery(name = "Sales.findByNumOfUnits", query = "SELECT s FROM Sales s WHERE s.numOfUnits = :numOfUnits")
    , @NamedQuery(name = "Sales.findByAccountNum", query = "SELECT s FROM Sales s WHERE s.accountNum = :accountNum")})
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TransId")
    private Integer transId;
    @Column(name = "TransDate")
    @Temporal(TemporalType.DATE)
    private Date transDate;
    @Column(name = "TransTime")
    @Temporal(TemporalType.TIME)
    private Date transTime;
    @Column(name = "NumOfUnits")
    private Integer numOfUnits;
    @Column(name = "AccountNum")
    private Integer accountNum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sales")
    private Collection<Buy> buyCollection;
    @JoinColumn(name = "AdId", referencedColumnName = "AdId")
    @ManyToOne
    private AdData adId;

    public Sales() {
    }

    public Sales(Integer transId) {
        this.transId = transId;
    }

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public Integer getNumOfUnits() {
        return numOfUnits;
    }

    public void setNumOfUnits(Integer numOfUnits) {
        this.numOfUnits = numOfUnits;
    }

    public Integer getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Integer accountNum) {
        this.accountNum = accountNum;
    }

    @XmlTransient
    public Collection<Buy> getBuyCollection() {
        return buyCollection;
    }

    public void setBuyCollection(Collection<Buy> buyCollection) {
        this.buyCollection = buyCollection;
    }

    public AdData getAdId() {
        return adId;
    }

    public void setAdId(AdData adId) {
        this.adId = adId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transId != null ? transId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sales)) {
            return false;
        }
        Sales other = (Sales) object;
        if ((this.transId == null && other.transId != null) || (this.transId != null && !this.transId.equals(other.transId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "profile.Sales[ transId=" + transId + " ]";
    }
    
}
