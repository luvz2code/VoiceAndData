package com.etrade.eeo.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rayyar on 3/9/18.
 */

@Entity
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "AccountId")
    @ApiModelProperty(notes = "The database generated primary key")
    private Long accountId;

    @NotBlank
    @ApiModelProperty(notes = "First Name")
    private String firstName;

    @NotBlank
    @ApiModelProperty(notes = "Last Name")
    private String lastName;

    @JsonFormat(pattern = "MM/dd/yyyy")
    @ApiModelProperty(notes = "Hire Date")
    private Date hireDate;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss")
    @ApiModelProperty(notes = "Date and Time when the account was created")
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss")
    @ApiModelProperty(notes = "Date and Time when the account was last updated")
    private Date updatedAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_ID")
    @ApiModelProperty(notes = "List of Addresses")
    private Set<Address> addresses = new HashSet<>();

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }


    public Account() {
    }

    public Account(Account account ) {
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.hireDate = account.getHireDate();
        this.addresses = account.getAddresses();
    }

    public Account(Account account, Long accountId ) {
        this.accountId = accountId;
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.hireDate = account.getHireDate();
        this.addresses = account.getAddresses();
    }

}
