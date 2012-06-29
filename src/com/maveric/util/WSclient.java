package com.maveric.util;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class WSclient {
	public Context ctx;
	public static final String META = "meta";
	public static final String DATA = "data";
	String url = null;
	String result;

	/*
	 * Constructor
	 */
	public WSclient(String url, Context ctx) {
		try {
			this.ctx = ctx;
			this.url = url;
			
			getHttpResponse(url);
		} catch (Exception e) {
			
		}
	}

	/**
	 * This gets HttpResponse and saves in the object for getMeta() and
	 * getData() calls.
	 * 
	 * @return
	 */
	public void getHttpResponse(String url) {
		InputStream is = null;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		try {
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception ex) {
			Log.e("WSclient",
					"Error in http connection: " + ex.toString());
		}
		try {

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line + "\n");
			}
			is.close();
			this.result = str.toString();
			Log.i("WSclient","result"+result);
		} catch (Exception ex) {
			Log.e("WSclient",
					"Error in http connection: " + ex.toString());
		}

	}

	/*
	 * Returns META part of the WebService output
	 */
	public JSONObject getMeta() {
		
		try {
			JSONArray jsa = new JSONArray(this.result);
			JSONObject jo = (JSONObject) jsa.get(0);
			JSONObject res = jo.getJSONObject(META);
			Log.i("WSclient", "getMeta(): Res: " + res);
			return res;
		} catch (JSONException e) {
			/*
			 * TODO: We should never fail to give META of last HttpResponse
			 */
			Log.e("WSclient", "getMeta() : " + e.getMessage());
			return null;
		} catch (Exception e) {
			/*
			 * How do we propagate Http errors back to caller properly?
			 */

			// TODO exception handling to be improved. we need to have precise
			// message and log the errors.
			throw new RuntimeException(e);
		}
	}

	/*
	 * Returns DATA part of the WebService output
	 */
	public JSONArray getData() {
		try {
			JSONArray jsa = new JSONArray(this.result);
			JSONObject jo = (JSONObject) jsa.get(0);
			JSONArray res = jo.getJSONArray(DATA);
			return res;
		} catch (JSONException e) {
			/*
			 * We get "No data" Exception error when DATA is empty. This is a
			 * perfectly fine case.
			 */
			return null;
		} catch (Exception e) {
			/*
			 * How do we propagate Http errors back to caller properly?
			 */

			// TODO exception handling to be improved. we need to have precise
			// message and log the errors.
			throw new RuntimeException(e);
		}
	}
}