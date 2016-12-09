/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import profile.Post;
import profile.UserPlus;

public class SessionUtils {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }

    public static int getUserId() {
        HttpSession session = getSession();
        if (session != null) {
            return (int) session.getAttribute("userid");
        } else {
            return -1;
        }
    }
    public static String getUserEmail(){
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("email");
        } else {
            return null;
        }
    }
    public static String getPostId(){
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("postId");
        } else {
            return null;
        }
    }
    public static Post getPost(){
          HttpSession session = getSession();
        if (session != null) {
            return (Post) session.getAttribute("post");
        } else {
            return null;
        }
    }
    public static UserPlus getUser(){
         HttpSession session = getSession();
        if (session != null) {
            return (UserPlus) session.getAttribute("currentUser");
        } else {
            return null;
        }
        
    }
    
    public static Integer getGroupId(){
        HttpSession session = getSession();
        if(session != null) {
            return (Integer) session.getAttribute("group");
        } else {
            return null;
        }
    }
   
}
