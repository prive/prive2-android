package ru.dtlbox.torchat.dbworking;

import java.util.ArrayList;
import java.util.List;

import ru.dtlbox.torchat.entities.Contact;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBManager {
	
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "APP_DATABASE";
	
	public static final String TABLE_CONTACTS_NAME = "contacts_table";
	public static final String FIELD_ONION_ADDRESS = "field_onion_adress";
	public static final String FIELD_NICKNAME = "field_nickname";
	
	public static final String[] ALL_CONTACT_FIELDS = new String[] {FIELD_ONION_ADDRESS, FIELD_NICKNAME};
	public static final int FIELD_ONION_ADDRESS_INDEX = 0;
	public static final int FIELD_NICKNAME_INDEX = 1;
	
	SQLiteDatabase database;
	DBHelper mDBHelper;
	Context context;
	
	public DBManager() {	}
	
	public DBManager init(Context context) {
		this.context = context;
		mDBHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
		return this;
	}
	
	public void insertContact(Contact contact) {
		getDatabase();
		ContentValues cv = new ContentValues();
		cv.put(FIELD_ONION_ADDRESS, contact.getOnionAddress());
		cv.put(FIELD_NICKNAME, contact.getNickName());
		try {
			database.insert(TABLE_CONTACTS_NAME, null, cv);
		} catch (Exception e) {
			// TODO: handle exception
		}
		closeDatabase();
	}
	
	public Contact getContact(String onionAddress) {
		getDatabase();
		try {
			Cursor c = database.query(TABLE_CONTACTS_NAME, ALL_CONTACT_FIELDS, 
					FIELD_ONION_ADDRESS + " = '" + onionAddress + "'",  null, null, null, null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				Contact result = new Contact(c.getString(FIELD_ONION_ADDRESS_INDEX),c.getString(FIELD_NICKNAME_INDEX));
				c.close();
				closeDatabase();
				return result;
			}
			c.close();
			
		} catch (Exception e) {
		
		}
		closeDatabase();
		return null;
		
	}
	
	public List<Contact> getAllContact() {
		getDatabase();
		List<Contact> result = new ArrayList<Contact>();
		try {
			Cursor c = database.query(TABLE_CONTACTS_NAME, ALL_CONTACT_FIELDS, 
					null,  null, null, null, null);
			if (c.getCount() > 0) {
				
				while(c.moveToNext())
					result.add(new Contact(c.getString(FIELD_NICKNAME_INDEX),c.getString(FIELD_ONION_ADDRESS_INDEX)));
				c.close();
				closeDatabase();
				return result;
			}
			c.close();
			
		} catch (Exception e) {
			Log.e("error: ", e.getMessage());
		}
		closeDatabase();
		return result;
		
	}
	
	
	public void deleteContact(String onionAddress) {
		getDatabase();
		try {
			database.delete(TABLE_CONTACTS_NAME, FIELD_ONION_ADDRESS + " = '" + onionAddress + "'", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		closeDatabase();
	}
	
	

	public SQLiteDatabase getDatabase() {
		database = mDBHelper.getWritableDatabase();
		return database;
	}

	public boolean closeDatabase() {
		if(database==null)
			return false;
		else
			database.close();
		return true;
	}
	
	public void close() {
		mDBHelper.close();
	}
	
	public class DBHelper extends SQLiteOpenHelper {
		
		public DBHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table " + TABLE_CONTACTS_NAME + 
					" (" + FIELD_ONION_ADDRESS + " text, " + 
						   FIELD_NICKNAME + " text, unique(" + FIELD_ONION_ADDRESS + ") on conflict replace);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS_NAME);
			onCreate(db);
		}

	}
	

}
