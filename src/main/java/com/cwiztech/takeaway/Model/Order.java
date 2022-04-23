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
@Table(name = "TBLORDER")
public class Order {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long ORDER_ID;
	@Column(name = "ORDER_DATE")
	private String ORDER_DATE;
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer CUSTOMER_ID;
	@Column(name = "PICKUP_DATE")
	private String PICKUP_DATE;
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
	public long getORDER_ID() {
		return ORDER_ID;
	}
	public void setORDER_ID(long oRDER_ID) {
		ORDER_ID = oRDER_ID;
	}
	public String getORDER_DATE() {
		return ORDER_DATE;
	}
	public void setORDER_DATE(String oRDER_DATE) {
		ORDER_DATE = oRDER_DATE;
	}
	public Customer getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}
	public void setCUSTOMER_ID(Customer cUSTOMER_ID) {
		CUSTOMER_ID = cUSTOMER_ID;
	}
	public String getPICKUP_DATE() {
		return PICKUP_DATE;
	}
	public void setPICKUP_DATE(String pICKUP_DATE) {
		PICKUP_DATE = pICKUP_DATE;
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
