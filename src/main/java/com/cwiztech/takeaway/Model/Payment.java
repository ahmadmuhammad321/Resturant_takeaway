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
@Table(name = "TBLPAYEMENT")
public class Payment {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long PAYEMENT_ID;
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer CUSTOMER_ID;
	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private Order ORDER_ID;
	@Column(name = "PAYMENT_AMOUNT")
	private Long PAYMENT_AMOUNT;
	@Column(name = "PAYMENT_DATE")
	private String PAYMENT_DATE;
	@Column(name = "PAYMENT_TYPE")
	private String PAYMENT_TYPE;
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
	public long getPAYEMENT_ID() {
		return PAYEMENT_ID;
	}
	public void setPAYEMENT_ID(long pAYEMENT_ID) {
		PAYEMENT_ID = pAYEMENT_ID;
	}
	public Customer getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}
	public void setCUSTOMER_ID(Customer cUSTOMER_ID) {
		CUSTOMER_ID = cUSTOMER_ID;
	}
	public Order getORDER_ID() {
		return ORDER_ID;
	}
	public void setORDER_ID(Order oRDER_ID) {
		ORDER_ID = oRDER_ID;
	}
	public Long getPAYMENT_AMOUNT() {
		return PAYMENT_AMOUNT;
	}
	public void setPAYMENT_AMOUNT(Long pAYMENT_AMOUNT) {
		PAYMENT_AMOUNT = pAYMENT_AMOUNT;
	}
	public String getPAYMENT_DATE() {
		return PAYMENT_DATE;
	}
	public void setPAYMENT_DATE(String pAYMENT_DATE) {
		PAYMENT_DATE = pAYMENT_DATE;
	}
	public String getPAYMENT_TYPE() {
		return PAYMENT_TYPE;
	}
	public void setPAYMENT_TYPE(String pAYMENT_TYPE) {
		PAYMENT_TYPE = pAYMENT_TYPE;
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
