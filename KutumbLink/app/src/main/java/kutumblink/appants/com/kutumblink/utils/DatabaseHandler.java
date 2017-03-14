package kutumblink.appants.com.kutumblink.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {


	// Database Version
	public static final int DATABASE_VERSION = 1;

	// Database Name
	public static final String DATABASE_NAME = "Contacts";

	// questionsData table name
	public static final String TABLE_PHONE_CONTACTS = "TBL_PHONE_CONTACTS";
	public static final String TABLE_GROUP = "TBL_GROUP";

	public static final String GROUP_ID = "G_GROUPID";
	public static final String GROUP_NAME = "G_NAME";
	public static final String GROUP_PIC = "G_GROUPID";
	public static final String GROUP_TOTALCONTACTS = "G_TOTALCONTACTS";

	public static final String PHONE_CONTACT_ID = "Phone_Contact_ID";
	public static final String PHONE_CONTACT_NAME = "Phone_Contact_Name";
	public static final String PHONE_CONTACT_NUMBER = "Phone_Contact_Number";
	public static final String PHONE_CONTACT_EMAIL = "Phone_Contact_email";
	public static final String PHONE_CONTACT_PIC = "Phone_Contact_Pic";
	public static final String PHONE_GROUP_ID = "Phone_Group_ID";



	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {



		String CREATE_GROUP = "CREATE TABLE "
				+ TABLE_GROUP
				+ " ("
				+ GROUP_ID 		+ " INTEGER PRIMARY KEY,"

				+ GROUP_NAME	+ " TEXT,"
				+ GROUP_PIC 	+ " BLOB,"
				+ GROUP_TOTALCONTACTS 	+ " TEXT"

				+ ")";

		String CREATE_CONTACTS = "CREATE TABLE "
				+ TABLE_PHONE_CONTACTS
				+ " ("
				+ PHONE_CONTACT_ID 		+ " INTEGER PRIMARY KEY,"

				+ PHONE_CONTACT_NAME	+ " TEXT,"
				+ PHONE_CONTACT_NUMBER 	+ " TEXT,"
				+ PHONE_CONTACT_EMAIL 	+ " TEXT,"
				+ PHONE_CONTACT_PIC 	+ " BLOB,"

				+ " FOREIGN KEY ("+PHONE_GROUP_ID+") REFERENCES "+TABLE_GROUP+" ("+GROUP_ID+") "

				+ ")";



		db.execSQL(CREATE_GROUP);


	}

	

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);

		// Create tables again
		onCreate(db);
	}

	public void UpdateTable(String TableName, ContentValues cvUpdate, String Where_Condition)
	{
		/*SQLiteDatabase db = this.getWritableDatabase();
			 db.update(TableName, cvUpdate, Where_Condition, null);
			db.close();*/



		try {
			SQLiteDatabase db = this.getWritableDatabase();
			if (Where_Condition.equals("")) {
				db.update(TableName,cvUpdate, null, null);
			} else {
				db.update(TableName,cvUpdate, Where_Condition, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void insert(String tableName, ContentValues values) {
		SQLiteDatabase db = this.getWritableDatabase();

		
		db.insert(tableName, null, values);
		db.close(); 
	//	return seq;
	}

	
	
	public Cursor retriveData(String query)
	{
		try
		{
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor curRetriveData = db.rawQuery(query,null);
			if(curRetriveData!=null)
				curRetriveData.moveToFirst();
			return curRetriveData;
		}
		catch (Exception e)
		{  
			e.printStackTrace();
			return null;
		}
	}


	public void DeleteTable(String tablename, String whereClause) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			if (whereClause.equals("")) {
				db.delete(tablename, null, null);
			} else {
				db.delete(tablename, whereClause, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
