package prof7bit.torchat.core;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class ConnectionManager {
	final static String LOG_TAG = "ConnectionManager";

	protected List<Connection> mConnections = new ArrayList<Connection>();

	public ConnectionManager() {
		super();
	}
	
	protected void addNewConnection(Connection connection){
		mConnections.add(connection);
	}
	
	protected Connection getConnectionByOnionAddress(String onionAddress, Connection.Type type){
		if(onionAddress == null){
			Log.e(LOG_TAG, "onion address is null");
			return null;
		}
		for(Connection connection : mConnections){
			if(connection.recipientOnionAddress != null){
				if(connection.recipientOnionAddress.equals(onionAddress) && type == connection.type){
					Log.i(LOG_TAG + "/getConnectionByOnionAddress", "Connection was found");
					return connection;
				}
			} else
				Log.w(LOG_TAG, "recipientOnionAddress is null");
		}
		return null;
	}
	

}