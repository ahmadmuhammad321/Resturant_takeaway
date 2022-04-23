package com.cwiztech.takeaway.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TBLADMIN")
public class Admin {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long ADMIN_ID;
	@Column(name = "ADMIN_NAME")
	private String ADMIN_NAME;
	@Column(name = "ADMIN_EMAIL")
	private String ADMIN_EMAIL;
	@Column(name = "ADMIN_PASSWORD")
	private String ADMIN_PASSWORD;
	@Column(name = "ADMIN_STATUS")
	private String ADMIN_STATUS;
	@Column(name = "MENU_ID")
	private Long MENU_ID;
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
	public long getADMIN_ID() {
		return ADMIN_ID;
	}
	public void setADMIN_ID(long aDMIN_ID) {
		ADMIN_ID = aDMIN_ID;
	}
	public String getADMIN_NAME() {
		return ADMIN_NAME;
	}
	public void setADMIN_NAME(String aDMIN_NAME) {
		ADMIN_NAME = aDMIN_NAME;
	}
	public String getADMIN_EMAIL() {
		return ADMIN_EMAIL;
	}
	public void setADMIN_EMAIL(String aDMIN_EMAIL) {
		ADMIN_EMAIL = aDMIN_EMAIL;
	}
	public String getADMIN_PASSWORD() {
		return ADMIN_PASSWORD;
	}
	public void setADMIN_PASSWORD(String aDMIN_PASSWORD) {
		ADMIN_PASSWORD = aDMIN_PASSWORD;
	}
	public String getADMIN_STATUS() {
		return ADMIN_STATUS;
	}
	public void setADMIN_STATUS(String aDMIN_STATUS) {
		ADMIN_STATUS = aDMIN_STATUS;
	}
	public Long getMENU_ID() {
		return MENU_ID;
	}
	public void setMENU_ID(Long mENU_ID) {
		MENU_ID = mENU_ID;
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
