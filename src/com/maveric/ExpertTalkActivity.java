package com.maveric;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.maveric.util.WSclient;

public abstract class ExpertTalkActivity extends MavericListBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.experttakeview);
	}

	protected Context context;
	ArrayList<HashMap<String, JSONObject>> mylist;
	ExpertMap data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mylist = new ArrayList<HashMap<String, JSONObject>>();

		try {
			if (isNetworkAvailable()) {

				String expertTalkUrl = getString(R.string.HTTP)
						+ getString(R.string.HTTP_DOMAIN)
						+ getString(R.string.HTTP_SUB)
						+ getString(R.string.HTTP_USER_EXPERT_TALK_JSON_API);

				WSclient expertResponse = new WSclient(expertTalkUrl, context);
				expertResponse.getMeta();

				if (!expertResponse.isApiCallSuccessful()) {
					// TODO somthing gonna failure

					return;
				}

				if (!expertResponse.isApiCallSuccessful()) {
					// TODO no expert field in database
				}

				try {

					data = new ExpertMap();

					JSONArray expertArray = expertResponse.getData();

					for (int i = 0; i < expertArray.length(); i++) {

						JSONObject jo = (JSONObject) expertArray.get(i);
						data.setJsonData(jo);
						data.setData(data.getId(), jo);
						mylist.add(data.getExpertArray());
					}

				} catch (Exception e) {
					Log.e("kumar", "error in parsing json" + e.getMessage());
				}

			} else {
				// do somthing without network condtion
			}

		} catch (Exception e) {
			Log.e("kumar", "error in serever call " + e.getMessage());

		}

		ListAdapter adapter = null;
		try {
			adapter = new SimpleAdapter(this, mylist,
					R.layout.data_select_input_cardatat,
					new String[] { data.getTitle() },
					new int[] { R.id.titlename });
		} catch (JSONException e) {

		}

		setListAdapter(adapter);

		final ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			@SuppressWarnings("unchecked")
			HashMap<String, JSONObject> o = (HashMap<String, JSONObject>)lv.getItemAtPosition(position);

				toast("ID '" + o.get("id"));

			}
		});

	}

}
