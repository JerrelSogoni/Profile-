/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yunjoon_soh
 */
@Entity
@Table(name = "UserPlus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserPlus.findAll", query = "SELECT u FROM UserPlus u")
    , @NamedQuery(name = "UserPlus.findByUserId", query = "SELECT u FROM UserPlus u WHERE u.userId = :userId")
    , @NamedQuery(name = "UserPlus.findByPassword", query = "SELECT u FROM UserPlus u WHERE u.password = :password")
    , @NamedQuery(name = "UserPlus.findByFirstName", query = "SELECT u FROM UserPlus u WHERE u.firstName = :firstName")
    , @NamedQuery(name = "UserPlus.findByLastName", query = "SELECT u FROM UserPlus u WHERE u.lastName = :lastName")
    , @NamedQuery(name = "UserPlus.findByAddress", query = "SELECT u FROM UserPlus u WHERE u.address = :address")
    , @NamedQuery(name = "UserPlus.findByCity", query = "SELECT u FROM UserPlus u WHERE u.city = :city")
    , @NamedQuery(name = "UserPlus.findByState", query = "SELECT u FROM UserPlus u WHERE u.state = :state")
    , @NamedQuery(name = "UserPlus.findByZipCode", query = "SELECT u FROM UserPlus u WHERE u.zipCode = :zipCode")
    , @NamedQuery(name = "UserPlus.findByPhone", query = "SELECT u FROM UserPlus u WHERE u.phone = :phone")
    , @NamedQuery(name = "UserPlus.findByEmail", query = "SELECT u FROM UserPlus u WHERE u.email = :email")
    , @NamedQuery(name = "UserPlus.findByAccountNum", query = "SELECT u FROM UserPlus u WHERE u.accountNum = :accountNum")
    , @NamedQuery(name = "UserPlus.findByAccountCreationDate", query = "SELECT u FROM UserPlus u WHERE u.accountCreationDate = :accountCreationDate")
    , @NamedQuery(name = "UserPlus.findByCreditCardNum", query = "SELECT u FROM UserPlus u WHERE u.creditCardNum = :creditCardNum")
    , @NamedQuery(name = "UserPlus.findByPreferences", query = "SELECT u FROM UserPlus u WHERE u.preferences = :preferences")})
public class UserPlus implements Serializable {

    @ManyToMany(mappedBy = "userPlusCollection")
    private Collection<GroupPost> groupPostCollection;
    @OneToMany(mappedBy = "authorId")
    private Collection<GroupPost> groupPostCollection1;

    @Size(max = 2)
    @Column(name = "Sex")
    private String sex;
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UserId")
    private Integer userId;
    @Size(max = 20)
    @Column(name = "Password")
    private String password;
    @Size(max = 20)
    @Column(name = "FirstName")
    private String firstName;
    @Size(max = 20)
    @Column(name = "LastName")
    private String lastName;
    @Size(max = 50)
    @Column(name = "Address")
    private String address;
    @Size(max = 60)
    @Column(name = "City")
    private String city;
    @Size(max = 20)
    @Column(name = "State")
    private String state;
    @Column(name = "ZipCode")
    private Integer zipCode;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 10)
    @Column(name = "Phone")
    private String phone;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "Email")
    private String email;
    @Column(name = "AccountNum")
    private Integer accountNum;
    @Column(name = "AccountCreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date accountCreationDate;
    @Size(max = 16)
    @Column(name = "CreditCardNum")
    private String creditCardNum;
    @Size(max = 50)
    @Column(name = "Preferences")
    private String preferences;
    @ManyToMany(mappedBy = "userPlusCollection")
    private Collection<Comment> commentCollection;
    @JoinTable(name = "HasAPersonal", joinColumns = {
        @JoinColumn(name = "UserId", referencedColumnName = "UserId")}, inverseJoinColumns = {
        @JoinColumn(name = "PersonalPageId", referencedColumnName = "PageId")})
    @ManyToMany
    private Collection<PersonalPage> personalPageCollection;
    @ManyToMany(mappedBy = "userPlusCollection")
    private Collection<Post> postCollection;
    @JoinTable(name = "IsIn", joinColumns = {
        @JoinColumn(name = "UserId", referencedColumnName = "UserId")}, inverseJoinColumns = {
        @JoinColumn(name = "GroupId", referencedColumnName = "GroupId")})
    @ManyToMany
    private Collection<GroupPlus> groupPlusCollection;
    @JoinTable(name = "FriendsWith", joinColumns = {
        @JoinColumn(name = "UserId", referencedColumnName = "UserId")}, inverseJoinColumns = {
        @JoinColumn(name = "FriendId", referencedColumnName = "UserId")})
    @ManyToMany
    private Collection<UserPlus> userPlusCollection;
    @ManyToMany(mappedBy = "userPlusCollection")
    private Collection<UserPlus> userPlusCollection1;
    @JoinTable(name = "Send", joinColumns = {
        @JoinColumn(name = "Sender", referencedColumnName = "UserId")}, inverseJoinColumns = {
        @JoinColumn(name = "MessageId", referencedColumnName = "MessageId")})
    @ManyToMany
    private Collection<Message> messageCollection;
    @JoinTable(name = "Receive", joinColumns = {
        @JoinColumn(name = "Receiver", referencedColumnName = "UserId")}, inverseJoinColumns = {
        @JoinColumn(name = "MessageId", referencedColumnName = "MessageId")})
    @ManyToMany
    private Collection<Message> messageCollection1;
    @JoinTable(name = "CreatesGroup", joinColumns = {
        @JoinColumn(name = "UserId", referencedColumnName = "UserId")}, inverseJoinColumns = {
        @JoinColumn(name = "GroupId", referencedColumnName = "GroupId")})
    @ManyToMany
    private Collection<GroupPlus> groupPlusCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userPlus")
    private Collection<HasAccessToGroup> hasAccessToGroupCollection;
    @OneToMany(mappedBy = "adderId")
    private Collection<HasAccessToGroup> hasAccessToGroupCollection1;
    @OneToMany(mappedBy = "authorId")
    private Collection<Comment> commentCollection1;
    @OneToMany(mappedBy = "receiverId")
    private Collection<Message> messageCollection2;
    @OneToMany(mappedBy = "senderId")
    private Collection<Message> messageCollection3;
    @OneToMany(mappedBy = "authorId")
    private Collection<Post> postCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userPlus")
    private Collection<Buy> buyCollection;
    @OneToMany(mappedBy = "ownerId")
    private Collection<PersonalPage> personalPageCollection1;
    @OneToMany(mappedBy = "owner")
    private Collection<GroupPlus> groupPlusCollection2;

    public UserPlus() {
    }

    public UserPlus(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Integer accountNum) {
        this.accountNum = accountNum;
    }

    public Date getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public String getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(String creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    @XmlTransient
    public Collection<PersonalPage> getPersonalPageCollection() {
        return personalPageCollection;
    }

    public void setPersonalPageCollection(Collection<PersonalPage> personalPageCollection) {
        this.personalPageCollection = personalPageCollection;
    }

    @XmlTransient
    public Collection<Post> getPostCollection() {
        return postCollection;
    }

    public void setPostCollection(Collection<Post> postCollection) {
        this.postCollection = postCollection;
    }

    @XmlTransient
    public Collection<GroupPlus> getGroupPlusCollection() {
        return groupPlusCollection;
    }

    public void setGroupPlusCollection(Collection<GroupPlus> groupPlusCollection) {
        this.groupPlusCollection = groupPlusCollection;
    }

    @XmlTransient
    public Collection<UserPlus> getUserPlusCollection() {
        return userPlusCollection;
    }

    public void setUserPlusCollection(Collection<UserPlus> userPlusCollection) {
        this.userPlusCollection = userPlusCollection;
    }

    @XmlTransient
    public Collection<UserPlus> getUserPlusCollection1() {
        return userPlusCollection1;
    }

    public void setUserPlusCollection1(Collection<UserPlus> userPlusCollection1) {
        this.userPlusCollection1 = userPlusCollection1;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection1() {
        return messageCollection1;
    }

    public void setMessageCollection1(Collection<Message> messageCollection1) {
        this.messageCollection1 = messageCollection1;
    }

    @XmlTransient
    public Collection<GroupPlus> getGroupPlusCollection1() {
        return groupPlusCollection1;
    }

    public void setGroupPlusCollection1(Collection<GroupPlus> groupPlusCollection1) {
        this.groupPlusCollection1 = groupPlusCollection1;
    }

    @XmlTransient
    public Collection<HasAccessToGroup> getHasAccessToGroupCollection() {
        return hasAccessToGroupCollection;
    }

    public void setHasAccessToGroupCollection(Collection<HasAccessToGroup> hasAccessToGroupCollection) {
        this.hasAccessToGroupCollection = hasAccessToGroupCollection;
    }

    @XmlTransient
    public Collection<HasAccessToGroup> getHasAccessToGroupCollection1() {
        return hasAccessToGroupCollection1;
    }

    public void setHasAccessToGroupCollection1(Collection<HasAccessToGroup> hasAccessToGroupCollection1) {
        this.hasAccessToGroupCollection1 = hasAccessToGroupCollection1;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection1() {
        return commentCollection1;
    }

    public void setCommentCollection1(Collection<Comment> commentCollection1) {
        this.commentCollection1 = commentCollection1;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection2() {
        return messageCollection2;
    }

    public void setMessageCollection2(Collection<Message> messageCollection2) {
        this.messageCollection2 = messageCollection2;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection3() {
        return messageCollection3;
    }

    public void setMessageCollection3(Collection<Message> messageCollection3) {
        this.messageCollection3 = messageCollection3;
    }

    @XmlTransient
    public Collection<Post> getPostCollection1() {
        return postCollection1;
    }

    public void setPostCollection1(Collection<Post> postCollection1) {
        this.postCollection1 = postCollection1;
    }

    @XmlTransient
    public Collection<Buy> getBuyCollection() {
        return buyCollection;
    }

    public void setBuyCollection(Collection<Buy> buyCollection) {
        this.buyCollection = buyCollection;
    }

    @XmlTransient
    public Collection<PersonalPage> getPersonalPageCollection1() {
        return personalPageCollection1;
    }

    public void setPersonalPageCollection1(Collection<PersonalPage> personalPageCollection1) {
        this.personalPageCollection1 = personalPageCollection1;
    }

    @XmlTransient
    public Collection<GroupPlus> getGroupPlusCollection2() {
        return groupPlusCollection2;
    }

    public void setGroupPlusCollection2(Collection<GroupPlus> groupPlusCollection2) {
        this.groupPlusCollection2 = groupPlusCollection2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserPlus)) {
            return false;
        }
        UserPlus other = (UserPlus) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "profile.UserPlus[ userId=" + userId + " ]";
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @XmlTransient
    public Collection<GroupPost> getGroupPostCollection() {
        return groupPostCollection;
    }

    public void setGroupPostCollection(Collection<GroupPost> groupPostCollection) {
        this.groupPostCollection = groupPostCollection;
    }

    @XmlTransient
    public Collection<GroupPost> getGroupPostCollection1() {
        return groupPostCollection1;
    }

    public void setGroupPostCollection1(Collection<GroupPost> groupPostCollection1) {
        this.groupPostCollection1 = groupPostCollection1;
    }
    
}
