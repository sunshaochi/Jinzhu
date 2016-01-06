package com.beyonditsm.financial.entity;

public class DistrictModel {
	private String name;
	/**
	 * id : 19dd2fdf8d1711e5be75eca86ba4ba05
	 * code : 350181
	 * abbr : null
	 * cityCode : 350100
	 */

	private String id;
	private String code;
	private String abbr;
	private String cityCode;

	public DistrictModel() {
		super();
	}

	public DistrictModel(String name, String zipcode) {
		super();
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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

	public String getCityCode() {
		return cityCode;
	}
}
