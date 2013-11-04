package com.yhg.navigation;

import com.yhg.navigation.R;
import com.yhg.navigation.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class Splash extends ARActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		new SplashAsyncTask().execute(this);
	}

	private class SplashAsyncTask extends AsyncTask<Context, Integer, Integer> {

		@Override
		protected Integer doInBackground(Context... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Intent intent = new Intent();
			intent.setClass(Splash.this, MainActivity.class);
			startActivity(intent);
			Splash.this.finish();
		}
		
	}



}
