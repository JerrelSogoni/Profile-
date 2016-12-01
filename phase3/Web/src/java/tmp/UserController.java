package tmp;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpSession;
import tmp.util.JsfUtil;
import tmp.util.LoginDAO;
import tmp.util.SessionUtils;

@Named("userController")
@SessionScoped
public class UserController implements Serializable {

    private String pwd;
    private String user;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

   //validate login
    public String login() {
        boolean valid = LoginDAO.validate(user, pwd);
        if (valid) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user);
            JsfUtil.addSuccessMessage("User login successful: " + user);
            return "/personalPage/MainPage";
        } else {
            JsfUtil.addErrorMessage("Incorrect Username and Passowrd: " + user + ", " + pwd);
            return "/signInNOut/login";
        }
    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "/signInNOut/login";
    }
}
