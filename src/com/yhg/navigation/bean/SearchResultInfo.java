package com.yhg.navigation.bean;

import com.baidu.platform.comapi.basestruct.GeoPoint;

public class SearchResultInfo {
	/**
	 * ��ַ��Ϣ
	 */
	public String address;
	
	/**
	 * poi���ڳ���
	 */
	public String city;
	
	
	/**
	 * poi���ͣ�0����ͨ�㣬1������վ��2��������·��3������վ��4��������·,
	 */
	public int ePoiType;
	
	/**
	 * �Ƿ�����ʳ������ҳ��
	 */
	public boolean hasCaterDetails;
	
	
	/**
	 * ����
	 */
	public String name;
	
	
	/**
	 * �绰��Ϣ
	 */
	public String phoneNum;
	
	
	/**
	 * �ʱ�
	 */
	public String postCode;
	
	
	/**
	 * ���꣬ePoiTypeΪ2��4ʱ��PTΪ��
	 */
	public GeoPoint pt;
	
	
	public String uid;
	
	/**
	 * ����
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
