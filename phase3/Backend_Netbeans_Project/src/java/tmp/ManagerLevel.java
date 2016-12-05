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
        return "/ManagerLevel/RevList";
    }

    public String vipCustRep() {
        return "/CustRepLevel/VipCustRep";
    }

    public String vipCust() {
        return "/CustRepLevel/VipCust";
    }

    public String vipItem() {
        return "/ManagerLevel/VipItem";
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

    private String queryItemType;

    /**
     * @return the queryItemType
     */
    public String getQueryItemType() {
        return queryItemType;
    }

    /**
     * @param queryItemType the queryItemType to set
     */
    public void setQueryItemType(String queryItemType) {
        this.queryItemType = queryItemType;
    }

    private List<Rev> revList;

    public class Rev {

        private String identifier;
        private Long rev;

        /**
         * @return the identifier
         */
        public String getIdentifier() {
            return identifier;
        }

        /**
         * @param identifier the identifier to set
         */
        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        /**
         * @return the rev
         */
        public Long getRev() {
            return rev;
        }

        /**
         * @param rev the rev to set
         */
        public void setRev(Long rev) {
            this.rev = rev;
        }

    }

    public List<Rev> getRevList() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();
        String action = params.get("target");

        JsfUtil.addErrorMessage("Action is " + action);

        setRevList(new ArrayList<>());
        Connection con = null;
        PreparedStatement ps = null;

        if (action == null) {
            return revList;
        } else {
            try {
                con = DataConnect.getConnection();
                if (action.equals("itemname")) {
                    ps = con.prepareStatement("SELECT\n"
                            + "    A.Itemname AS identifier, SUM(S.NumOfUnits * A.UnitPrice) AS total\n"
                            + "FROM\n"
                            + "    AdData A, Buy B, Sales S\n"
                            + "WHERE \n"
                            + "	S.TransId = B.TransId AND\n"
                            + "    S.AdId = A.AdId AND\n"
                            + "    A.AdId = ( SELECT AdId FROM AdData A2 WHERE A2.ItemName = ?)\n"
                            + "GROUP BY\n"
                            + "	A.Itemname; ");
                    ps.setString(1, queryItemName);
                } else if (action.equals("itemtype")) {
                    ps = con.prepareStatement("SELECT\n"
                            + "    A.Type AS identifier, SUM(S.NumOfUnits * A.UnitPrice) AS total\n"
                            + "FROM\n"
                            + "    AdData A, Buy B, Sales S\n"
                            + "WHERE \n"
                            + "	S.TransId = B.TransId AND\n"
                            + "    S.AdId = A.AdId AND\n"
                            + "    A.AdId in ( SELECT AdId FROM AdData A2 WHERE A2.Type = ?)\n"
                            + "GROUP BY\n"
                            + "	A.Type;");
                    ps.setString(1, queryItemType);
                } else if (action.equals("name")) {
                    ps = con.prepareStatement("SELECT\n"
                            + "    CONCAT(U.LastName, ' ', U.FirstName) AS identifier, SUM(S.NumOfUnits * A.UnitPrice) AS total\n"
                            + "FROM\n"
                            + "    AdData A, Buy B, Sales S, UserPlus U\n"
                            + "WHERE \n"
                            + "	S.TransId = B.TransId AND\n"
                            + "    S.AdId = A.AdId AND\n"
                            + "    B.UserId in (SELECT UserId FROM UserPlus U WHERE U.LastName = ? AND U.FirstName = ?) AND\n"
                            + "    B.UserId = U.UserId\n"
                            + "GROUP BY\n"
                            + "	B.UserId;");
                    ps.setString(1, queryLastName);
                    ps.setString(2, queryFirstName);
                }

                // print out the query statement
                JsfUtil.addErrorMessage(ps.toString());

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    //result found, means valid inputs
                    Rev added = new Rev();

                    added.setIdentifier(rs.getString("identifier"));
                    added.setRev(rs.getLong("total"));

                    revList.add(added);
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
        return revList;
    }

    private List<VipCustRep> vipCustRepList;

    public class VipCustRep {

        private String total, empName;

        /**
         * @return the total
         */
        public String getTotal() {
            return total;
        }

        /**
         * @param total the total to set
         */
        public void setTotal(String total) {
            this.total = total;
        }

        /**
         * @return the empName
         */
        public String getEmpName() {
            return empName;
        }

        /**
         * @param empName the empName to set
         */
        public void setEmpName(String empName) {
            this.empName = empName;
        }
    }

    /**
     * @param revList the revList to set
     */
    public void setRevList(List<Rev> revList) {
        this.revList = revList;
    }

    /**
     * @return the vipCustRepList
     */
    public List<VipCustRep> getVipCustRepList() {
        vipCustRepList = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;

        String s = "SELECT \n"
                + "    T.who AS empName, SUM(sales) AS total\n"
                + "FROM\n"
                + "    (SELECT \n"
                + "        B.EmpId AS who, (A.UnitPrice * S.NumOfUnits) AS sales\n"
                + "    FROM\n"
                + "        AdData A, Sales S, Buy B\n"
                + "    WHERE\n"
                + "        A.AdId = S.AdId\n"
                + "            AND B.TransId = S.TransId) T\n"
                + "GROUP BY T.who\n"
                + "ORDER BY total DESC;";

        try {
            con = DataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(s);

            // print out the query statement
            JsfUtil.addErrorMessage(s);

            rs = ps.executeQuery();
            while (rs.next()) {
                VipCustRep vcr = new VipCustRep();

                vcr.empName = rs.getString("empName");
                vcr.total = rs.getString("total");

                vipCustRepList.add(vcr);
            }
        } catch (SQLException ex) {
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            System.out.println("Login error -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
        }

        return vipCustRepList;
    }

    /**
     * @param vipCustRepList the vipCustRepList to set
     */
    public void setVipCustRepList(List<VipCustRep> vipCustRepList) {
        this.vipCustRepList = vipCustRepList;
    }

    private List<VipCust> vipCustList;

    public class VipCust {

        private String custName, total;

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }

    public List<VipCust> getVipCustList() {
        vipCustList = new ArrayList<>();
        Connection con = null;
        ResultSet rs;

        String s = "SELECT \n"
                + "    T.who AS custName, SUM(bought) AS total\n"
                + "FROM\n"
                + "    (SELECT \n"
                + "        B.UserId AS who, (A.UnitPrice * S.NumOfUnits) AS bought\n"
                + "    FROM\n"
                + "        AdData A, Sales S, Buy B\n"
                + "    WHERE\n"
                + "        A.AdId = S.AdId\n"
                + "            AND B.TransId = S.TransId) T\n"
                + "GROUP BY T.who\n"
                + "ORDER BY total DESC;";

        try {
            con = DataConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(s);

            // print out the query statement
            JsfUtil.addErrorMessage(s);

            rs = ps.executeQuery();
            while (rs.next()) {
                VipCust vcr = new VipCust();

                vcr.custName = rs.getString("custName");
                vcr.total = rs.getString("total");

                vipCustList.add(vcr);
            }
        } catch (SQLException ex) {
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            System.out.println("Login error -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
        }

        return vipCustList;
    }

    public void setVipCustList(List<VipCust> vipCustList) {
        this.vipCustList = vipCustList;
    }

}
