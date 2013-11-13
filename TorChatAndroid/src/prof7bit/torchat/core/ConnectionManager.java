package prof7bit.torchat.core;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import prof7bit.reactor.ListenPortHandler;

public class ConnectionManager {
	final static String LOG_TAG = "ConnectionManager";

	protected List<Connection> mConnections = new ArrayList<Connection>();

	public ConnectionManager() {
		super();
	}
	
	protected void addNewConnection(Connection connection){
		mConnections.add(connection);
	}
	
	protected Connection getConnectionByOnionAddress(String onionAddress){
		if(onionAddress == null){
			Log.e(LOG_TAG, "onion address is null");
			return null;
		}
		for(Connection connection : mConnections){
			if(connection.recipietnOnionAddress != null){
				if(connection.recipietnOnionAddress.equals(onionAddress)){
					Log.i(LOG_TAG + "/getConnectionByOnionAddress", "Connection was found");
					return connection;
				}
			} else
				Log.w(LOG_TAG, "recipientOnionAddress is null");
		}
		return null;
	}
	

}