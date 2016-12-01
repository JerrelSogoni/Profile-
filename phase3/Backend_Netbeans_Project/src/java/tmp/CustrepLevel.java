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
import profile.UserPlus;
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
@Named("custrepLevel")
@SessionScoped
public class CustrepLevel implements Serializable {

    private String queryCompany;

    private List<UserPlus> mailingList;

    public String addAd() {
        return "/adData/List";
    }

    public String rmAd() {
        return "/adData/List";
    }

    public String addTrans() {
        return null;
    }

    public String editCustomerInfo() {
        return null;
    }

    public String listCust() {

        return "/CustRepLevel/ListCust";
    }

    public String listCustSug() {
        return null;
    }

    public String listCustGrp() {
        return null;
    }

    public String custHistory() {
        return null;
    }

    public String bestSeller() {
        return null;
    }

    public String itemSuggestion() {
        return null;
    }

    /**
     * @return the queryCompany
     */
    public String getQueryCompany() {
        return queryCompany;
    }

    /**
     * @param queryCompany the queryCompany to set
     */
    public void setQueryCompany(String queryCompany) {
        this.queryCompany = queryCompany;
    }

    public List<UserPlus> getMailingList() {
        ArrayList<UserPlus> toRet = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT A.Company, U.Email FROM Buy B, Sales S, AdData A, UserPlus U"
                    + " WHERE B.TransId = S.TransId AND B.UserId = U.UserId AND A.AdId = S.AdId AND A.Company = ?;");
            ps.setString(1, queryCompany);

            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //result found, means valid inputs
                UserPlus added = new UserPlus();
                added.setEmail(rs.getString("Email"));
                toRet.add(added);
                JsfUtil.addErrorMessage("Added to return list " + rs.getString("Email"));
            }
        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            System.out.println("Login error -->" + ex.getMessage());
            return null;

        } finally {
            DataConnect.close(con);
        }
        return toRet;
    }

    /**
     * @param mailingList the mailingList to set
     */
    public void setMailingList(List<UserPlus> mailingList) {
        this.mailingList = mailingList;
    }
}
