package com.maveric.database.model;

import android.database.sqlite.SQLiteDatabase;

public class ExceriseValue {

	public static final String TABLE = "excerise";

	public static class Column {
		public static final String _ID = "_id";
		public static final String EXCERISE_TYPE = "exceriseType";
		public static final String CALORIES = "calories";
		public static final String UPDATED = "updated";
		public static final String CREATED = "created";
	}

	public static String[] getColumns() {
		String[] columns = { Column._ID, Column.EXCERISE_TYPE, Column.CALORIES,
				Column.UPDATED, Column.CREATED };
		return columns;
	}

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Column.EXCERISE_TYPE + " TEXT," + Column.CALORIES + " INTEGER,"
			+ Column.UPDATED + " INTEGER," + Column.CREATED + " INTEGER ); ";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {

		// TODO Version check has to done here to avoid old version database
		// crack while upgrading new version database
	}
}