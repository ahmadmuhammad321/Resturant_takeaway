package com.cwiztech.takeaway.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TBLFOOD")
public class Food {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long FOOD_ID;
	@Column(name = "FOOD_NAME")
	private String FOOD_NAME;
	@Column(name = "FOOD_QUANTITY")
	private String FOOD_QUANTITY;
	@Column(name = "UNIT_PRICE")
	private Long UNIT_PRICE;
	@Column(name = "FOOD_CATEGORY")
	private String FOOD_CATEGORY;
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
	public long getFOOD_ID() {
		return FOOD_ID;
	}
	public void setFOOD_ID(long fOOD_ID) {
		FOOD_ID = fOOD_ID;
	}
	public String getFOOD_NAME() {
		return FOOD_NAME;
	}
	public void setFOOD_NAME(String fOOD_NAME) {
		FOOD_NAME = fOOD_NAME;
	}
	public String getFOOD_QUANTITY() {
		return FOOD_QUANTITY;
	}
	public void setFOOD_QUANTITY(String fOOD_QUANTITY) {
		FOOD_QUANTITY = fOOD_QUANTITY;
	}
	public Long getUNIT_PRICE() {
		return UNIT_PRICE;
	}
	public void setUNIT_PRICE(Long uNIT_PRICE) {
		UNIT_PRICE = uNIT_PRICE;
	}
	public String getFOOD_CATEGORY() {
		return FOOD_CATEGORY;
	}
	public void setFOOD_CATEGORY(String fOOD_CATEGORY) {
		FOOD_CATEGORY = fOOD_CATEGORY;
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
