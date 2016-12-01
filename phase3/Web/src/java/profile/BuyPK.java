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
import javax.validation.constraints.Size;

/**
 *
 * @author yunjoon_soh
 */
@Embeddable
public class BuyPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "TransId")
    private int transId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "EmpId")
    private String empId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UserId")
    private int userId;

    public BuyPK() {
    }

    public BuyPK(int transId, String empId, int userId) {
        this.transId = transId;
        this.empId = empId;
        this.userId = userId;
    }

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) transId;
        hash += (empId != null ? empId.hashCode() : 0);
        hash += (int) userId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BuyPK)) {
            return false;
        }
        BuyPK other = (BuyPK) object;
        if (this.transId != other.transId) {
            return false;
        }
        if ((this.empId == null && other.empId != null) || (this.empId != null && !this.empId.equals(other.empId))) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "profile.BuyPK[ transId=" + transId + ", empId=" + empId + ", userId=" + userId + " ]";
    }
    
}
