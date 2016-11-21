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
@Table(name = "Buy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Buy.findAll", query = "SELECT b FROM Buy b")
    , @NamedQuery(name = "Buy.findByTransId", query = "SELECT b FROM Buy b WHERE b.buyPK.transId = :transId")
    , @NamedQuery(name = "Buy.findByEmpId", query = "SELECT b FROM Buy b WHERE b.buyPK.empId = :empId")
    , @NamedQuery(name = "Buy.findByUserId", query = "SELECT b FROM Buy b WHERE b.buyPK.userId = :userId")})
public class Buy implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BuyPK buyPK;
    @JoinColumn(name = "TransId", referencedColumnName = "TransId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sales sales;
    @JoinColumn(name = "EmpId", referencedColumnName = "SSN", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employee;
    @JoinColumn(name = "UserId", referencedColumnName = "UserId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UserPlus userPlus;

    public Buy() {
    }

    public Buy(BuyPK buyPK) {
        this.buyPK = buyPK;
    }

    public Buy(int transId, String empId, int userId) {
        this.buyPK = new BuyPK(transId, empId, userId);
    }

    public BuyPK getBuyPK() {
        return buyPK;
    }

    public void setBuyPK(BuyPK buyPK) {
        this.buyPK = buyPK;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public UserPlus getUserPlus() {
        return userPlus;
    }

    public void setUserPlus(UserPlus userPlus) {
        this.userPlus = userPlus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buyPK != null ? buyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Buy)) {
            return false;
        }
        Buy other = (Buy) object;
        if ((this.buyPK == null && other.buyPK != null) || (this.buyPK != null && !this.buyPK.equals(other.buyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "profile.Buy[ buyPK=" + buyPK + " ]";
    }
    
}
