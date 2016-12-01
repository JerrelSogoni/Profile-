package tmp;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("userLevel")
@SessionScoped
public class UserLevel implements Serializable{

    private String content;
    
    public String Post() {
        return "/UserLevel/Post";
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
        return null;
    }

    public String commentPost() {
        return null;
    }

    public String removePost() {
        return null;
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