package com.maveric;

import java.security.KeyStore.LoadStoreParameter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.maveric.contentprovider.ExceriseProvider;
import com.maveric.contentprovider.FavoriteProvider;
import com.maveric.contentprovider.WorkoutProvider;
import com.maveric.database.model.ExceriseValue;
import com.maveric.database.model.FavWorkoutTracterTable;
import com.maveric.database.model.WorkOutTrackerTable;
import com.maveric.enums.FavEnums;
import com.maveric.obj.type.MaverickDataOrganize;

public class WorkoutTrackerActivity extends MavericBaseActiity {

	RelativeLayout exceriseSelect;
	RelativeLayout exceriseTime;
	MaverickDataOrganize maverickData;
	Context ctx;
	Button previousLog;
	Button saveWorkoutData;
	Cursor workoutInfo;
	int width;
	TextView exceriseText;
	TextView workoutText;
	private int selectedColories = 0;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.workoutcontainer);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth();

		ctx = getApplicationContext();

		maverickData = new MaverickDataOrganize(ctx);
		Button loadFromFav = (Button) findViewById(R.id.load_fav);
		Button saveAsFav = (Button) findViewById(R.id.add_as_fav);
		exceriseSelect = (RelativeLayout) findViewById(R.id.selectexercisevalue);
		exceriseTime = (RelativeLayout) findViewById(R.id.selectworkoutvalue);

		previousLog = (Button) findViewById(R.id.workout_date_logs);

		saveWorkoutData = (Button) findViewById(R.id.workoutsave);

		exceriseText = (TextView) findViewById(R.id.selected_exercise_text);

		workoutText = (TextView) findViewById(R.id.selectworkouttext);

		previousLog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(WorkoutTrackerActivity.this,
						WorkoutTrackerViewerActivity.class));

			}
		});

		exceriseSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				exceriseTypeInput();

			}
		});
		exceriseTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				workTimeInput();

			}
		});

		saveWorkoutData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (isAllfilled()) {

					final ProgressDialog progressDialog = ProgressDialog.show(
							WorkoutTrackerActivity.this, "Saving...",
							"Wait a few sec your data is saving");

					new Thread() {
						public void run() {
							try {

								Calendar c = Calendar.getInstance();
								SimpleDateFormat format = new SimpleDateFormat(
										"dd-MM-yyyy");
								String cureentDate = format.format(c.getTime());

								Log.i("kumar" + this.getClass(), "date"
										+ cureentDate);
								ContentValues values = new ContentValues();

								values.put(WorkOutTrackerTable.Column.DATE,
										cureentDate);
								values.put(
										WorkOutTrackerTable.Column.SELECT_EXCERISE,
										maverickData.getExceriseType());
								values.put(WorkOutTrackerTable.Column.WORKOUT,
										maverickData.getExceriseTimeWorking());
								values.put(WorkOutTrackerTable.Column.CALORIES,
										maverickData.getCalories());

								getContentResolver()
										.insert(WorkoutProvider.INSERT_WORKOUT_DETAILS_URI,
												values);
								handler.sendEmptyMessage(0);
							} catch (Exception e) {
								if (progressDialog != null) {
									progressDialog.dismiss();
								}
								Log.i("kumar:" + this.getClass(),
										"error in sve data into workout table"
												+ e.getMessage(), e);
								WorkoutTrackerActivity.this.finish();
							}
							progressDialog.dismiss();
						}
					}.start();
				} else
					toast(getString(R.string.REQUIRE_FIELD_TOAST));
			}
		});
		loadFromFav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("manikk", "loadFromFav onClick");
				getFavName(FavEnums.LOAD_FAV);

			}
		});
		saveAsFav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getFavName(FavEnums.ADD_FAV);

			}
		});
	}

	private void exceriseTypeInput() {
		try {
			final Cursor exceriseCursor = managedQuery(
					ExceriseProvider.EXCERISETYPE_URI, null, null, null, null);
			if (exceriseCursor.getCount() > 0) {
				final Dialog listDialog = new Dialog(
						WorkoutTrackerActivity.this, R.style.PauseDialog);

				listDialog
						.setTitle(getString(R.string.EXCERISE_TYPE_DIALOG_HEAD));
				WindowManager.LayoutParams transparentView = listDialog
						.getWindow().getAttributes();
				transparentView.alpha = 0.7f;

				listDialog.getWindow().setAttributes(transparentView);

				LayoutInflater inflater = (LayoutInflater) WorkoutTrackerActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.alertlist, null, false);
				listDialog.setContentView(view);
				LayoutParams params = listDialog.getWindow().getAttributes();
				params.width = (int) (width / 1.5);
				listDialog.getWindow().setAttributes(
						(android.view.WindowManager.LayoutParams) params);
				listDialog.setCanceledOnTouchOutside(true);

				ListView list = (ListView) listDialog
						.findViewById(R.id.alertlist);
				list.setAdapter(new SimpleCursorAdapter(
						WorkoutTrackerActivity.this,
						R.layout.data_select_input_cardatat, exceriseCursor,
						new String[] { ExceriseValue.Column.EXCERISE_TYPE },
						new int[] { R.id.titlename }));
				listDialog.show();
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View view,
							int id, long position) {
						String exceriseTypeValue = exceriseCursor.getString(exceriseCursor
								.getColumnIndex(ExceriseValue.Column.EXCERISE_TYPE));
						selectedColories = exceriseCursor.getInt(exceriseCursor
								.getColumnIndex(ExceriseValue.Column.CALORIES));
						maverickData.setExceriseType(exceriseTypeValue);
						Log.i("kumar:" + this.getClass(),
								"Input of ExceriseTypedata:"
										+ exceriseTypeValue);
						exceriseText.setText(exceriseTypeValue);
						listDialog.dismiss();
						overridePendingTransition(R.anim.prev_slidein,
								R.anim.prev_slideout);
						if (!TextUtils
								.isEmpty(workoutText.getText().toString()))
							setColories();
					}
				});
			} else {
				// TODO
			}

		} catch (Exception e) {
			Log.e("kumar" + this.getClass(), "excerise input function failed."
					+ e.getMessage(), e);
		}

	}

	private void workTimeInput() {
		try {
			final Integer[] arrayInput = new Integer[24];
			int listPosition = 0;
			for (int i = 1; i < 25; i++) {
				arrayInput[listPosition] = i;
				listPosition++;
			}
			final Dialog listDialog = new Dialog(WorkoutTrackerActivity.this,
					R.style.PauseDialog);
			listDialog.setTitle(R.string.CALORIES_DIALOG_HEAD);
			WindowManager.LayoutParams transparentView = listDialog.getWindow()
					.getAttributes();
			transparentView.alpha = 0.7f;
			listDialog.getWindow().setAttributes(transparentView);

			LayoutInflater inflater = (LayoutInflater) WorkoutTrackerActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.alertlist, null, false);
			listDialog.setContentView(view);
			LayoutParams params = listDialog.getWindow().getAttributes();
			params.width = width / 2;

			listDialog.setCanceledOnTouchOutside(true);

			ListView list = (ListView) listDialog.findViewById(R.id.alertlist);

			list.setAdapter(new ArrayAdapter<Integer>(
					WorkoutTrackerActivity.this,
					R.layout.data_select_input_cardatat, arrayInput));
			listDialog.show();
			listDialog.getWindow().setAttributes(
					(android.view.WindowManager.LayoutParams) params);

			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view,
						int id, long position) {
					try {
						int arrayValue = arrayInput[id];

						maverickData.setExceriseTimeWorking(arrayValue);

						Log.i("kumar:" + this.getClass(),
								"Input of ExceriseworkoutTime:" + arrayValue);
						workoutText.setText("" + arrayValue);
						listDialog.dismiss();
						overridePendingTransition(R.anim.prev_slidein,
								R.anim.prev_slideout);
						if (!TextUtils.isEmpty(exceriseText.getText()
								.toString()))
							setColories();
					} catch (Exception e) {
						Log.e("kumar" + this.getClass(),
								"excerise input function failed."
										+ e.getMessage());
					}
				}

			});
		} catch (Exception e) {
			Log.e("kumar" + this.getClass(),
					"selectthevaluefromdialogue failed." + e.getMessage());
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				WorkoutTrackerActivity.this.finish();
				break;
			}
		}

	};

	private void setColories() {
		TextView Calories_burned_today = (TextView) findViewById(R.id.Calories_burned_today);
		int calories = Integer.valueOf(workoutText.getText().toString().trim())
				* selectedColories;
		maverickData.setCalories(calories);
		Calories_burned_today.setText(calories + " cal");
	}

	private Boolean isAllfilled() {
		return !(TextUtils.isEmpty(workoutText.getText().toString()) && TextUtils
				.isEmpty(exceriseText.getText().toString()));
	}

	private void addFav(final String name) {
		if (isAllfilled()) {

			final ProgressDialog progressDialog = ProgressDialog.show(
					WorkoutTrackerActivity.this, "Saving...",
					"Wait a few sec your data is saving");

			new Thread() {
				public void run() {
					try {
						ContentValues values = new ContentValues();
						values.put(FavWorkoutTracterTable.Column.NAME, name);
						values.put(FavWorkoutTracterTable.Column.EXERCISE,
								maverickData.getExceriseType());
						values.put(FavWorkoutTracterTable.Column.WORKOUT,
								maverickData.getExceriseTimeWorking());
						values.put(FavWorkoutTracterTable.Column.CALORIES,
								maverickData.getCalories());

						getContentResolver()
								.insert(FavoriteProvider.WORKTRACKER_FAV_INSERT,
										values);
						handler.sendEmptyMessage(0);
					} catch (Exception e) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						Log.e("kumar:" + this.getClass(),
								"error in sve data into fav_workout table"
										+ e.getMessage(), e);
						WorkoutTrackerActivity.this.finish();
					}
					progressDialog.dismiss();
				}
			}.start();
		} else
			toast(getString(R.string.REQUIRE_FIELD_TOAST));

	};

	private void getFavName(final FavEnums option) {
		String positive = "OK", nagative = "CANCEL";
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		alert.setMessage("Favourite Name");
		alert.setView(input);

		alert.setPositiveButton(positive,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String value = input.getText().toString().trim();
						switch (option) {
						case ADD_FAV:
							if (!TextUtils.isEmpty(value))
								addFav(value);
							else
								toast("must type your favorite name");
							break;
						case LOAD_FAV:
							if (!TextUtils.isEmpty(value))
								loadFav(value);
							else
								toast("must type your favorite name");
						}

					}
				});
		alert.setNegativeButton(nagative,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		alert.show();
	}

	private void loadFav(final String name) {

		Uri getFav = Uri.withAppendedPath(
				FavoriteProvider.WORKTRACKER_FAV, name);
		Cursor loadFavWorkoutTracker = managedQuery(getFav, null,
				null, null, null);
		Log.i("manikk", "loadFavWorkoutTracker = "+loadFavWorkoutTracker.getCount());
		if (loadFavWorkoutTracker.moveToFirst()) {
			workoutText
					.setText(loadFavWorkoutTracker.getInt(loadFavWorkoutTracker
							.getColumnIndex(FavWorkoutTracterTable.Column.WORKOUT))+" ");
			exceriseText
					.setText(loadFavWorkoutTracker.getString(loadFavWorkoutTracker
							.getColumnIndex(FavWorkoutTracterTable.Column.EXERCISE)));
			Log.i("manikk", "fav name = "+loadFavWorkoutTracker.getString(loadFavWorkoutTracker
					.getColumnIndex(FavWorkoutTracterTable.Column.NAME)));
			setColories();
//		final ProgressDialog progressDialog = ProgressDialog.show(
//				WorkoutTrackerActivity.this, "Loading...",
//				"Wait a few sec your data is Loading");
//
//		new Thread() {
//			public void run() {
//				try {
//					
//					}
//				catch (Exception e) {
//					if (progressDialog != null) {
//						progressDialog.dismiss();
//					}
//					Log.i("kumar:" + this.getClass(),
//							"error in load data into fav_workout table"
//									+ e.getMessage(), e);
//
//				}
//				progressDialog.dismiss();
//			}
//		}.start();
	}
	}
}

