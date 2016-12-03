package profile.util;

import java.sql.*;
import javax.servlet.http.HttpSession;

public class LoginDAO {

    public static boolean validate(String user, String password) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select * from UserPlus where UserId = ? and Password = ?");
            ps.setInt(1, Integer.parseInt(user));
            ps.setString(2, password);
            
            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("userid", rs.getInt("userId"));
                session.setAttribute("email", rs.getString("email"));
                //result found, means valid inputs
                return true;
            }
        } catch (SQLException ex) {
            
            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            System.out.println("Login error -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
        }
        return false;
    }
}
