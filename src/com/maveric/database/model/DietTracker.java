package com.maveric.database.model;

import android.database.sqlite.SQLiteDatabase;

public class DietTracker {

	public static final String TABLE = "Image";

	public static class Column {
		public static final String ID = "_id";
		public static final String DATE = "date";
		public static final String BREAKFAST = "breakfast";
		public static final String LUNCH = "luch";
		public static final String DINNER = "dinner";
		public static final String SNACK = "snack";
	}

	public static String[] getColumns() {
		String[] columns = { Column.ID, Column.DATE, Column.BREAKFAST,
				Column.LUNCH, Column.DINNER, Column.SNACK, };
		return columns;
	}

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + Column.ID
			+ " TEXT UNIQUE, " + Column.DATE + " TEXT," + Column.BREAKFAST
			+ " TEXT," + Column.LUNCH + " TEXT," + Column.DINNER + " TEXT, "
			+ Column.SNACK + " TEXT ); ";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {

		// TODO Version check has to done here to avoid old version database
		// crack while upgrading new version database
	}
}