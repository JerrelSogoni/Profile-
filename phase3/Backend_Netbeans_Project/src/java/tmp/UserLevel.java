package tmp;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("userLevel")
@SessionScoped
public class UserLevel implements Serializable{

    private String content;
    public String mainPage(){
        return "/personalPage/MainPage";
    }
    public String makeAPost(){
        return "/UserLevel/makeAPost";
    }
    public String friendsList(){
        return "/UserLevel/UserFriendsList";
    }
    public String Post() {
        return "/UserLevel/Post";
    }
    public String Comment(){
         return "/comment/CommentListPostViewer";
    }

    public String SendMessage() {
        return "/UserLevel/SendMessage";
    }

    public String ReceiveMessage() {
        return "/UserLevel/ReceiveMessage";
    }

    public String DeleteMessage() {
        return "/UserLevel/DeleteMessage";
    }

    public String makePost() {
        return "/UserLevel/Post";
    }

    public String commentPost() {
        return null;
    }

    public String removePost() {
        return "/UserLevel/Post";
    }

    public String removeComment() {
        return null;
    }

    public String likePost() {
        return null;
    }

    public String likeComment() {
        return null;
    }

    public String unlikePost() {
        return null;
    }

    public String unlikeComment() {
        return null;
    }

    public String editPost() {
        return null;
    }

    public String editComment() {
        return null;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

}
