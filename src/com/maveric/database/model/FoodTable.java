package com.maveric.database.model;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FoodTable {
	public static final String TABLE = "food";

	public static class Column {
		// public static final String _ID = "_id";
		public static final String NAME = "name";
		public static final String PROTIN = "protin";
		public static final String CARBOS = "carbos";
		public static final String FAT = "fat";
		public static final String UNIT = "unit";
		public static final String CALORIES = "calories";
		public static final String UPDATED = "updated";
		public static final String CREATED = "created";

	}

	public static String[] getColumns() {
		String[] columns = { Column.NAME, Column.PROTIN, Column.CARBOS,
				Column.FAT, Column.CALORIES, Column.UPDATED, Column.CREATED };
		return columns;
	}

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + Column.NAME
			+ " TEXT," + Column.PROTIN + " INTEGER," + Column.CARBOS
			+ " INTEGER," + Column.FAT + " INTEGER, " + Column.CREATED
			+ " INTEGER," + Column.UPDATED + " INTEGER," + Column.CALORIES
			+ " TEXT ); ";

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