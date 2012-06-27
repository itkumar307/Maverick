package com.maveric.obj.type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.maveric.R;
import com.maveric.contentprovider.ProfileProvider;
import com.maveric.database.model.ProfileTable;

public class Profile {

	private Context ctx;
	private String userName;
	private String emailId;
	private String passWord;
	private Float currentHeight;
	private Float currentWeight;
	private Float currentHip;
	private Float waist;
	private Float currentBmi;
	private Float targetHeight;
	private Float targetWeight;
	private Float targetHip;

	public Profile(Context ctx) {
		this.ctx = ctx;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setPassword(String passWord) {
		this.passWord = passWord;
	}

	public String getPassword() {
		return passWord;
	}

	public void setCurrentHeight(Float height) {
		this.currentHeight = height;
	}

	public Float getCurrentHeight() {
		return currentHeight;
	}

	public void setCurrentWeight(Float weight) {
		this.currentWeight = weight;
	}

	public Float getCurrentWeight() {
		return this.currentWeight;
	}

	public void setCurrentHip(Float hip) {
		this.currentHip = hip;
	}

	public Float getCurrentHip() {
		return currentHip;
	}

	public void setWaist(Float waist) {
		this.waist = waist;
	}

	public Float getWaist() {
		return waist;
	}

	public void setTargetHeight(Float height) {
		this.targetHeight = height;
	}

	public Float getTargetHeight() {
		return targetHeight;
	}

	public void setTargetWeight(Float weight) {
		this.targetWeight = weight;
	}

	public Float getTargetWeight() {
		return this.targetWeight;
	}

	public void setTargetHip(Float hip) {
		this.targetHip = hip;
	}

	public Float getTargetHip() {
		return targetHip;
	}

	public void setBmi(Float bmi) {
		this.currentBmi = bmi;
	}

	public Float getBmi() {
		return currentBmi;
	}

	public void setEmailId(String email) {
		this.emailId = email;
	}

	public String getEmailId() {
		return emailId;
	}

	public JSONArray toJsonArray() {
		JSONObject jobj = new JSONObject();
		try {
			if (userName != null) {
				jobj.put(ProfileTable.Column.USER_NAME, userName);
				jobj.put(ProfileTable.Column.EMAIL_ID, emailId);
				jobj.put(ProfileTable.Column.PASSWORD, passWord);
				jobj.put(ProfileTable.Column.CURRENT_HEIGHT, currentHeight);
				jobj.put(ProfileTable.Column.CURRENT_HIP, currentHip);
				jobj.put(ProfileTable.Column.CURRENT_WEIGHT, currentWeight);
				jobj.put(ProfileTable.Column.CURRENT_WAIST, waist);
				jobj.put(ProfileTable.Column.CURRENT_BMI, currentBmi);
				jobj.put(ProfileTable.Column.TARGET_HEIGHT, targetHeight);
				jobj.put(ProfileTable.Column.TARGET_WEIGHT, targetWeight);
				jobj.put(ProfileTable.Column.TARGET_HIP, targetHip);
			}
			return new JSONArray().put(jobj);

		} catch (JSONException e) {
			Log.e(ctx.getString(R.string.app_name), e.getMessage(), e);
			return null;
		}

	}

	public ContentValues toContentValues() {
		ContentValues values = new ContentValues();
		Log.d(ctx.getString(R.string.app_name), "CURRENT_HEIGHT = "
				+ currentHeight + " EMAIL_ID = " + emailId);
		values.put(ProfileTable.Column.CURRENT_HEIGHT, currentHeight);
		values.put(ProfileTable.Column.CURRENT_WEIGHT, currentWeight);
		values.put(ProfileTable.Column.CURRENT_HIP, currentHip);
		values.put(ProfileTable.Column.CURRENT_WAIST, waist);
		values.put(ProfileTable.Column.CURRENT_BMI, currentBmi);
		values.put(ProfileTable.Column.TARGET_HEIGHT, targetHeight);
		values.put(ProfileTable.Column.TARGET_WEIGHT, targetWeight);
		values.put(ProfileTable.Column.TARGET_HIP, targetHip);
		values.put(ProfileTable.Column.EMAIL_ID, emailId);
		values.put(ProfileTable.Column.PASSWORD, passWord);
		values.put(ProfileTable.Column.USER_NAME, userName);
		return values;
	}

	public void flush() {
		try {
			this.ctx.getContentResolver().insert(ProfileProvider.INSERT_URI,
					this.toContentValues());
		} catch (Exception e) {
			Log.e(ctx.getString(R.string.app_name), "profile insert failed "
					+ e.getMessage(), e);
		}
	}

	public String toString() {
		return "[ UserName :" + userName + "; " + "EmailId :" + emailId + "; "
				+ "Password :" + passWord + "; " + "CurrentHeight :"
				+ currentHeight + "; " + "CurrentWeight :" + currentWeight
				+ "; " + "CurrentBmi :" + currentBmi + ": " + "CurrentHip :"
				+ currentHip + "; " + "Waist :" + waist + "; "
				+ "TargetHeight :" + targetHeight + "; " + "TargetWeight : "
				+ targetWeight + "; " + "TargetHip : " + targetHip + "]";
	}
}
