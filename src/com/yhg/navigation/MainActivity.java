package com.yhg.navigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yhg.navigation.R;
import com.yhg.navigation.R.id;
import com.yhg.navigation.R.menu;
import com.yhg.navigation.bean.SearchResultInfo;
import com.yhg.navigation.util.Logs;
import com.yhg.navigation.util.Util;

import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends CameraActivity implements MKSearchListener,SensorEventListener,OnClickListener{
	
	private String TAG = "MainActivity";
	//地图
	private BMapManager mBMapMan = null;
	private MKSearch mMKSearch = null;
	
	//查询到的信息
	private ArrayList<SearchResultInfo> resultList = new ArrayList<SearchResultInfo>();
	private ArrayList<Object> distanceList = new ArrayList<Object>();
	
	private BDLocation location;
	private int range = 1000;//单位米
	//传感器
	private SensorManager mSensorManager;
	private boolean mRegisteredSensor;
	
	public Handler handler;
	public static final int GET_DISTANCE = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		initMap();
		handlerControl();
	}
	
	public void initMap(){
		mBMapMan = new BMapManager(getApplicationContext());
		mBMapMan.init("B34752EC756CB2C57A8472B8313BC0072AF128CF", null);
		mMKSearch = new MKSearch();
		mMKSearch.init(mBMapMan, this);
		searchButton.setOnClickListener(this);
	}
	
	public void handlerControl(){
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
				case GET_DISTANCE:
					//TODO
//					msg.getData().get(key)
					break;
				}
				super.handleMessage(msg);
			}
			
		};
	}
	

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.searchButton:
			search();
			break;
		}
	}
	
	
	public void search(){
		String text = searchText.getText().toString();
		if(text != null && !text.equals("")){
			location = myContext.getLocation();
			if(location!=null)
			mMKSearch.poiSearchNearBy(text,new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6)) , range);
		}else{
			Toast.makeText(myContext, "请输入搜索内容！", Toast.LENGTH_SHORT).show();
		}
		Logs.i(TAG, "resultList:" + resultList.size() + "---------distanceList:" + distanceList.size());
	}

	public void getDistance(){
		
	}

	//**********************地图查询*****************************
	
	public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
	}

	public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
	}

	public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
	}

	public void onGetPoiDetailSearchResult(int arg0, int arg1) {
	}

	public void onGetPoiResult(MKPoiResult result, int arg1, int arg2) {
		ArrayList<MKPoiInfo> list = result.getAllPoi();
		Logs.i("onGetPoiResult", "result List size:" + list.size());
		StringBuffer s = new StringBuffer();
		
		MKPlanNode myMKPlanNode = new MKPlanNode();
		myMKPlanNode.pt = new GeoPoint((int) (location.getLatitude() * 1e6),(int) (location.getLongitude() * 1e6));
		
		for(MKPoiInfo info : list){
			SearchResultInfo rInfo = new SearchResultInfo();
			rInfo.setAddress(info.address);
			rInfo.setCity(info.city);
			rInfo.setDistance(0);
			rInfo.setePoiType(info.ePoiType);
			rInfo.setHasCaterDetails(info.hasCaterDetails);
			rInfo.setName(info.name);
			rInfo.setPhoneNum(info.phoneNum);
			rInfo.setPostCode(info.postCode);
			rInfo.setPt(info.pt);
			rInfo.setUid(info.uid);
			
			resultList.add(rInfo);
			double dis = Util.calDistance(location.getLatitude() * 1e6,location.getLongitude() * 1e6, (double)info.pt.getLatitudeE6(), (double)info.pt.getLongitudeE6());
			Logs.i("MKPoiInfo", info.address + "---" +dis);s.append(info.name + "---" +dis + "\n"); 
			MKPlanNode targetMKPlanNode = new MKPlanNode();
			targetMKPlanNode.pt = new GeoPoint(info.pt.getLatitudeE6(),info.pt.getLongitudeE6());
			mMKSearch.walkingSearch(null, myMKPlanNode, null,targetMKPlanNode);
		}
		msg.setText(s.toString());
		handler.sendEmptyMessage(GET_DISTANCE);
	}

	public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
	}

	public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
	}

	public int j = 1;
	public void onGetWalkingRouteResult(MKWalkingRouteResult result, int arg1 ) {
		int planNum = result.getNumPlan();
		int disResult = 0; 
		List disList = new ArrayList<Integer>();
		for(int i = 0;i < planNum;i++){
			disList.add(result.getPlan(i).getDistance());
			Logs.i(TAG, j + "----------"+ result.getPlan(i).getDistance() + "----" + arg1);
			j++;
		}
		Collections.sort(disList);
		if(disList.size() > 0) disResult = (Integer)disList.get(0);
		
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putInt("DISTANCE", disResult);
		msg.setData(data);
		msg.what = GET_DISTANCE;
		handler.sendMessage(msg);
	}

	
	//**********************传感器*****************************
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	public void onSensorChanged(SensorEvent event) {
		 // 接受方向感应器的类型  
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)  
        {  
            float x = event.values[SensorManager.DATA_X];  
            float y = event.values[SensorManager.DATA_Y];  
            float z = event.values[SensorManager.DATA_Z];  
//            Logs.i("sensor", x + "********" + y + "********" + z);
            if(!Util.getDegreeString(x).equals(""))angle.setText(Util.getDegreeString(x));
        }  
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//注册SensorManager  
		List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		if(sensors.size() > 0){
			 Sensor sensor = sensors.get(0);  
	            mRegisteredSensor = mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);  
		}
	}

	@Override
	protected void onPause() {
		//关闭感应器
		if (mRegisteredSensor)  
	        {  
	            mSensorManager.unregisterListener(this);  
	            mRegisteredSensor = false;  
	        }  
		super.onPause();
	}
	
	

}
