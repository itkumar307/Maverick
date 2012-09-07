package com.maveric.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.maveric.R;

import android.content.Context;
import android.util.Log;

public class WSclient {
	public Context ctx;
	public static final String META = "meta";
	public static final String DATA = "data";
	String url = null;
	String result;
	private StatusLine httpStatus;
	String userName;
	String password;
	private boolean isApiCallSuccessful;
	private String responseString;

	/*
	 * Constructor by using access without manual username and password
	 */
	public WSclient(String url, Context ctx) {
		try {
			this.ctx = ctx;
			this.url = url;

			getHttpResponse(url, null, null);

		} catch (Exception e) {
			Log.e("WSclient", "Error call response method: " + e.getMessage(),
					e);
		}
	}

	/*
	 * Constructor by using access manual username and password
	 */

	public WSclient(String url, Context ctx, String userName, String password) {

		try {
			this.ctx = ctx;
			this.url = url;

			getHttpResponse(url, userName, password);
		} catch (Exception e) {

		}
	}

	/**
	 * This gets HttpResponse and saves in the object for getMeta() and
	 * getData() calls.
	 * 
	 * @return
	 */
	public void getHttpResponse(String url, String userName, String password) {

		InputStream is = null;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		/*
		 * set password for authenticate server
		 */

		if (userName == null) {
			this.userName = ctx.getResources().getString(
					R.string.HTTP_ACCESSS_NAME);
			this.password = ctx.getResources().getString(
					R.string.HTTP_ACCESSS_PASSWORD);
		} else {
			this.userName = userName;
			this.password = password;
		}

		HttpResponse response;
		try {
			client.getCredentialsProvider().setCredentials(
					new AuthScope(null, -1),
					new UsernamePasswordCredentials(this.userName,
							this.password));
			response = client.execute(request);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception ex) {
			Log.e("WSclient", "Error in http connection: " + ex.getMessage(),
					ex);
			return;
		}
		int responseCode = response.getStatusLine().getStatusCode();
		httpStatus = response.getStatusLine();

		if (responseCode == HttpStatus.SC_UNAUTHORIZED
				|| responseCode == HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED) {
			Log.w("kumar", "Authentication failed for API " + request.getURI());
		}
		Log.w("kumar", "HTTP status code :"
				+ response.getStatusLine().getStatusCode());

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
			Log.i("WSclient", "result" + result);
		} catch (Exception ex) {
			Log.e("WSclient", "Error in http connection: " + ex.toString());
		}

	}

	/*
	 * Returns META part of the WebService output
	 */
	public JSONObject getMeta() {

		try {
			// JSONArray jsa = new JSONArray(this.result);
			JSONObject jo = new JSONObject(this.result);
			JSONObject metaData = jo.getJSONObject(META);
			Log.i("WSclient", "getMeta(): Res: " + metaData);

			if (metaData != null) {
				Log.i("kumar", "metaData for API " + url + "; meta :"
						+ metaData);
				String metaResult = metaData.getString("result");
				responseString = metaResult;
				if (metaResult != null && metaResult.equals("SUCCESS")) {
					isApiCallSuccessful = true;
				}
			} else {
				Log.i("kumar", "No metaData for API " + url);
			}
			return metaData;
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

	public boolean isApiCallSuccessful() {
		return httpStatus != null
				&& httpStatus.getStatusCode() == HttpStatus.SC_OK
				&& isApiCallSuccessful;
	}

	/*
	 * response string
	 */

	public String getResponseString() {
		return responseString;
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