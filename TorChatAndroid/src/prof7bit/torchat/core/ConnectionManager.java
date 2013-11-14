package prof7bit.torchat.core;

import android.util.Log;

public class ConnectionManager extends BuddyManager {
	final static String LOG_TAG = "ConnectionManager";
	
	/**
	 * Set handler for this connection
	 * Find the buddy for this recipient by onionAddress.
	 * If Buddy did not find, need to create new buddy
	 * @param connection
	 */
	public void setConnectionHandlerFor(Connection connection) {
		//check if onion address for this connection was not define
		if (connection.recipientOnionAddress == null){
			Log.w(LOG_TAG, "recepient onion address is null");
			return;
		}
		
		for (Buddy buddy : mBuddies){
			
		}
		
		
	}
}
