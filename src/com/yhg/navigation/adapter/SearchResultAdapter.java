package com.yhg.navigation.adapter;

import java.util.ArrayList;
import com.yhg.navigation.R;
import com.yhg.navigation.bean.SearchResultInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchResultAdapter extends BaseAdapter {
	
	private ArrayList<SearchResultInfo> resultList;
	private Context myContext;
	private LayoutInflater inflater;
	

	public SearchResultAdapter(Context context,ArrayList<SearchResultInfo> resultList) {
		this.myContext = context;
		this.resultList = resultList;
		inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return resultList.size();
	}

	public Object getItem(int position) {
		return resultList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item, null);
			holder.name = (TextView)convertView.findViewById(R.id.name);
			holder.address = (TextView)convertView.findViewById(R.id.address);
			holder.distance = (TextView)convertView.findViewById(R.id.distance);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		SearchResultInfo info = resultList.get(position);
		holder.name.setText(info.getName());
		holder.address.setText("µÿ÷∑£∫" + info.getAddress());
		holder.distance.setText("æ‡¿Î£∫" + info.getDistance());
		
		return null;
	}
	
	class ViewHolder {
		TextView name;
		TextView address;
		TextView distance;
	}

}
