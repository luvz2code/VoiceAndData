package com.etrade.eeo.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by rayyar on 3/9/18.
 */
@Entity
@Table(name = "address")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "AddressId")
    @ApiModelProperty(notes = "The database generated primary key")
    private Long addressId;

    @ApiModelProperty(notes = "Line 1 of address")
    private String line1;

    @ApiModelProperty(notes = "Line 2 of address")
    private String line2;

    @ApiModelProperty(notes = "Line 3 of address")
    private String line3;

    @ApiModelProperty(notes = "City")
    private String city;

    @ApiModelProperty(notes = "State or Province")
    private String state;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotBlank
    @ApiModelProperty(notes = "Country")
    private String country;

    @ApiModelProperty(notes = "Zipcode or Pincode")
    private String zipcode;

    @NotBlank
    @ApiModelProperty(notes = "Address Type - Can be 'HOME', 'WORK' or 'OTHER'")
    private String type;

    @NotNull
    @JsonFormat(pattern = "MM/dd/yyyy")
    @ApiModelProperty(notes = "The date from which this address is effective")
    private Date effectiveDate;

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
