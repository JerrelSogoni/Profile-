/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import profile.util.DataConnect;
import profile.util.JsfUtil;

/**
 *
 * @author yunjoon_soh
 */
@Named("register")
@ApplicationScoped
public class Register implements Serializable {

    private String phone;
    private String firstname, lastname, email, address, city, state, zip,
            dob, pref, sex;
    private String password, accountNum;

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return the dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * @param dob the dob to set
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * @return the pref
     */
    public String getPref() {
        return pref;
    }

    /**
     * @param pref the pref to set
     */
    public void setPref(String pref) {
        this.pref = pref;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String submit() {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("INSERT INTO UserPlus("
                    + "FirstName, LastName, Address, City, State, ZipCode, "
                    + "Phone, Email, Sex, Password, AccountNum, DOB) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, address);
            ps.setString(4, city);
            ps.setString(5, state);
            ps.setString(6, zip);
            ps.setString(7, phone);
            ps.setString(8, email);
            ps.setString(9, sex);
            ps.setString(10, password);
            ps.setString(11, accountNum);
            ps.setString(12, dob);

            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int UserId = rs.getInt(1);
            
            // Generate Page
            ps = con.prepareStatement("INSERT INTO PagePlus(PostCount) VALUES(0);", Statement.RETURN_GENERATED_KEYS);
            
            JsfUtil.addErrorMessage(ps.toString());
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            rs.next();
            int PageId = rs.getInt(1);
            
            ps = con.prepareStatement("INSERT INTO PersonalPage VALUES(?, ?);");
            ps.setInt(1, PageId);
            ps.setInt(2, UserId);
            
            JsfUtil.addErrorMessage(ps.toString());
            ps.executeUpdate();            

        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            
            return "/userPlus/Register";
        } finally {
            DataConnect.close(con);
        }
        return "";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

}
