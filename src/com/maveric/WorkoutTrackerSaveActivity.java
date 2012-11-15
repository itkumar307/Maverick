package com.maveric;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.maveric.contentprovider.ExceriseProvider;
import com.maveric.contentprovider.WorkoutProvider;
import com.maveric.database.model.ExceriseValue;
import com.maveric.database.model.WorkOutTrackerTable;

public class WorkoutTrackerSaveActivity extends MavericBaseActiity {

	Context ctx;
	TextView countTypeText;
	TextView countTypeTime;
	TextView exceriseTypeText;
	// TextView woroutCalories;
	EditText inputData;
	Button saveData;
	String countData;
	CheckBox favoriteDataSave;
	Cursor checkSub;

	String exceriseType;
	String Type;
	String intensityData;
	String calories;
	Boolean isCheckbox = false;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.workoutdatasavecontainer);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ctx = getApplicationContext();

		Bundle extras = getIntent().getExtras();

		exceriseType = extras.getString("exceriseName");
		Type = extras.getString("type");
		calories = extras.getString("calories");

		Log.i("kumar", "calories" + calories);

		exceriseTypeText = (TextView) findViewById(R.id.excerisetypetext);
		exceriseTypeText.setText(exceriseType);
		countTypeText = (TextView) findViewById(R.id.favouritebefretext);
		countTypeTime = (TextView) findViewById(R.id.favouriteaftertext);
		inputData = (EditText) findViewById(R.id.favouritecount);
		favoriteDataSave = (CheckBox) findViewById(R.id.favoritcheckbox);

		saveData = (Button) findViewById(R.id.saveexcerisedata);

		checkType();
		checkSubIntensity();

		inputData.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				try {
					countData = inputData.getText().toString();

					if (Integer.parseInt(countData) > 300) {
						toast("please Enter correct value,Are you did excerise more than five hour? dont cheat");
						return;
					}
				} catch (NumberFormatException e) {
					toast(" Please enter numbers only - alphabets are not accepted");
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				// if (!intensityData.equalsIgnoreCase("nothing")) {
				// toast(" Please eselect intensity data");
				// return;
				// }

			}
		});

		favoriteDataSave
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						isCheckbox = isChecked;

					}
				});

		saveData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (isAllfilled()) {
					final ProgressDialog progressDialog = ProgressDialog.show(
							WorkoutTrackerSaveActivity.this, "Saving...",
							"Wait a few secs while your data is being saved");

					new Thread() {
						public void run() {
							try {
								sleep(1000);
								Bundle dateSelected = getIntent().getExtras();
								String selectedDate = dateSelected
										.getString("date");
								Log.i("kumar" + this.getClass(), "date"
										+ selectedDate);
								ContentValues values = new ContentValues();

								values.put(WorkOutTrackerTable.Column.DATE,
										selectedDate);
								values.put(
										WorkOutTrackerTable.Column.SELECT_EXCERISE,
										exceriseType);

								// calories calculation depends upon type if
								// type 0 means mulptiply calories otherwise
								// leave it

								try {
									if (Type.equalsIgnoreCase("0")) {

										countData = String.valueOf(Integer
												.parseInt(calories)
												* Integer.parseInt(countData));
										Log.i("kumar", "countData" + countData);

									} else {

										countData = "0";

									}
								} catch (Exception e) {
									Log.e("kumar",
											"formatException" + e.getMessage());
									/*
									 * default value
									 */
									countData = "50";
								}

								values.put(WorkOutTrackerTable.Column.WORKOUT,
										countData);

								getContentResolver()
										.insert(WorkoutProvider.INSERT_WORKOUT_DETAILS_URI,
												values);
								// save favourite

								if (isCheckbox) {
									ContentValues favoriteValues = new ContentValues();
									favoriteValues
											.put(ExceriseValue.Column.FAVOURITE_STATUS,
													"1");
									getContentResolver().update(
											ExceriseProvider.ADD_FAVOURITE_URI,
											favoriteValues, exceriseType, null);
								}

								handler.sendEmptyMessage(0);
							} catch (Exception e) {
								if (progressDialog != null) {
									progressDialog.dismiss();
								}
								Log.e("kumar:" + this.getClass(),
										"error in sve data into workout table"
												+ e.getMessage(), e);
								WorkoutTrackerSaveActivity.this.finish();
							}
							progressDialog.dismiss();
						}
					}.start();
				} else
					toast(getString(R.string.REQUIRE_FIELD_TOAST));
			}

		});

	}

	private void checkType() {
		if (Type.equalsIgnoreCase("0")) {
			// timing excerise

			countTypeText.setText("How Long?");
			countTypeTime.setText("minutes");

		} else {

			// counting excerise

			countTypeText.setText("Reputation");
			countTypeTime.setText("count");

		}

	}

	private void checkSubIntensity() {
		try {

			Uri name = Uri.withAppendedPath(
					ExceriseProvider.EXCERISETYPE_SEARCH_SUB_URI, exceriseType);
			checkSub = managedQuery(name, null, null, null, null);

			if (checkSub.getCount() > 0) {
				Spinner spin = (Spinner) findViewById(R.id.spinner1);
				spin.setVisibility(View.VISIBLE);

				String[] from = new String[] { ExceriseValue.Column.INTENSITY_SELECT };
				int[] to = new int[] { android.R.id.text1 };
				SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
						android.R.layout.simple_spinner_item, checkSub, from,
						to);

				// set layout for activated adapter
				sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				// get xml file spinner and set adapter

				spin.setAdapter(sca);

				spin.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {

						intensityData = checkSub.getString(checkSub
								.getColumnIndex(ExceriseValue.Column.INTENSITY_SELECT));

						calories = checkSub.getString(checkSub
								.getColumnIndex(ExceriseValue.Column.KG60));

						Log.i("kumar", "intentdata" + intensityData);

						exceriseTypeText.setText(exceriseType + " "
								+ intensityData);

					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

			} else {
				intensityData = "nothing";
			}

		} catch (Exception e) {
			Log.e("kumar", "error in intensity search" + e.getMessage());
		}

	}

	private Boolean isAllfilled() {
		return !(TextUtils.isEmpty(countData));
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				WorkoutTrackerSaveActivity.this.finish();
				break;
			case 1:
				toast("favorite saved successfully");
			}
		}

	};
}
