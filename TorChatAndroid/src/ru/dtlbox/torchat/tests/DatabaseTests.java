package ru.dtlbox.torchat.tests;

import ru.dtlbox.torchat.dbworking.DBManager;
import ru.dtlbox.torchat.entities.Contact;
import android.content.Context;
import android.util.Log;

public class DatabaseTests {
	
	
	final String LOG_TAG = "DATA_BASE_TEST";
	
	public DatabaseTests() {
	
	}
	
	
	public void testingInsertGetAndRemove(Context context) {
		
		Log.d(LOG_TAG, "start testing");
		Contact contact1 = new Contact("contact 1", "nickname 1");
		Contact contact2 = new Contact("contact 2", "nickname 2");
		Contact contact3 = new Contact("contact 3", "nickname 3");
		
		Log.d(LOG_TAG, "contacts creating: DONE");
		
		DBManager mDBManager = new DBManager().init(context);
		
		Log.d(LOG_TAG, "database manager creating: DONE");
		
		mDBManager.insertContact(contact1);
		mDBManager.insertContact(contact2);
		mDBManager.insertContact(contact3);
		
		Log.d(LOG_TAG, "contacts inserting: DONE");
		
		Log.d(LOG_TAG, "trying to get all contacts...");
		
		if(mDBManager.getAllContact().size()==3)
			Log.d(LOG_TAG, "contacts list size = 3, CORRECT");
		else
			Log.d(LOG_TAG, "something wrong");
		
		Log.d(LOG_TAG, "trying to get one contact (number 1)...");
		
		Contact contact = mDBManager.getContact("contact 1");
		
		Log.d(LOG_TAG, "contact is " + contact.getOnionAddress() + " " +contact.getNickName());
		
		Log.d(LOG_TAG, "trying to adding add it...");
		
		mDBManager.insertContact(contact);
		if(mDBManager.getAllContact().size()==3)
			Log.d(LOG_TAG, "contact list size 3, CORRECT");
		else
			Log.d(LOG_TAG, "something wrong");
		
		Log.d(LOG_TAG, "trying to delete contect (number 2)...");
		
		mDBManager.deleteContact("contact 2");
		
		if(mDBManager.getAllContact().size()==2)
			Log.d(LOG_TAG, "contact list size 2, CORRECT");
		else
			Log.d(LOG_TAG, "something wrong");
		
		for(Contact contactI : mDBManager.getAllContact()) {
			Log.d(LOG_TAG, "in DB : "  + contactI.getOnionAddress() + " " +contactI.getNickName());
		}
		
	}
	
	

}
