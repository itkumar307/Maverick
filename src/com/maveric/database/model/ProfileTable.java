package com.maveric.database.model;

import android.database.sqlite.SQLiteDatabase;

public class ProfileTable {
	public static String TABLE = "Profile";

	public static class Column {

		public static String CURRENT_HEIGHT = "currentHeight";
		public static String CURRENT_WEIGHT = "currentWeight";
		public static String CURRENT_HIP = "hip";
		public static String CURRENT_WAIST = "waist";

		public static String TARGET_HEIGHT = "recomBmi";
		public static String TARGET_WEIGHT = "targetWeight";
		public static String TARGET_HIP = "actualWH";
		public static String CURRENT_BMI = "actualBmi";

		public static String USER_NAME = "recomWater";
		public static String EMAIL_ID = "Email";
		public static String PASSWORD = "password";

	}

	public static String[] getColumns() {
		String[] columns = { Column.CURRENT_HEIGHT, Column.CURRENT_WEIGHT,
				Column.CURRENT_HIP, Column.CURRENT_WAIST, Column.TARGET_HEIGHT,
				Column.TARGET_WEIGHT, Column.TARGET_HIP, Column.CURRENT_BMI,
				Column.USER_NAME, Column.EMAIL_ID, Column.PASSWORD };
		return columns;
	}
	
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT," + Column.EMAIL_ID
			+ " TEXT UNIQUE, " + Column.PASSWORD + " TEXT, "
			+ Column.CURRENT_HEIGHT + " REAL, " + Column.CURRENT_WEIGHT
			+ " REAL, " + Column.CURRENT_HIP + " REAL, "
			+ Column.CURRENT_WAIST + " REAL, " + Column.TARGET_HEIGHT
			+ " REAL, " + Column.TARGET_WEIGHT + " REAL, "
			+ Column.TARGET_HIP + " REAL, " + Column.CURRENT_BMI + " REAL, "
			+ Column.USER_NAME + " TEXT ); ";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {

	}
}
