package prof7bit.torchat.core;

/**
 * Event handler for events that the TorChat client will produce.
 * The application must implement this and pass it to the Client
 * instance to be notified about events. 
 *
 * @author Bernd Kreuss <prof7bit@gmail.com>
 *
 */
public interface ClientHandler {
	
	public void onStartHandshake(String onionAddress, String randomString);
	
	public void onHandshakeComplete();
	
	public void onHandshakeAbort(String reason);
	
}
