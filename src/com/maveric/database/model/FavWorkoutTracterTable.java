package com.maveric.database.model;

import android.database.sqlite.SQLiteDatabase;

public class FavWorkoutTracterTable {
	public static String TABLE = "Fav_workout_tracker";

	public static class Column {
		public static final String _ID = "_id";
		public static final String NAME = "name";
		public static final String EXERCISE = "exercise";
		public static final String WORKOUT = "workout";
		public static final String CALORIES = "colories";
	}

	public static String[] getColumns() {
		String[] columns = { Column._ID, Column.NAME, Column.EXERCISE,
				Column.WORKOUT, Column.CALORIES };
		return columns;
	}

	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + Column.NAME
			+ " TEXT UNIQUE," + Column.EXERCISE + " TEXT,"
			+ Column.WORKOUT + " INTEGER," + Column.CALORIES + " TEXT ); ";
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {

		// TODO Version check has to done here to avoid old version database
		// crack while upgrading new version database
	}
}
