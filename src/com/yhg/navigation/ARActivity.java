package com.yhg.navigation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class ARActivity extends Activity {

	private Context myContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myContext = (MyContext)getApplication();
		MyContext.activityList.add(this);
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		for(Activity activity : MyContext.activityList){
			if(!activity.isFinishing())
				activity.finish();
		}
		System.exit(0);
		super.finish();
	}
	
	

}
