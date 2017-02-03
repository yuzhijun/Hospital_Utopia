package com.lenovohit.hospital_utopia.domain;

import java.util.List;

/**
 * 省市的model
 * 
 * @author yuzhijun
 * @version 创建时间 2015.04.20
 * */
public class CapitalAndCity {
	private String ID;
	private String Title;
	private String CenterE;
	private String CenterN;
	private boolean selected = false;
	private boolean changed = false;
	private List<CapitalAndCity> Childrens;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public List<CapitalAndCity> getChildrens() {
		return Childrens;
	}

	public void setChildrens(List<CapitalAndCity> childrens) {
		Childrens = childrens;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public String getCenterE() {
		return CenterE;
	}

	public void setCenterE(String centerE) {
		CenterE = centerE;
	}

	public String getCenterN() {
		return CenterN;
	}

	public void setCenterN(String centerN) {
		CenterN = centerN;
	}

}
