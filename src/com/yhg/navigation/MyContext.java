package com.yhg.navigation;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yhg.navigation.util.Logs;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.Message;

public class MyContext extends Application {
	
	private final String TAG = "MyContext";
	
	
	public static ArrayList<Activity>  activityList = new ArrayList<Activity>();
	
	
	
	private final int GET_LOCATION = 1;
	private static MyContext myContext;
	
	//���ʱ��
	private static int interval = 30*1000;
	private boolean isLocOpened = false;
	private boolean isLocTaskOpened = false;
	private LocationClientOption option = null;
	
	private LocationClient mLocationClient = null; 
	private MyReceiveListener mListenner = new MyReceiveListener();
	public  BDLocation location;
	// ��ʱ��
	private Timer myLocationTimer = null;
	// ��ʱ�߳�
	private TimerTask myLocationTimerTask = null;
	
	//������Щ�޷�ֱ�ӻ�ȡȫ�ֻ����ĵط�
	public static MyContext getInstance() {
		return myContext;
	}
	
	
	@Override
	public void onCreate() {
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(mListenner);
	}
	

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == GET_LOCATION){
				getLocationInfo(); 
			}
			super.handleMessage(msg);
		}
	};
	
	public BDLocation getLocation() {
		return location;
	}


	public void setLocation(BDLocation location) {
		Logs.i(TAG, "setLocation-----------");
		this.location = location;
	}

	public  void startLocation() {
		try {
			if (!isLocOpened) // ���û�д�
			{
				option = new LocationClientOption();
				option.setCoorType("gcj02"); // ���÷��ص���������
				option.setScanSpan(interval); // ����ʱ��
				option.setAddrType("detail"); // ���ص�ַ����
				mLocationClient.setLocOption(option);
				mLocationClient.start(); // �򿪶�λ
				isLocOpened = true; // ��ʶΪ�Ѿ����˶�λ
			}
		} catch (Exception e) {
			Logs.i(TAG, "�򿪶�λ�쳣:" + e.toString());
		}
	}

	private void closeLocation() {
		try {
			mLocationClient.stop(); // ������λ
			isLocOpened = false; // ��ʶΪ�Ѿ������˶�λ
		} catch (Exception e) {
			Logs.i(TAG, "������λ�쳣:" + e.toString());
		}
	}
	
	
	
	/***
	 * �򿪶�λ��ʱ���߳�
	 */
	public void openLocationTask() {
		try {
			if (!isLocTaskOpened)
			{
				startLocation();
				initLocationTimeAndTimeTask();
				myLocationTimer.schedule(myLocationTimerTask, interval, interval);
				isLocTaskOpened = true;
				Logs.i(TAG, " ���˶�λ��ʱ���߳� ");
			} else {
				Logs.i(TAG, " �Ѿ������˶�λ��ʱ���߳� ");
			}
		} catch (Exception e) {
			Logs.i(TAG, "�򿪶�λ��ʱ���߳� �쳣" + e.toString());
		}
	}
	
	 //��ʼ����ʱ����
	private void initLocationTimeAndTimeTask() {
			if (myLocationTimer == null) {
				myLocationTimer = new Timer();
			} 
			myLocationTimerTask = new TimerTask() {
				@Override
				public void run() {
					handler.sendEmptyMessage(GET_LOCATION); 
				}
			};
		}

	private void destroyLocationTimeAndTimeTask() {
			myLocationTimer = null;
			myLocationTimerTask = null;
		}

	//��ȡ��γ��
	public void getLocationInfo() {
		/**
		 * 0��������
		 * 1��SDK��δ������
		 * 2��û�м���������
		 * 6�����������̡�
		 */
		int i = mLocationClient.requestLocation();
		switch (i) {
		case 0:
			Logs.i(TAG, "requestLocation() : " + "������");
			break;
		case 1:
			Logs.i(TAG, "requestLocation() : " + "SDK��δ������");
			break;
		case 2:
			Logs.i(TAG, "requestLocation() : " + "û�м��������� ");
			break;
		case 6:
			Logs.i(TAG, "requestLocation() : " + "���������̡� ");
			break;
		default:
			Logs.i(TAG, "requestLocation() : " + "����ԭ��	");
		}
	}

	/***
	 * �رն�λ��ʱ���߳�
	 */
	public void closeLocationTask() {
		try {
			if (isLocTaskOpened)
			{
				closeLocation();
				myLocationTimer.cancel();
				destroyLocationTimeAndTimeTask();
				isLocTaskOpened = false;
				Logs.i(TAG, " �ر��˶�λ��ʱ���߳� ");
			} else {
				Logs.i(TAG, " �Ѿ��ر��˶�λ��ʱ���߳� ");
			}
		} catch (Exception e) {
			Logs.i(TAG, "�رն�λ��ʱ���߳��쳣: " + e.toString());
		}
	}
	
	private class MyReceiveListener implements BDLocationListener{
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null)
				return;
			setLocation(location);
			Logs.i(TAG, location.getLatitude() + "----" + location.getLongitude());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
			}
		}
		public void onReceivePoi(BDLocation arg0) {
		}
		
	}

}
