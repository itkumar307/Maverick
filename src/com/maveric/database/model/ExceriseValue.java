package com.maveric.database.model;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ExceriseValue {

	public static final String TABLE = "workoutdb";

	public static class Column {
		public static final String _ID = "_id";
		public static final String EXCERISE_TYPE = "exceriseType";
		public static final String UPDATED = "updated";
		public static final String FAVOURITE_STATUS = "favourite";
		/*
		 * default value is 0 .it is represent no one to favourite 1-->favourite
		 * excerise for user
		 */
		public static final String CREATED = "created";
	}

	public static String[] getColumns() {
		String[] columns = { Column._ID, Column.EXCERISE_TYPE, Column.UPDATED,
				Column.CREATED, Column.FAVOURITE_STATUS };
		return columns;
	}

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Column.EXCERISE_TYPE + " TEXT," + Column.UPDATED + " INTEGER,"
			+ Column.CREATED + " INTEGER ," + Column.FAVOURITE_STATUS
			+ " TEXT DEFAULT \'0\' ); ";

	public static void onCreate(SQLiteDatabase database) {
		// database.execSQL(DATABASE_CREATE);
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