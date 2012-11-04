package com.maveric;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.maveric.util.WSclient;

public class ExpertTalkActivity extends MavericListBaseActiity {

	protected Context context;
	ArrayList<HashMap<String, String>> mylist;

	Apppref app;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		app = new Apppref(context);
		mylist = new ArrayList<HashMap<String, String>>();

		try {
			if (isNetworkAvailable()) {

				String expertTalkUrl = getString(R.string.HTTP)
						+ getString(R.string.HTTP_DOMAIN)
						+ getString(R.string.HTTP_SUB)
						+ getString(R.string.HTTP_USER_EXPERT_TALK_JSON_API);

				WSclient expertResponse = new WSclient(expertTalkUrl, context,
						app.getUserNameOnly(), app.getPasswordonly());
				expertResponse.getMeta();

				if (!expertResponse.isApiCallSuccessful()) {
					Intent s = new Intent(context, ExpertTalkViewActivity.class);
					s.putExtra("description",
							"unexpected condition occured try after some time");
					startActivity(s);

					return;
				}

				try {
					JSONArray expertArray = expertResponse.getData();

					for (int i = 0; i < expertArray.length(); i++) {

						HashMap<String, String> map = new HashMap<String, String>();

						JSONObject jo = (JSONObject) expertArray.get(i);

						map.put("id", String.valueOf(i));
						map.put("title", jo.getString("title"));
						map.put("description", jo.getString("description"));
						mylist.add(map);
					}
				} catch (Exception e) {
					Log.e("kumar", "error in parsing json" + e.getMessage());
				}

			} else {
				toast(getString(R.string.NO_INTERNET_CONNECTION));
				this.finish();
			}

		} catch (Exception e) {
			Log.e("kumar", "error in serever call " + e.getMessage());

		}

		ListAdapter adapter = null;
		try {
			adapter = new SimpleAdapter(this, mylist,
					R.layout.experttaketabview, new String[] { "title" },
					new int[] { R.id.titlename1 });
		} catch (Exception e) {
			Log.e("kumar", "showlist" + e.getMessage());

		}

		setListAdapter(adapter);

		final ListView lv = getListView();
		lv.setBackgroundColor(Color.WHITE);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				@SuppressWarnings("unchecked")
				HashMap<String, String> o = (HashMap<String, String>) lv
						.getItemAtPosition(position);

				Intent s = new Intent(context, ExpertTalkViewActivity.class);
				s.putExtra("description", o.get("description"));
				startActivity(s);

			}
		});

	}

	@Override
	protected void setContentToLayout() {
		// TODO Auto-generated method stub

	}

}
