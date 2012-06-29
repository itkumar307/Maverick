package com.maveric;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.maveric.contentprovider.WorkoutProvider;
import com.maveric.database.model.WorkOutTrackerTable;
import com.maveric.obj.type.MaverickDataOrganize;

public class WorkoutTrackerViewerActivity extends MavericListBaseActiity {

	TextView previous;
	TextView next;
	MaverickDataOrganize maverickData;
	Context ctx;
	Button close;
	Cursor workoutInfo;
	int width;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.workoutviewcontainer);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ctx = getApplicationContext();

		maverickData = new MaverickDataOrganize(ctx);

		previous = (TextView) findViewById(R.id.prev_date);
		next = (TextView) findViewById(R.id.next_date);

		try {
			workoutInfo = managedQuery(WorkoutProvider.WORKOUT_URI, null, null,
					null, null);

			workoutInfo.moveToFirst();
			
			ListAdapter adapter = new WorkoutAdapter(this, workoutInfo,
					new String[] { WorkOutTrackerTable.Column.DATE },
					new int[] { R.id.listexcerisetype });
			ListView listView = getListView();
			setListAdapter(adapter);
		} catch (Exception e1) {
			Log.e("kumar" + this.getClass(), "cursor error" + e1.getMessage(),
					e1);
		}

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		previous.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

	}
}
