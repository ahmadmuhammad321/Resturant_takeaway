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
@Table(name = "TBLCHEF")
public class Chef {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long CHEF_ID;
	@Column(name = "CHEF_NAME")
	private String CHEF_NAME;
	@Column(name = "CHEF_EMAIL")
	private String CHEF_EMAIL;
	@Column(name = "CHEF_PASSWORD")
	private String CHEF_PASSWORD;
	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private Order ORDER_ID;
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
	public long getCHEF_ID() {
		return CHEF_ID;
	}
	public void setCHEF_ID(long cHEF_ID) {
		CHEF_ID = cHEF_ID;
	}
	public String getCHEF_NAME() {
		return CHEF_NAME;
	}
	public void setCHEF_NAME(String cHEF_NAME) {
		CHEF_NAME = cHEF_NAME;
	}
	public String getCHEF_EMAIL() {
		return CHEF_EMAIL;
	}
	public void setCHEF_EMAIL(String cHEF_EMAIL) {
		CHEF_EMAIL = cHEF_EMAIL;
	}
	public String getCHEF_PASSWORD() {
		return CHEF_PASSWORD;
	}
	public void setCHEF_PASSWORD(String cHEF_PASSWORD) {
		CHEF_PASSWORD = cHEF_PASSWORD;
	}
	public Order getORDER_ID() {
		return ORDER_ID;
	}
	public void setORDER_ID(Order oRDER_ID) {
		ORDER_ID = oRDER_ID;
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
