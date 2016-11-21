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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "Message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m")
    , @NamedQuery(name = "Message.findByMessageId", query = "SELECT m FROM Message m WHERE m.messageId = :messageId")
    , @NamedQuery(name = "Message.findByDateSent", query = "SELECT m FROM Message m WHERE m.dateSent = :dateSent")
    , @NamedQuery(name = "Message.findBySubject", query = "SELECT m FROM Message m WHERE m.subject = :subject")
    , @NamedQuery(name = "Message.findByContent", query = "SELECT m FROM Message m WHERE m.content = :content")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MessageId")
    private Integer messageId;
    @Column(name = "DateSent")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSent;
    @Size(max = 100)
    @Column(name = "Subject")
    private String subject;
    @Size(max = 100)
    @Column(name = "Content")
    private String content;
    @ManyToMany(mappedBy = "messageCollection")
    private Collection<UserPlus> userPlusCollection;
    @ManyToMany(mappedBy = "messageCollection1")
    private Collection<UserPlus> userPlusCollection1;
    @JoinColumn(name = "ReceiverId", referencedColumnName = "UserId")
    @ManyToOne
    private UserPlus receiverId;
    @JoinColumn(name = "SenderId", referencedColumnName = "UserId")
    @ManyToOne
    private UserPlus senderId;

    public Message() {
    }

    public Message(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public UserPlus getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(UserPlus receiverId) {
        this.receiverId = receiverId;
    }

    public UserPlus getSenderId() {
        return senderId;
    }

    public void setSenderId(UserPlus senderId) {
        this.senderId = senderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageId != null ? messageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.messageId == null && other.messageId != null) || (this.messageId != null && !this.messageId.equals(other.messageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "profile.Message[ messageId=" + messageId + " ]";
    }
    
}
