package com.maveric.obj.type;

import android.content.Context;

public class MaverickDataOrganize {

	private Context ctx;
	private String exceriseType;
	private Integer exceriseTimeWorking;

	public MaverickDataOrganize(Context ctx) {
		this.ctx = ctx;
	}

	public void setExceriseType(String exceriseType) {
		this.exceriseType = exceriseType;
	}

	public String getExceriseType() {
		return exceriseType;
	}

	public void setExceriseTimeWorking(Integer exceriseTimeWorking) {
		this.exceriseTimeWorking = exceriseTimeWorking;
	}

	public Integer getExceriseTimeWorking() {
		return exceriseTimeWorking;
	}
}
