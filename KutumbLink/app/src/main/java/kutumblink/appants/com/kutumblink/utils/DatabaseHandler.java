package kutumblink.appants.com.kutumblink.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {


	// Database Version
	public static final int DATABASE_VERSION = 1;

	// Database Name
	public static final String DATABASE_NAME = "Contacts";

	// questionsData table name
	public static final String TABLE_PHONE_CONTACTS = "TBL_PHONE_CONTACTS";
	public static final String TABLE_GROUP = "TBL_GROUP";
	public static final String TABLE_MESSAGES = "TBL_MSG";
	public static final String TABLE_PHOTOS = "TBL_PHO";
	public static final String TABLE_EVENTS = "TBL_EVENTS";

	public static final String GROUP_ID = "G_GROUPID";
	public static final String GROUP_NAME = "G_NAME";
	public static final String GROUP_PIC = "G_GROUP_PIC";
	public static final String GROUP_TOTALCONTACTS = "G_TOTALCONTACTS";
	public static final String GROUP_SORT_ORDER = "g_sort_order";

	/**
	 * Message related fields.
	 */
	public static final String MSG_ID = "msg_id";
	public static final String MSG_TITLE = "msg_title";
	public static final String MSG_LINK = "msg_link";
	public static final String CREATED_ON = "created_on";

	public static final String EVT_ID = "evt_id";
	public static final String EVT_TITLE = "evt_title";
	public static final String EVT_DESC = "evt_desc";
	public static final String EVT_CREATED_ON = "evt_created_on";
	public static final String EVT_EMAIL= "evt_email";
	public static final String EVT_CONTACTS = "evt_contacts";
	public static  String EVT_TIME_MILLY= "evt_time_milly";
	public static  String EVENT_SORT_ORDER= "evt_sort_order";



	/**
	 * Photos table fields
	 *
	 */
	public static final String PHOTO_ID = "pho_id";
	public static final String PHOTO_TITLE = "pho_title";
	public static final String PHOTO_LINK = "pho_link";

	public static final String PHONE_ID = "Phone_ID";
	public static final String PHONE_CONTACT_ID = "Phone_Contact_ID";
	public static final String PHONE_CONTACT_NAME = "Phone_Contact_Name";
	public static final String PHONE_CONTACT_NUMBER = "Phone_Contact_Number";
	public static final String PHONE_CONTACT_EMAIL = "Phone_Contact_email";
	public static final String PHONE_CONTACT_PIC = "Phone_Contact_Pic";
	public static final String PHONE_CONTACT_GID = "Phone_Contact_Gid";
	public static final String PHONE_GROUP_ID = "Phone_Group_ID";

	public static final String PHONE_CONTACT_FNAME = "Phone_firstName";
	public static final String PHONE_CONTACT_LNAME = "Phone_lastName";




	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	String CREATE_MESSGE_TABLE = "CREATE TABLE "
			+ TABLE_MESSAGES
			+ " ("
			+ MSG_ID 		+ " INTEGER PRIMARY KEY AUTOINCREMENT,"

			+ MSG_TITLE	+ " TEXT,"
			+ MSG_LINK 	+ " TEXT,"
			+ CREATED_ON 	+ " DATETIME DEFAULT CURRENT_TIMESTAMP"
			+ ")";



	String CREATE_EVENTS_TABLE = "CREATE TABLE "
			+ TABLE_EVENTS
			+ " ("
			+ EVT_ID 		+ " INTEGER PRIMARY KEY AUTOINCREMENT,"

			+ EVT_TITLE	+ " TEXT,"
			+ EVT_DESC 	+ " TEXT,"
			+ EVT_CONTACTS 	+ " TEXT,"
			+ EVT_EMAIL + " TEXT,"
			+ EVT_TIME_MILLY + " LONG,"
			+ EVT_CREATED_ON 	+ " TEXT ,"
			+EVENT_SORT_ORDER +" TEXT DEFAULT '"+Constants.DEFAULT+"'"

			+ ")";

	String CREATE_PHOTOS_TABLE = "CREATE TABLE "
			+ TABLE_PHOTOS
			+ " ("
			+ PHOTO_ID 		+ " INTEGER PRIMARY KEY AUTOINCREMENT,"

			+ PHOTO_TITLE	+ " TEXT,"
			+ PHOTO_LINK 	+ " TEXT,"
			+ CREATED_ON 	+ "DATETIME DEFAULT CURRENT_TIMESTAMP"
			+ ")";


	String CREATE_GROUP = "CREATE TABLE "
			+ TABLE_GROUP
			+ " ("
			+ GROUP_ID 		+ " INTEGER PRIMARY KEY,"

			+ GROUP_NAME	+ " TEXT not null unique,"
			+ GROUP_PIC 	+ " BLOB,"
			+ GROUP_TOTALCONTACTS 	+ " TEXT ,"
			+GROUP_SORT_ORDER +" TEXT DEFAULT '"+Constants.DEFAULT+"'"

			+ ")";

	String CREATE_CONTACTS = "CREATE TABLE "
			+ TABLE_PHONE_CONTACTS
			+ " ("
			+ PHONE_ID + " INTEGER PRIMARY KEY,"
			+ PHONE_CONTACT_ID 		+ " TEXT,"
			+ PHONE_CONTACT_NAME	+ " TEXT,"
			+ PHONE_CONTACT_FNAME	+ " TEXT,"
			+ PHONE_CONTACT_LNAME	+ " TEXT,"
			+ PHONE_CONTACT_NUMBER 	+ " TEXT,"
			+ PHONE_CONTACT_EMAIL 	+ " TEXT,"
			+ PHONE_CONTACT_PIC 	+ " BLOB,"
			+ PHONE_CONTACT_GID 	+ " TEXT"
			+ ")";
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {






		db.execSQL(CREATE_GROUP);

		db.execSQL(CREATE_CONTACTS);
		db.execSQL(CREATE_MESSGE_TABLE);
		db.execSQL(CREATE_PHOTOS_TABLE);
		db.execSQL(CREATE_EVENTS_TABLE);

	}

	

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);

		// Create tables again
		onCreate(db);
	}

	public long UpdateTable(String TableName, ContentValues cvUpdate, String Where_Condition)
	{
		/*SQLiteDatabase db = this.getWritableDatabase();
			 db.update(TableName, cvUpdate, Where_Condition, null);
			db.close();*/


		long result=-1;

		try {
			SQLiteDatabase db = this.getWritableDatabase();
			if (Where_Condition.equals("")) {
				result=	db.update(TableName,cvUpdate, null, null);
			} else {
				result=db.update(TableName,cvUpdate, Where_Condition, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.i("TEST","Update "+result);
		return result;
	}


	public long insert(String tableName, ContentValues values) {
		SQLiteDatabase db = this.getWritableDatabase();

		
		long result=db.insert(tableName, null, values);
		db.close(); 
	 	return result;
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
				int result=db.delete(tablename, whereClause, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
