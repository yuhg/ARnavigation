package com.yhg.navigation;

import java.util.TimerTask;

import com.yhg.navigation.R;
import com.yhg.navigation.R.id;
import com.yhg.navigation.R.layout;
import com.yhg.navigation.util.Logs;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CameraActivity extends ARActivity implements SurfaceHolder.Callback{

	private final String TAG = "CameraActivity";
	public MyContext myContext;
	public Button searchButton;
	public EditText searchText;
	public TextView msg;
	public TextView angle;
	
	//相机
	private Camera mCamera;
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;

	public int intScreenX ;
	public int intScreenY ;
	private boolean isPreview = false;
	
	
	public TimerTask task;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		myContext = (MyContext)getApplication();
		setContentView(R.layout.main);
		init();
		initCamera();
	}

	public void init(){
		searchButton = (Button)findViewById(R.id.searchButton);
		searchText = (EditText)findViewById(R.id.searchText);
		msg = (TextView)findViewById(R.id.msg);
		angle = (TextView)findViewById(R.id.angle);
	}
	
	public void setScreenPara(){
		// 取得屏幕解析像素
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		intScreenX = dm.widthPixels;
		intScreenY = dm.heightPixels;
	}
	
	public void initCamera(){
		mSurfaceView = (SurfaceView)findViewById(R.id.mSurfaceView);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(CameraActivity.this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	public void openCamera(){
		if(!isPreview){
			try {
				mCamera = Camera.open();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(mCamera != null && !isPreview){
			try {
				mCamera.setPreviewDisplay(mSurfaceHolder);
				Camera.Parameters para = mCamera.getParameters();
				mCamera.setParameters(para);
				mCamera.setPreviewDisplay(mSurfaceHolder);
				mCamera.startPreview();
				isPreview = true;
				Logs.i("MainActity", "start preview");
			} catch (Exception e) {
				mCamera.release();
				mCamera = null;
				e.printStackTrace();
			}
		}
	}
	
	public void closeCamera(){
		if(mCamera != null && isPreview){
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
			isPreview = false;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		setScreenPara();
		myContext.openLocationTask();
		super.onResume();
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		closeCamera();
		//关闭定位
		myContext.closeLocationTask();
		super.onPause();
	}


	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		openCamera();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}

}
