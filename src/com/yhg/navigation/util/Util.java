package com.yhg.navigation.util;

public class Util {

	
	
	
	/**
	 * @description 获取角度描述信息 20130823
	 * @param degree
	 * @return
	 */
	public static String getDegreeString(float degree) {
		float _decDegree = 0;
		String _message = "";
		// 设置灵敏度
		if (Math.abs(_decDegree - degree) >= Constants.SENSITIVITY) {
			_decDegree = degree;
			String degreeStr = String.valueOf(_decDegree);
			// 指向正北
			if (_decDegree > 360 -  Constants.SENSITIVITY && _decDegree < 360 +  Constants.SENSITIVITY) {
				_message = "正北 " + degreeStr + "°";
			}

			// 指向正东
			if (_decDegree > 90 -  Constants.SENSITIVITY && _decDegree < 90 +  Constants.SENSITIVITY) {
				_message = "正东 " + degreeStr + "°";
			}

			// 指向正南
			if (_decDegree > 180 -  Constants.SENSITIVITY && _decDegree < 180 +  Constants.SENSITIVITY) {
				_message = "正南 " + degreeStr + "°";
			}

			// 指向正西
			if (_decDegree > 270 -  Constants.SENSITIVITY && _decDegree < 270 +  Constants.SENSITIVITY) {
				_message = "正西 " + degreeStr + "°";
			}

			// 指向东北
			if (_decDegree > 45 -  Constants.SENSITIVITY && _decDegree < 45 +  Constants.SENSITIVITY) {
				_message = "东北 " + degreeStr + "°";
			}

			// 指向东南
			if (_decDegree > 135 -  Constants.SENSITIVITY && _decDegree < 135 +  Constants.SENSITIVITY) {
				_message = "东南 " + degreeStr + "°";
			}

			// 指向西南
			if (_decDegree > 225 -  Constants.SENSITIVITY && _decDegree < 225 +  Constants.SENSITIVITY) {
				_message = "西南 " + degreeStr + "°";
			}

			// 指向西北
			if (_decDegree > 315 -  Constants.SENSITIVITY && _decDegree < 315 +  Constants.SENSITIVITY) {
				_message = "西北 " + degreeStr + "°";
			}
		}
		return _message;
	}
	
	public static long calDistance(Double a_x_point, Double a_y_point,
			Double b_x_point, Double b_y_point) {
		Double R = new Double(6371);
		Double dlat = (b_x_point - a_x_point) * Math.PI / 180;
		Double dlon = (b_y_point - a_y_point) * Math.PI / 180;
		Double aDouble = Math.sin(dlat / 2) * Math.sin(dlat / 2)
				+ Math.cos(a_x_point * Math.PI / 180)
				* Math.cos(b_x_point * Math.PI / 180) * Math.sin(dlon / 2)
				* Math.sin(dlon / 2);
		Double cDouble = 2 * Math.atan2(Math.sqrt(aDouble),
				Math.sqrt(1 - aDouble));
		long d = Math.round((R * cDouble) * 1000) / 1000;
		return d;

	}

}
