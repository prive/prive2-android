package prof7bit.torchat.core;

public interface ConnectionHandler {
	
	/**
	 * protocol events
	 */
	public void onPingReceived(Msg_ping msg);
	public void onPongReceived(Msg_pong msg);
	public void onMessageReceived(Msg_message msg);
	
	/**
	 * other
	 */
	public void onDisconnect(String reason);
	
}
