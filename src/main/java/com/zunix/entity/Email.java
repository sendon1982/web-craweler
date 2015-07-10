package com.zunix.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Email_List")
public class Email implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 12312311231231L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email", unique = true, length = 60)
    private String email = null;

    @Column(name = "createdBy", length = 80)
    private String createdBy = null;

    @Column(name = "createdTime", length = 60)
    private Date createdTime = null;

    public Email() {

    }

    public Email(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public static Email newEmptyEmail()
    {
        return new Email();
    }
}
