package profile.util;

import java.sql.*;
import javax.servlet.http.HttpSession;
import profile.UserPlus;

public class LoginDAO {

    public static boolean validate(String user, String password) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select * from UserPlus where Email = ? and Password = ?");
//            ps.setInt(1, Integer.parseInt(user));
            ps.setString(1, user);
            ps.setString(2, password);
            
            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UserPlus userPlus = new UserPlus();
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("userId", rs.getInt("userId"));
                session.setAttribute("email", rs.getString("email"));

                userPlus.setUserId(rs.getInt("userId"));
                userPlus.setAccountCreationDate(rs.getDate("AccountCreationDate"));
                userPlus.setPassword(rs.getString("Password"));
                userPlus.setFirstName(rs.getString("FirstName"));
                userPlus.setLastName(rs.getString("LastName"));
                userPlus.setAddress(rs.getString("Address"));
                userPlus.setCity(rs.getString("City"));
                userPlus.setZipCode(rs.getInt("ZipCode"));
                userPlus.setPhone(rs.getString("Phone"));
                userPlus.setEmail(rs.getString("email"));
                userPlus.setAccountNum(rs.getInt("AccountNum"));
                userPlus.setCreditCardNum(rs.getString("CreditCardNum"));
                userPlus.setPreferences(rs.getString("Preferences"));
                session.setAttribute("currentUser", userPlus);
                
                //result found, means valid inputs
                return true;
            }
        } catch (SQLException ex) {
            
            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            
            return false;
        } finally {
            DataConnect.close(con);
        }
        return false;
    }
}
