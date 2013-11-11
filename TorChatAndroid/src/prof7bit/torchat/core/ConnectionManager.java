package prof7bit.torchat.core;

import java.util.ArrayList;
import java.util.List;

import prof7bit.reactor.ListenPortHandler;

public class ConnectionManager {

	protected List<Connection> mConnections = new ArrayList<Connection>();

	public ConnectionManager() {
		super();
	}
	
	protected void addNewConnection(Connection connection){
		mConnections.add(connection);
	}
	
	protected Connection getConnectionByOnionAddress(String onionAddress){
		for(Connection connection : mConnections)
			if(connection.recepietnOnionAddress.equals(onionAddress))
				return connection;
		return null;
	}
	

}