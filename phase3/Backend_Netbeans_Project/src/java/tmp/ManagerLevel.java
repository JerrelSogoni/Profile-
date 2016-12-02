package tmp;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import profile.AdData;
import profile.Sales;
import profile.util.DataConnect;
import profile.util.JsfUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yunjoon_soh
 */
@Named("managerLevel")
@SessionScoped
public class ManagerLevel implements Serializable {

    private String queryMonth;
    private List<Sales> salesList;

    public String manage() {
        return "/employee/List";
    }

    public String salesReport() {
        return "/ManagerLevel/SalesReport";
    }

    public String listAds() {
        return null;
    }

    public String listTrans() {
        return null;
    }

    public String listRev() {
        return null;
    }

    public String vipCusRep() {
        return null;
    }

    public String vipCus() {
        return null;
    }

    public String vipItem() {
        return null;
    }

    public String listBuyer() {
        return null;
    }

    public String listItemByComp() {
        return null;
    }

    /**
     * @return the queryMonth
     */
    public String getQueryMonth() {
        return queryMonth;
    }

    /**
     * @param queryMonth the queryMonth to set
     */
    public void setQueryMonth(String queryMonth) {
        this.queryMonth = queryMonth;
    }

    public List<Sales> getSalesMonth() {
        salesList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM Sales WHERE MONTH(TransDate) = ?;");
            ps.setString(1, queryMonth);

            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //result found, means valid inputs
                Sales added = new Sales();

                added.setTransId(rs.getInt("TransId"));
                added.setTransDate(rs.getDate("TransDate"));
                added.setTransTime(rs.getDate("TransTime"));
                added.setS_adId(rs.getString("AdId"));
                added.setNumOfUnits(rs.getInt("NumOfUnits"));
                added.setAccountNum(rs.getInt("AccountNum"));

                salesList.add(added);
            }
        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            System.out.println("Login error -->" + ex.getMessage());
            return null;

        } finally {
            DataConnect.close(con);
        }
        return salesList;
    }
}
