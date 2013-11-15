package prof7bit.torchat.core;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class BuddyManager {
	final static String LOG_TAG = "ConnectionManager";

	protected List<Buddy> mBuddies = new ArrayList<Buddy>();

	protected List<Connection> mConnections = new ArrayList<Connection>();

	public BuddyManager() {
		super();
	}

	protected void addNewConnection(Connection connection) {
		mConnections.add(connection);
	}
	
	protected void addNewBuddy(Buddy buddy){
		mBuddies.add(buddy);
	}

	/**
	 * return buddy for passed onion address
	 * @param onionAddress
	 * @return
	 */
	public Buddy getBuddyByOnionAddress(String onionAddress) {
		if (onionAddress != null) {
			for (Buddy buddy : mBuddies)
				if (buddy.isOnionAddressLike(onionAddress))
					return buddy;
		}
		return null;
	}

	protected Connection getConnectionByOnionAddress(String onionAddress,
			Connection.Type type) {
		if (onionAddress == null) {
			Log.e(LOG_TAG, "onion address is null");
			return null;
		}
		for (Connection connection : mConnections) {
			if (connection.recipientOnionAddress != null) {
				if (connection.recipientOnionAddress.equals(onionAddress)
						&& type == connection.type) {
					Log.i(LOG_TAG + "/getConnectionByOnionAddress",
							"Connection was found");
					return connection;
				}
			} else
				Log.w(LOG_TAG, "recipientOnionAddress is null");
		}
		return null;
	}

}