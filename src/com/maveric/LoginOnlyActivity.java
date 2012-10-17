package com.maveric;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.maveric.util.SUtil;
import com.maveric.util.WSclient;

public class LoginOnlyActivity extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.loginonlycontainer);

	}

	private LinearLayout signInLayout, signUpLayout;
	private Button signUp, signIn, ok, cancel;
	private Apppref appPref;
	Context ctx;
	private ProgressDialog progressDialog;
	String userNameString;
	String passWordString;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		signUp = (Button) findViewById(R.id.signin_signup);
		signIn = (Button) findViewById(R.id.signin_signin);
		ok = (Button) findViewById(R.id.signup_ok);
		cancel = (Button) findViewById(R.id.signup_cancel);
		ctx = getApplicationContext();

		final EditText userNameIn = (EditText) findViewById(R.id.signin_user_name);
		final EditText passWordIn = (EditText) findViewById(R.id.signin_password);
		final EditText userNameUp = (EditText) findViewById(R.id.signup_user_name);
		final EditText passWordUp = (EditText) findViewById(R.id.signup_password);
		final EditText conformPwdUp = (EditText) findViewById(R.id.signup_confrm_pwd);

		signInLayout = (LinearLayout) findViewById(R.id.signinlayout);
		signUpLayout = (LinearLayout) findViewById(R.id.signuplayout);

		appPref = new Apppref(context);
		if (appPref.getSignInOnly())
			gotoHomeActivity();

		signIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				userNameString = userNameIn.getText().toString();
				passWordString = passWordIn.getText().toString();

				if (!isAllFilled(userNameString, passWordString, "checkdata")) {
					toast(" Please fill out all required fields");
					return;
				}

				if (!SUtil.hasRegularExpressionMatch(
						getString(R.string.EMAIL_ID_FORMAT_EXPRESSION),
						userNameString)) {
					toast("Pls enter correct email id");
					return;
				}

				if (!isNetworkAvailable()) {
					toast("Please check your internet connection");
					return;
				}
				progressDialog = ProgressDialog.show(LoginOnlyActivity.this,
						"Loading...", "please wait we are checking your data");
				new Thread() {
					public void run() {
						try {
							String registerUrl = ctx.getResources().getString(
									R.string.HTTP)
									+ ctx.getResources().getString(
											R.string.HTTP_DOMAIN)
									+ ctx.getResources().getString(
											R.string.HTTP_SUB)
									+ ctx.getResources().getString(
											R.string.HTTP_USER_LOGIN)
									+ userNameString + "/" + passWordString;
							WSclient registerResponse = new WSclient(
									registerUrl, ctx);
							registerResponse.getMeta();
							if (registerResponse.isApiCallSuccessful()) {
								handlerMsg.sendEmptyMessage(3);
							} else {
								handlerMsg.sendEmptyMessage(2);
							}
						} catch (Exception e) {
							Log.i("kumar", "loginerror" + e.getMessage(), e);
							handlerMsg.sendEmptyMessage(1);

						}
					}
				}.start();
			}
		});

		signUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				signInLayout.setVisibility(View.GONE);
				signUpLayout.setVisibility(View.VISIBLE);
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				signUpLayout.setVisibility(View.GONE);
				signInLayout.setVisibility(View.VISIBLE);

			}
		});

		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				userNameString = userNameUp.getText().toString();
				passWordString = passWordUp.getText().toString();
				String conformPassString = conformPwdUp.getText().toString();

				if (isAllFilled(userNameString, passWordString,
						conformPassString)) {
					if (!checkPassWord(passWordString, conformPassString)) {
						toast("Your passwords do not match - please try again");
						passWordUp.setText(" ");
						conformPwdUp.setText(" ");
						return;
					}

				} else {
					toast("Please fill out all required fields");
					return;
				}

				if (!SUtil.hasRegularExpressionMatch(
						getString(R.string.EMAIL_ID_FORMAT_EXPRESSION),
						userNameString)) {

					toast(" Please enter a valid email ID");
					return;
				}

				if (!isNetworkAvailable()) {
					toast("Internet connection required");
					return;
				}

				progressDialog = ProgressDialog.show(LoginOnlyActivity.this,
						"Loading...", "Wait a few sec your data is saving");
				new Thread() {
					public void run() {
						try {
							String registerUrl = ctx.getResources().getString(
									R.string.HTTP)
									+ ctx.getResources().getString(
											R.string.HTTP_DOMAIN)
									+ ctx.getResources().getString(
											R.string.HTTP_SUB)
									+ ctx.getResources().getString(
											R.string.HTTP_USER_ADD)
									+ userNameString + "/" + passWordString;
							WSclient registerResponse = new WSclient(
									registerUrl, ctx);
							registerResponse.getMeta();
							if (registerResponse.isApiCallSuccessful()) {
								handlerMsg.sendEmptyMessage(3);
							} else {
								handlerMsg.sendEmptyMessage(0);
							}
						} catch (Exception e) {
							Log.i("kumar", "loginerror" + e.getMessage(), e);
							handlerMsg.sendEmptyMessage(1);

						}
					}
				}.start();
			}
		});

	}

	Handler handlerMsg = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
			case 0:
				toast("ID is duplicate.Please will you go ahead using another ID");
				break;
			case 1:
				toast("oh Sorry got unexpectedcondition please try some time");
				break;
			case 2:
				toast("username and password is not matched");
				break;
			case 3:
				appPref.setUserNameOnly(userNameString);
				appPref.setPasswordonly(passWordString);
				appPref.setSignInOnly(true);
				gotoHomeActivity();
				break;
			}
		}
	};

	protected boolean checkPassWord(String passWordString,
			String conformPassString) {
		if (passWordString.equalsIgnoreCase(conformPassString)) {
			return true;
		}
		return false;
	}

	private Boolean isAllFilled(String first, String third, String Fourth) {
		if (first.length() > 0 && third.length() > 0 && Fourth.length() > 0)
			return true;
		else
			return false;
	}

	private void gotoHomeActivity() {
		Intent home = new Intent(context, WorkSummeryActivity.class);
		startActivity(home);
		this.finish();
	}
}
