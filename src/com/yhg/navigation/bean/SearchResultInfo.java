package com.yhg.navigation.bean;

import com.baidu.platform.comapi.basestruct.GeoPoint;

public class SearchResultInfo {
	/**
	 * 地址信息
	 */
	public String address;
	
	/**
	 * poi所在城市
	 */
	public String city;
	
	
	/**
	 * poi类型，0：普通点，1：公交站，2：公交线路，3：地铁站，4：地铁线路,
	 */
	public int ePoiType;
	
	/**
	 * 是否有美食类详情页面
	 */
	public boolean hasCaterDetails;
	
	
	/**
	 * 名称
	 */
	public String name;
	
	
	/**
	 * 电话信息
	 */
	public String phoneNum;
	
	
	/**
	 * 邮编
	 */
	public String postCode;
	
	
	/**
	 * 坐标，ePoiType为2或4时，PT为空
	 */
	public GeoPoint pt;
	
	
	public String uid;
	
	/**
	 * 距离
	 */
	public double distance;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getePoiType() {
		return ePoiType;
	}

	public void setePoiType(int ePoiType) {
		this.ePoiType = ePoiType;
	}

	public boolean isHasCaterDetails() {
		return hasCaterDetails;
	}

	public void setHasCaterDetails(boolean hasCaterDetails) {
		this.hasCaterDetails = hasCaterDetails;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public GeoPoint getPt() {
		return pt;
	}

	public void setPt(GeoPoint pt) {
		this.pt = pt;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	

}
