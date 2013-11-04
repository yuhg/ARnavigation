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
	
	//间隔时间
	private static int interval = 30*1000;
	private boolean isLocOpened = false;
	private boolean isLocTaskOpened = false;
	private LocationClientOption option = null;
	
	private LocationClient mLocationClient = null; 
	private MyReceiveListener mListenner = new MyReceiveListener();
	public  BDLocation location;
	// 定时器
	private Timer myLocationTimer = null;
	// 定时线程
	private TimerTask myLocationTimerTask = null;
	
	//用于那些无法直接获取全局环境的地方
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
			if (!isLocOpened) // 如果没有打开
			{
				option = new LocationClientOption();
				option.setCoorType("gcj02"); // 设置返回的坐标类型
				option.setScanSpan(interval); // 设置时间
				option.setAddrType("detail"); // 返回地址类型
				mLocationClient.setLocOption(option);
				mLocationClient.start(); // 打开定位
				isLocOpened = true; // 标识为已经打开了定位
			}
		} catch (Exception e) {
			Logs.i(TAG, "打开定位异常:" + e.toString());
		}
	}

	private void closeLocation() {
		try {
			mLocationClient.stop(); // 结束定位
			isLocOpened = false; // 标识为已经结束了定位
		} catch (Exception e) {
			Logs.i(TAG, "结束定位异常:" + e.toString());
		}
	}
	
	
	
	/***
	 * 打开定位定时器线程
	 */
	public void openLocationTask() {
		try {
			if (!isLocTaskOpened)
			{
				startLocation();
				initLocationTimeAndTimeTask();
				myLocationTimer.schedule(myLocationTimerTask, interval, interval);
				isLocTaskOpened = true;
				Logs.i(TAG, " 打开了定位定时器线程 ");
			} else {
				Logs.i(TAG, " 已经开启了定位定时器线程 ");
			}
		} catch (Exception e) {
			Logs.i(TAG, "打开定位定时器线程 异常" + e.toString());
		}
	}
	
	 //初始化定时对象
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

	//获取经纬度
	public void getLocationInfo() {
		/**
		 * 0：正常。
		 * 1：SDK还未启动。
		 * 2：没有监听函数。
		 * 6：请求间隔过短。
		 */
		int i = mLocationClient.requestLocation();
		switch (i) {
		case 0:
			Logs.i(TAG, "requestLocation() : " + "正常。");
			break;
		case 1:
			Logs.i(TAG, "requestLocation() : " + "SDK还未启动。");
			break;
		case 2:
			Logs.i(TAG, "requestLocation() : " + "没有监听函数。 ");
			break;
		case 6:
			Logs.i(TAG, "requestLocation() : " + "请求间隔过短。 ");
			break;
		default:
			Logs.i(TAG, "requestLocation() : " + "其他原因	");
		}
	}

	/***
	 * 关闭定位定时器线程
	 */
	public void closeLocationTask() {
		try {
			if (isLocTaskOpened)
			{
				closeLocation();
				myLocationTimer.cancel();
				destroyLocationTimeAndTimeTask();
				isLocTaskOpened = false;
				Logs.i(TAG, " 关闭了定位定时器线程 ");
			} else {
				Logs.i(TAG, " 已经关闭了定位定时器线程 ");
			}
		} catch (Exception e) {
			Logs.i(TAG, "关闭定位定时器线程异常: " + e.toString());
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
