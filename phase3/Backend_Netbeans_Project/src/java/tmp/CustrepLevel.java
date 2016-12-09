package tmp;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        return "/CustRepLevel/AddTransaction";
    }

    public String editCustomerInfo() {
        return "/userPlus/List";
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
        return "/CustRepLevel/AccountHistory";
    }

    public String bestSeller() {
        return "/CustRepLevel/BestSeller";
    }

    public String itemSuggestion() {
        return "/CustRepLevel/ItemSuggestion";
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
            ps = con.prepareStatement("SELECT DISTINCT A.Company, U.Email FROM Buy B, Sales S, AdData A, UserPlus U"
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
            }
        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            
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
            if (!queryCustomer.equals("")) {
                ps.setInt(1, Integer.parseInt(queryCustomer));
            }

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
            if (!queryCustomer.equals("")) {
                ps.setInt(1, Integer.parseInt(queryCustomer));
            }

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

    private List<ItemSuggestion> suggestedItemList;

    public class ItemSuggestion {

        private String itemName;
        private String itemType;

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

    }

    public List<ItemSuggestion> getSuggestedItemList() {
        suggestedItemList = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT U.Preferences FROM UserPlus U WHERE U.UserId = ?;");
            if (queryUser != null) {
                ps.setInt(1, queryUser.getUserId());
            }

            ResultSet rs = ps.executeQuery();

            rs.next();
            String preferences = rs.getString("Preferences");

            String[] splitted = preferences.split(",");

            for (String s : splitted) {
                ps = con.prepareStatement("SELECT A.ItemName AS ItemName, A.Type AS ItemType FROM AdData A, UserPlus U WHERE U.Userid = ? AND A.Type LIKE ?;");
                if (queryUser != null) {
                    ps.setInt(1, queryUser.getUserId());
                }
                ps.setString(2, s);

                JsfUtil.addErrorMessage(ps.toString());
                rs = ps.executeQuery();                

                while (rs.next()) {
                    //result found, means valid inputs
                    ItemSuggestion added = new ItemSuggestion();

                    added.setItemName(rs.getString("ItemName"));
                    added.setItemType(rs.getString("ItemType"));

                    suggestedItemList.add(added);
                }
            }

        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            
            return null;

        } finally {
            DataConnect.close(con);
        }

        return suggestedItemList;
    }

    public void setSuggestedItemList(List<ItemSuggestion> suggestedItemList) {
        this.suggestedItemList = suggestedItemList;
    }

    private AdData queryAdId;
    private Employee queryEmpId;
    private int queryNumOfUnits;
    private String queryAccountNum;
    private UserPlus queryUser;

    public AdData getQueryAdId() {
        return queryAdId;
    }

    public void setQueryAdId(AdData queryAdId) {
        this.queryAdId = queryAdId;
    }

    public Employee getQueryEmpId() {
        return queryEmpId;
    }

    public void setQueryEmpId(Employee queryEmpId) {
        this.queryEmpId = queryEmpId;
    }

    public int getQueryNumOfUnits() {
        return queryNumOfUnits;
    }

    public void setQueryNumOfUnits(int queryNumOfUnits) {
        this.queryNumOfUnits = queryNumOfUnits;
    }

    public String getQueryAccountNum() {
        return queryAccountNum;
    }

    public void setQueryAccountNum(String queryAccountNum) {
        this.queryAccountNum = queryAccountNum;
    }

    private List<Transaction> transList;

    public class Transaction {

        private String TransDate, TransTime, ItemName, Type, NumOfUnits, AccountNum, EmpId, UserId;

        public String getTransDate() {
            return TransDate;
        }

        public void setTransDate(String TransDate) {
            this.TransDate = TransDate;
        }

        public String getTransTime() {
            return TransTime;
        }

        public void setTransTime(String TransTime) {
            this.TransTime = TransTime;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getType() {
            return Type;
        }

        public void setType(String ItemType) {
            this.Type = ItemType;
        }

        public String getNumOfUnits() {
            return NumOfUnits;
        }

        public void setNumOfUnits(String NumOfUnits) {
            this.NumOfUnits = NumOfUnits;
        }

        public String getAccountNum() {
            return AccountNum;
        }

        public void setAccountNum(String AccountNum) {
            this.AccountNum = AccountNum;
        }

        public String getEmpId() {
            return EmpId;
        }

        public void setEmpId(String EmpId) {
            this.EmpId = EmpId;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }
    }

    public List<Transaction> getTransList() {
        transList = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT S.TransDate, S.TransTime, A.ItemName, A.Type, S.numOfUnits, S.accountNum, U.UserId, E.SSN  FROM Buy B, AdData A, UserPlus U, Employee E, Sales S WHERE B.EmpId = E.SSN and B.TransId = S.TransId and S.AdId = A.AdId and B.UserId = U.UserId ORDER BY S.TransDate, S.TransTime DESC;");

            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //result found, means valid inputs
                Transaction added = new Transaction();

                added.setTransDate(rs.getString("TransDate"));
                added.setTransTime(rs.getString("TransTime"));
                added.setItemName(rs.getString("ItemName"));
                added.setType(rs.getString("Type"));
                added.setNumOfUnits(rs.getString("NumOfUnits"));
                added.setAccountNum(rs.getString("AccountNum"));
                added.setUserId(rs.getString("UserId"));
                added.setEmpId(rs.getString("SSN"));

                transList.add(added);
            }
        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            
            return null;

        } finally {
            DataConnect.close(con);
        }

        return transList;
    }

    public void setTransList(List<Transaction> transList) {
        this.transList = transList;
    }

    public String addTransaction() {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("INSERT INTO sales(TransDate, TransTime, AdId, NumOfUnits, AccountNum) values (NOW(), NOW(), ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            if (queryAdId != null) {
                ps.setInt(1, queryAdId.getAdId());
            }
            if (queryNumOfUnits != 0) {
                ps.setInt(2, queryNumOfUnits);
            }
            if (queryAccountNum != null) {
                ps.setString(3, queryAccountNum);
            }

            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int TransId = rs.getInt(1);

            ps = con.prepareStatement("INSERT INTO Buy VALUES(?, ?, ?);");
            ps.setInt(1, TransId);
            ps.setString(2, queryEmpId.getSsn());
            ps.setInt(3, queryUser.getUserId());

            JsfUtil.addErrorMessage(ps.toString());

            ps.executeUpdate();

        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            

        } finally {
            DataConnect.close(con);
        }

        return "/CustRepLevel/AddTransaction";
    }

    public UserPlus getQueryUser() {
        return queryUser;
    }

    public void setQueryUser(UserPlus queryUser) {
        this.queryUser = queryUser;
    }

    private List<Transaction> custAccountHistory;

    public List<Transaction> getCustAccountHistory() {
        custAccountHistory = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT \n"
                    + "    S.TransDate,\n"
                    + "    S.TransTime,\n"
                    + "    A.ItemName,\n"
                    + "    A.Type,\n"
                    + "    S.numOfUnits,\n"
                    + "    S.accountNum,\n"
                    + "    U.UserId,\n"
                    + "    E.SSN\n"
                    + "FROM\n"
                    + "    Buy B,\n"
                    + "    AdData A,\n"
                    + "    UserPlus U,\n"
                    + "    Employee E,\n"
                    + "    Sales S\n"
                    + "WHERE\n"
                    + "    B.EmpId = E.SSN\n"
                    + "        AND B.TransId = S.TransId\n"
                    + "        AND S.AdId = A.AdId\n"
                    + "        AND B.UserId = U.UserId\n"
                    + "        AND U.UserId = ?\n"
                    + "        AND S.AccountNum = ?\n"
                    + "ORDER BY S.TransDate DESC, S.TransTime DESC;");

            if (queryUser != null) {
                ps.setInt(1, queryUser.getUserId());
            }

            if (queryAccountNum != null) {
                ps.setString(2, queryAccountNum);
            }

            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //result found, means valid inputs
                Transaction added = new Transaction();

                added.setTransDate(rs.getString("TransDate"));
                added.setTransTime(rs.getString("TransTime"));
                added.setItemName(rs.getString("ItemName"));
                added.setType(rs.getString("Type"));
                added.setNumOfUnits(rs.getString("NumOfUnits"));
                added.setAccountNum(rs.getString("AccountNum"));
                added.setUserId(rs.getString("UserId"));
                added.setEmpId(rs.getString("SSN"));

                custAccountHistory.add(added);
            }

        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            

        } finally {
            DataConnect.close(con);
        }

        return custAccountHistory;
    }

    public void setCustAccountHistory(List<Transaction> custAccountHistory) {
        this.custAccountHistory = custAccountHistory;
    }

    private List<BestSeller> bestSellerList;

    public class BestSeller {

//        private int AdId, totalCnt;
        private String AdId, totalCnt;
        private String ItemName;

//        public int getAdId() {
//            return AdId;
//        }
//
//        public void setAdId(int AdId) {
//            this.AdId = AdId;
//        }
//
//        public int getTotalCnt() {
//            return totalCnt;
//        }
//
//        public void setTotalCnt(int totalCnt) {
//            this.totalCnt = totalCnt;
//        }
//
//        public String getItemName() {
//            return ItemName;
//        }
//
//        public void setItemName(String ItemName) {
//            this.ItemName = ItemName;
//        }
        public String getAdId() {
            return AdId;
        }

        public void setAdId(String AdId) {
            this.AdId = AdId;
        }

        public String getTotalCnt() {
            return totalCnt;
        }

        public void setTotalCnt(String totalCnt) {
            this.totalCnt = totalCnt;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }
    }

    public List<BestSeller> getBestSellerList() {
        bestSellerList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT \n"
                    + "    A.AdId, A.ItemName, SUM(S.NumOfUnits) AS totalCnt\n"
                    + "FROM\n"
                    + "    AdData A,\n"
                    + "    Buy B,\n"
                    + "    Sales S\n"
                    + "WHERE\n"
                    + "    A.AdId = S.AdId\n"
                    + "        AND B.TransId = S.TransId\n"
                    + "GROUP BY A.AdId , A.ItemName , S.NumOfUnits\n"
                    + "ORDER BY totalCnt DESC;");

            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //result found, means valid inputs
                BestSeller added = new BestSeller();

                added.setItemName(rs.getString("ItemName"));
                added.setAdId(rs.getString("AdId"));
                added.setTotalCnt(rs.getString("totalCnt"));

                bestSellerList.add(added);
            }

        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            

        } finally {
            DataConnect.close(con);
        }

        return bestSellerList;
    }

    public void setBestSellerList(List<BestSeller> bestSeller) {
        this.bestSellerList = bestSeller;
    }
}
