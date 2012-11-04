package com.maveric.database.model;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ExceriseValue {

	public static final String TABLE = "workoutdb";

	public static class Column {
		public static final String _ID = "_id";
		public static final String EXCERISE_TYPE = "excerisetype";
		public static final String EXCERISE_NAME = "excerisename";
		public static final String INTENSITY_SELECT = "intensityselect";
		public static final String KG50 = "kg50";
		public static final String KG60 = "kg60";
		public static final String KG70 = "kg70";
		public static final String KG80 = "kg80";
		public static final String IMAGEPATH = "imagepath";
		public static final String FAVOURITE_STATUS = "favourite";
		public static final String UPDATED = "updated";
		public static final String TYPE = "type";

		/*
		 * default value is 0 .it is represent no one to favourite 1-->favourite
		 * excerise for user
		 */
		public static final String CREATED = "created";
	}

	public static String[] getColumns() {
		String[] columns = { Column._ID, Column.EXCERISE_TYPE,
				Column.EXCERISE_NAME, Column.UPDATED, Column.INTENSITY_SELECT,
				Column.KG50, Column.KG60, Column.KG70, Column.KG80,
				Column.IMAGEPATH, Column.CREATED, Column.FAVOURITE_STATUS,
				Column.TYPE };
		return columns;
	}

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Column.EXCERISE_TYPE + " TEXT," + Column.EXCERISE_NAME + "TEXT,"
			+ Column.INTENSITY_SELECT + "TEXT," + Column.KG50 + "TEXT,"
			+ Column.KG60 + "TEXT," + Column.KG70 + "TEXT," + Column.KG80
			+ "TEXT," + Column.IMAGEPATH + "TEXT," + Column.UPDATED
			+ " INTEGER," + Column.TYPE + "TEXT," + Column.FAVOURITE_STATUS
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