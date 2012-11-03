package com.maveric;

import java.io.Serializable;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class ExpertMap implements Serializable {

	private HashMap<String, JSONObject> selectedExpertArray = new HashMap<String, JSONObject>();

	JSONObject expertData;

	public HashMap<String, JSONObject> getExpertArray() {
		return selectedExpertArray;
	}
	
	public void setExpertArray(HashMap<String, JSONObject> data) {
		
		selectedExpertArray=data;
	}

	public JSONObject getJsonData(String id) {
		return selectedExpertArray.get(id);
	}

	public void setData(String id, JSONObject JO) {
		selectedExpertArray.put(id, JO);
	}

	public void setJsonData(JSONObject data) {
		expertData = data;
	}

	public int getCount() {
		return selectedExpertArray.size();
	}

	public String getId() throws JSONException {
		return expertData.getString("id");
	}

	public String getTitle() throws JSONException {
		return expertData.getString("title");
	}

	public String getDescription() throws JSONException {
		return expertData.getString("title");
	}
	


}
