package prof7bit.torchat.core;

public interface ConnectionHandler {
	
	/**
	 * protocol events
	 */
	public void onPingReceived(Msg_ping msg);
	public void onPongReceived(Msg_pong msg);
	public void onMessageReceived(Msg_message msg);
	public void onStatusReceived(Msg_status msg);
	
	/**
	 * other
	 */
	public void onConnect(Connection connection);
	public void onDisconnect(Connection connection, String reason);
	
}
