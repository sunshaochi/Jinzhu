package com.beyonditsm.financial.entity;

import java.util.List;

public class CityModel {
	private String name;
	private List<DistrictModel> districtList;
	/**
	 * id : 8d190ef28d1711e5be75eca86ba4ba05
	 * code : 350800
	 * abbr : null
	 * provinceCode : 350000
	 */

	private String id;
	private String code;
	private String abbr;
	private String provinceCode;

	public CityModel() {
		super();
	}

	public CityModel(String name, List<DistrictModel> districtList) {
		super();
		this.name = name;
		this.districtList = districtList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DistrictModel> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictModel> districtList) {
		this.districtList = districtList;
	}


	public void setId(String id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getAbbr() {
		return abbr;
	}

	public String getProvinceCode() {
		return provinceCode;
	}
}
