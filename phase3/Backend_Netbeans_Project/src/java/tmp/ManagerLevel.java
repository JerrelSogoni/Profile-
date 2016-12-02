package tmp;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import profile.AdData;
import profile.Employee;
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
    private List<Sales_Wrapper> salesList;

    public String manage() {
        return "/employee/List";
    }

    public String salesReport() {
        return "/ManagerLevel/SalesReport";
    }

    public String listAds() {
        return "/ManagerLevel/AdsList";
    }

    public String listTrans() {
        return "/ManagerLevel/TransList";
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

    public List<Sales_Wrapper> getSalesMonth() {
        setSalesList(new ArrayList<>());
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
                Sales_Wrapper added = new Sales_Wrapper();

                added.sales.setTransId(rs.getInt("TransId"));
                added.sales.setTransDate(rs.getDate("TransDate"));
                added.sales.setTransTime(rs.getDate("TransTime"));
                added.setS_adId(rs.getString("AdId"));
                added.sales.setNumOfUnits(rs.getInt("NumOfUnits"));
                added.sales.setAccountNum(rs.getInt("AccountNum"));

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

    public class Sales_Wrapper {

        private Sales sales;
        private String s_adId;

        public Sales_Wrapper() {
            sales = new Sales();
        }

        /**
         * @return the sales
         */
        public Sales getSales() {
            return sales;
        }

        /**
         * @param sales the sales to set
         */
        public void setSales(Sales sales) {
            this.sales = sales;
        }

        /**
         * @return the s_adId
         */
        public String getS_adId() {
            return s_adId;
        }

        /**
         * @param s_adId the s_adId to set
         */
        public void setS_adId(String s_adId) {
            this.s_adId = s_adId;
        }
    }

    private String queryItemName;
    private String queryLastName;
    private String queryFirstName;
    private List<Trans> transList;

    public class Trans {

        private Sales sales;
        private AdData adData;
        private Employee employee;

        public Trans() {
            sales = new Sales();
            adData = new AdData();
            employee = new Employee();
        }

        /**
         * @return the sales
         */
        public Sales getSales() {
            return sales;
        }

        /**
         * @param sales the sales to set
         */
        public void setSales(Sales sales) {
            this.sales = sales;
        }

        /**
         * @return the employee
         */
        public Employee getEmployee() {
            return employee;
        }

        /**
         * @param employee the employee to set
         */
        public void setEmployee(Employee employee) {
            this.employee = employee;
        }

        /**
         * @return the adData
         */
        public AdData getAdData() {
            return adData;
        }

        /**
         * @param adData the adData to set
         */
        public void setAdData(AdData adData) {
            this.adData = adData;
        }

    }

    /**
     * @return the salesList
     */
    public List<Sales_Wrapper> getSalesList() {
        return salesList;
    }

    /**
     * @param salesList the salesList to set
     */
    public void setSalesList(List<Sales_Wrapper> salesList) {
        this.salesList = salesList;
    }

    /**
     * @return the queryItemName
     */
    public String getQueryItemName() {
        return queryItemName;
    }

    /**
     * @param queryItemName the queryItemName to set
     */
    public void setQueryItemName(String queryItemName) {
        this.queryItemName = queryItemName;
    }

    /**
     * @return the queryLastName
     */
    public String getQueryLastName() {
        return queryLastName;
    }

    /**
     * @param queryLastName the queryLastName to set
     */
    public void setQueryLastName(String queryLastName) {
        this.queryLastName = queryLastName;
    }

    /**
     * @return the queryFirstName
     */
    public String getQueryFirstName() {
        return queryFirstName;
    }

    /**
     * @param queryFirstName the queryFirstName to set
     */
    public void setQueryFirstName(String queryFirstName) {
        this.queryFirstName = queryFirstName;
    }

    /**
     * @return the transList
     */
    public List<Trans> getTransList() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();
        String action = params.get("target");

        JsfUtil.addErrorMessage("Action is " + action);
        transList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        if (action == null) {
            return transList;
        } else {
            try {
                con = DataConnect.getConnection();
                if (action.equals("item")) {
                    ps = con.prepareStatement("select S.TransId, S.TransDate, S.TransTime, S.NumOfUnits, S.AccountNum, A.AdId, A.Type, A.Company, A.ItemName, A.Content, A.UnitPrice, A.NumOfAvaUnits from sales S inner join AdData A on S.AdId = A.AdId WHERE A.ItemName = ?;");
                    ps.setString(1, queryItemName);
                } else if (action.equals("name")) {
                    ps = con.prepareStatement("select * from Buy B inner join Sales S on B.TransId = S.TransId inner join UserPlus U on B.UserId=U.UserId inner join AdData A on A.AdId = S.AdId WHERE U.FirstName=? AND U.LastName=?;");
                    ps.setString(1, queryFirstName);
                    ps.setString(2, queryLastName);
                }
                // print out the query statement
                JsfUtil.addErrorMessage(ps.toString());
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    //result found, means valid inputs
                    Trans added = new Trans();

                    added.sales.setTransId(rs.getInt("TransId"));
                    added.sales.setTransDate(rs.getDate("TransDate"));
                    added.sales.setTransTime(rs.getDate("TransTime"));
                    added.sales.setNumOfUnits(rs.getInt("NumOfUnits"));
                    added.sales.setAccountNum(rs.getInt("AccountNum"));

                    added.adData.setAdId(rs.getInt("AdId"));
                    added.adData.setType(rs.getString("Type"));
                    added.adData.setCompany(rs.getString("Company"));
                    added.adData.setItemName(rs.getString("ItemName"));
                    added.adData.setContent(rs.getString("Content"));
                    added.adData.setUnitPrice(rs.getFloat("UnitPrice"));
                    added.adData.setNumOfAvaUnits(rs.getInt("NumOfAvaUnits"));

                    transList.add(added);
                }
            } catch (SQLException ex) {

                // print out error message
                JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
                System.out.println("Login error -->" + ex.getMessage());
                return null;

            } finally {
                DataConnect.close(con);
            }
        }
        return transList;
    }

    /**
     * @param transList the transList to set
     */
    public void setTransList(List<Trans> transList) {
        this.transList = transList;
    }
}
