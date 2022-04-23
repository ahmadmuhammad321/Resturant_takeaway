package com.cwiztech.takeaway.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TBLCUSTOMER")
public class Customer {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long CUSTOMER_ID;
	@Column(name = "CUSTOMER_NAME")
	private String CUSTOMER_NAME;
	@Column(name = "CUSTOMER_EMAIL")
	private String CUSTOMER_EMAIL;
	@Column(name = "CUSTOMER_PHONE")
	private Long CUSTOMER_PHONE;
	@ManyToOne
	@JoinColumn(name = "FOOD_ID")
	private Food FOOD_ID;
	@ManyToOne
	@JoinColumn(name = "PAYMENT_ID")
	private Payment PAYMENT_ID;
	@JsonIgnore
	@Column(name = "ISACTIVE")
	private String ISACTIVE;
	@JsonIgnore
	@Column(name = "MODIFIED_BY")
	private Long MODIFIED_BY;
	@JsonIgnore
	@Column(name = "MODIFIED_WHEN")
	private String MODIFIED_WHEN;
	@JsonIgnore
	@Column(name = "MODIFIED_WORKSTATION")
	private String MODIFIED_WORKSTATION;
	public long getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}
	public void setCUSTOMER_ID(long cUSTOMER_ID) {
		CUSTOMER_ID = cUSTOMER_ID;
	}
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}
	public String getCUSTOMER_EMAIL() {
		return CUSTOMER_EMAIL;
	}
	public void setCUSTOMER_EMAIL(String cUSTOMER_EMAIL) {
		CUSTOMER_EMAIL = cUSTOMER_EMAIL;
	}
	public Long getCUSTOMER_PHONE() {
		return CUSTOMER_PHONE;
	}
	public void setCUSTOMER_PHONE(Long cUSTOMER_PHONE) {
		CUSTOMER_PHONE = cUSTOMER_PHONE;
	}
	public Food getFOOD_ID() {
		return FOOD_ID;
	}
	public void setFOOD_ID(Food fOOD_ID) {
		FOOD_ID = fOOD_ID;
	}
	public Payment getPAYMENT_ID() {
		return PAYMENT_ID;
	}
	public void setPAYMENT_ID(Payment pAYMENT_ID) {
		PAYMENT_ID = pAYMENT_ID;
	}
	public String getISACTIVE() {
		return ISACTIVE;
	}
	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}
	public Long getMODIFIED_BY() {
		return MODIFIED_BY;
	}
	public void setMODIFIED_BY(Long mODIFIED_BY) {
		MODIFIED_BY = mODIFIED_BY;
	}
	public String getMODIFIED_WHEN() {
		return MODIFIED_WHEN;
	}
	public void setMODIFIED_WHEN(String mODIFIED_WHEN) {
		MODIFIED_WHEN = mODIFIED_WHEN;
	}
	public String getMODIFIED_WORKSTATION() {
		return MODIFIED_WORKSTATION;
	}
	public void setMODIFIED_WORKSTATION(String mODIFIED_WORKSTATION) {
		MODIFIED_WORKSTATION = mODIFIED_WORKSTATION;
	}
	
	
	
}
