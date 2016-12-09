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
        return "/UserLevel/Post";
    }
    public String friendsList(){
        return "/UserLevel/UserFriendsList";
    }
    public String Post() {
        return "/UserLevel/Post";
    }
    public String Comment(){
         return "/UserLevel/Post";
    }

    public String SendMessage() {
        return "/UserLevel/MessageList";
    }

    public String ReceiveMessage() {
        return "/UserLevel/MessageList";
    }

    public String DeleteMessage() {
        return "/UserLevel/MessageList";
    }

    public String makePost() {
        return "/UserLevel/Post";
    }

    public String commentPost() {
        return "/UserLevel/Post";
    }

    public String removePost() {
        return "/UserLevel/Post";
    }
    public String messageList(){
        return "/UserLevel/MessageList";
    }
    public String groupList(){
        return "/GroupLevel/GroupList";
    }
    public String removeComment() {
        return "/UserLevel/Post";
    }

    public String likePost() {
        return "/UserLevel/Post";
    }

    public String likeComment() {
        return "/UserLevel/Post";
    }

    public String unlikePost() {
        return "/UserLevel/Post";
    }

    public String unlikeComment() {
        return "/UserLevel/Post";
    }

    public String editPost() {
        return "/UserLevel/Post";
    }

    public String editComment() {
        return "/UserLevel/Post";
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
