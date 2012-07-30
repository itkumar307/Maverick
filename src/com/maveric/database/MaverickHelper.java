package com.maveric.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.maveric.database.model.DietTracker;
import com.maveric.database.model.ExceriseValue;
import com.maveric.database.model.FavWorkoutTracterTable;
import com.maveric.database.model.FoodTrackerTable;
import com.maveric.database.model.ProfileTable;
import com.maveric.database.model.WaterTracker;
import com.maveric.database.model.WorkOutTrackerTable;

public class MaverickHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "ConsumerDB";
	public static final int DATABASE_VERSION = 1;
	public Context ctx;
	public MaverickHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		ctx = context;
		if (!isModelDBExists()) {
			try {
				copyDataBase();
			} catch (IOException e) {
				Log.e("tag",
						"Error copying model database", e);
				throw new RuntimeException("Error copying model database",
						e);
			}
			Log.e("tag",
					"Initial model database is created");
		}
	}
	private void copyDataBase() throws IOException {
		InputStream myInput = ctx.getAssets().open(DATABASE_NAME);
		File db = ctx.getDatabasePath(DATABASE_NAME);
		if (!db.exists()) {
			String dbDir = db.getAbsolutePath().substring(0,
					db.getAbsolutePath().lastIndexOf("/"));
			File dbDirectory = new File(dbDir);
			dbDirectory.mkdir();
		}
		OutputStream myOutput = new FileOutputStream(db.getAbsolutePath());

		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	private boolean isModelDBExists() {

		SQLiteDatabase checkDB = null;
		boolean exist = false;
		try {
			File db = ctx.getDatabasePath(DATABASE_NAME);
			String dbPath = db.getAbsolutePath();
			if (db == null || !db.exists()) {
				exist = false;
			} else {
				checkDB = SQLiteDatabase.openDatabase(dbPath, null,
						SQLiteDatabase.OPEN_READONLY
								| SQLiteDatabase.NO_LOCALIZED_COLLATORS);
			}
		} catch (Exception e) {
			Log.e("tag", "database " + DATABASE_NAME
					+ " does't exist", e);
		}

		if (checkDB != null) {
			exist = true;
			if (checkDB.isOpen())
				checkDB.close();
		}
		return exist;
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		try {
			ProfileTable.onCreate(database);
			WorkOutTrackerTable.onCreate(database);
			WaterTracker.onCreate(database);
			DietTracker.onCreate(database);
			ExceriseValue.onCreate(database);
			FoodTrackerTable.onCreate(database);
			//FoodTable.onCreate(database);
			FavWorkoutTracterTable.onCreate(database);
		} catch (Exception e) {
			Log.e("mohan", "error in create db");
		}
	}

	// Method is called during an upgrade of the database,
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		// WorkOutTrackerTable.onUpgrade(database, oldVersion, newVersion);
		// ConsumerInterestsTable.onUpgrade(database, oldVersion, newVersion);
	}
}
