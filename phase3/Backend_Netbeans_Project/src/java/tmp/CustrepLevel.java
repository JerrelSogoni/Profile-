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
import profile.Employee;
import profile.GroupPlus;
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

    private String queryCompany = "";
    private List<UserPlus> mailingList;
    
    private String queryCustomer = "";
    private List<AdData> suggestionList;
    
    private List<GroupPlus> groupList;

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
        return "/CustRepLevel/ListCustSug";
    }

    public String listCustGrp() {
        return "/CustRepLevel/ListCustGrp";
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
        mailingList = new ArrayList<>();
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
                mailingList.add(added);
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
        return mailingList;
    }

    /**
     * @param mailingList the mailingList to set
     */
    public void setMailingList(List<UserPlus> mailingList) {
        this.mailingList = mailingList;
    }

    /**
     * @return the queryCustomer
     */
    public String getQueryCustomer() {
        return queryCustomer;
    }

    /**
     * @param queryCustomer the queryCustomer to set
     */
    public void setQueryCustomer(String queryCustomer) {
        this.queryCustomer = queryCustomer;
    }

    /**
     * @return the suggestionList
     */
    public List<AdData> getSuggestionList() {
        suggestionList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM AdData A1 WHERE A1.Type IN ("
                    + " SELECT Type FROM AdData A2, Buy B, Sales S WHERE"
                    + " B.TransId = S.TransId AND S.AdId = A2.AdId AND B.UserId = ?);");
            if(!queryCustomer.equals(""))
                ps.setInt(1, Integer.parseInt(queryCustomer));

            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //result found, means valid inputs
                AdData added = new AdData();

                added.setAdId(rs.getInt("adId"));
                added.setType(rs.getString("Type"));
                added.setCompany(rs.getString("Company"));
                added.setEmpId(new Employee(rs.getString("EmpId")));
                added.setItemName(rs.getString("ItemName"));
                added.setContent(rs.getString("Content"));
                added.setUnitPrice(rs.getFloat("UnitPrice"));
                added.setNumOfAvaUnits(rs.getInt("NumOfAvaUnits"));
                
                suggestionList.add(added);
            }
        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            System.out.println("Login error -->" + ex.getMessage());
            return null;

        } finally {
            DataConnect.close(con);
        }
        return suggestionList;
    }

    /**
     * @param suggestionList the suggestionList to set
     */
    public void setSuggestionList(List<AdData> suggestionList) {
        this.suggestionList = suggestionList;
    }

    /**
     * @return the groupList
     */
    public List<GroupPlus> getGroupList() {
        groupList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT G.GroupName FROM GroupPlus G WHERE EXISTS ("
                    + " SELECT * FROM HasAccessToGroup H WHERE H.UserId = ? AND H.GroupID = G.GroupID);");
            if(!queryCustomer.equals(""))
                ps.setInt(1, Integer.parseInt(queryCustomer));

            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //result found, means valid inputs
                GroupPlus added = new GroupPlus();

                added.setGroupName(rs.getString("GroupName"));
                
                groupList.add(added);
            }
        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            System.out.println("Login error -->" + ex.getMessage());
            return null;

        } finally {
            DataConnect.close(con);
        }
        return groupList;
    }

    /**
     * @param groupList the groupList to set
     */
    public void setGroupList(List<GroupPlus> groupList) {
        this.groupList = groupList;
    }
}
