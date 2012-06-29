package com.maveric.database.model;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WorkOutTrackerTable {
	public static final String TABLE = "workout";

	public static class Column {
		public static final String ID = "_id";
		public static final String DATE = "date";
		public static final String SELECT_EXCERISE = "excerise";
		public static final String WORKOUT = "workout";
	}

	public static String[] getColumns() {
		String[] columns = { Column.ID, Column.DATE, Column.SELECT_EXCERISE,
				Column.WORKOUT };
		return columns;
	}

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE
			+ " ( " + Column.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Column.DATE + " TEXT, " + Column.SELECT_EXCERISE + " TEXT,"
			+ Column.WORKOUT + " TEXT ); ";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.i("kumar", "Upgrading database from version " + oldVersion + " to "
				+ newVersion
				+ ", which will destroy all old data in the table " + TABLE);
		// TODO Version check has to done here to avoid old version database
		// crack while upgrading new version database
	}
}