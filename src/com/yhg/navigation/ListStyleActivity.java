package com.yhg.navigation;

import com.yhg.navigation.adapter.SearchResultAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class ListStyleActivity extends Activity{
	
	private ListView listView;
	private Context myContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_list_style);
		myContext = (MyContext)getApplication();
	}
	
	public void init(){
		listView = (ListView)findViewById(R.id.result_list);
		SearchResultAdapter adapter = new SearchResultAdapter(myContext,null);
	}
	
	

}
