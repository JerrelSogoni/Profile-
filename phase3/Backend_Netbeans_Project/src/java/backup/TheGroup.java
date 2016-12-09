/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;

import profile.*;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
/*
This is used when making the groups
*/

/**
 *
 * @author Jerrel
 */
@Named("theGrouisfsdiofjsdfjp")
@SessionScoped
public class TheGroup implements Serializable {
    private Integer GroupId;
    private String GroupName;
    private Integer OwnerId;
    private String Set;
    private String joinStatus;

    /**
     * @return the GroupId
     */
    public Integer getGroupId() {
        return GroupId;
    }

    /**
     * @param GroupId the GroupId to set
     */
    public void setGroupId(Integer GroupId) {
        this.GroupId = GroupId;
    }

    /**
     * @return the GroupName
     */
    public String getGroupName() {
        return GroupName;
    }

    /**
     * @param GroupName the GroupName to set
     */
    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }

    /**
     * @return the OwnerId
     */
    public Integer getOwnerId() {
        return OwnerId;
    }

    /**
     * @param OwnerId the OwnerId to set
     */
    public void setOwnerId(Integer OwnerId) {
        this.OwnerId = OwnerId;
    }

    /**
     * @return the Set
     */
    public String getSet() {
        return Set;
    }

    /**
     * @param Set the Set to set
     */
    public void setSet(String Set) {
        this.Set = Set;
    }

    /**
     * @return the joinStatus
     */
    public String getJoinStatus() {
        return joinStatus;
    }

    /**
     * @param joinStatus the joinStatus to set
     */
    public void setJoinStatus(String joinStatus) {
        this.joinStatus = joinStatus;
    }
    
}
