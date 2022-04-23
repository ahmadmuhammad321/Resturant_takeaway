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
@Table(name = "TBLMENU")
public class Menu {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long MENU_ID;
	@Column(name = "PRICE")
	private Long PRICE;
	@ManyToOne
	@JoinColumn(name = "FOOD_ID")
	private Food FOOD_ID;
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
	public long getMENU_ID() {
		return MENU_ID;
	}
	public void setMENU_ID(long mENU_ID) {
		MENU_ID = mENU_ID;
	}
	public Long getPRICE() {
		return PRICE;
	}
	public void setPRICE(Long pRICE) {
		PRICE = pRICE;
	}
	public Food getFOOD_ID() {
		return FOOD_ID;
	}
	public void setFOOD_ID(Food fOOD_ID) {
		FOOD_ID = fOOD_ID;
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
