package prof7bit.torchat.core;

import android.util.Log;

public class Buddy implements ConnectionHandler {
	final static String LOG_TAG = "Buddy";
	
	Connection incomingConnection = null;
	Connection outcomingConnection = null;
	
	String mOnionAddressRecepient = null;

	public Buddy(){
		
	}

	@Override
	public void onPingReceived(Msg_ping msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPongReceived(Msg_pong msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessageReceived(Msg_message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusReceived(Msg_status msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnect(Connection connection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnect(String reason) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * check is this buddy for given onion address
	 * @param onionAddress
	 * @return
	 */
	public boolean isOnionAddressLike(String onionAddress){
		
		if(mOnionAddressRecepient == null){
			Log.w(LOG_TAG, "onion address of recipient is null");
			return false;
		}
		
		if (onionAddress == null){
			Log.w(LOG_TAG, "getted onion address is null");
			return false;
		}
		return mOnionAddressRecepient.equals(onionAddress);
	}
	
	/*********************
	 * GETTERS AND SETTERS
	 ********************/
	public String getOnionAddressRecepient() {
		return mOnionAddressRecepient;
	}

	public void setOnionAddressRecepient(String onionAddressRecepient) {
		this.mOnionAddressRecepient = onionAddressRecepient;
	}
}
