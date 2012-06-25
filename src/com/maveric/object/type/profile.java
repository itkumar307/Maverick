package com.maveric.object.type;

import android.content.Context;

public class profile {

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

	public profile(Context ctx) {
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
}
