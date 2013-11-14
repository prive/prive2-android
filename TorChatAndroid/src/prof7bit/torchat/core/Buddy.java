package prof7bit.torchat.core;

import android.util.Log;

public class Buddy implements ConnectionHandler {
	final static String LOG_TAG = "Buddy";
	
	Connection mIncomingConnection = null;
	Connection mOutcomingConnection = null;
	
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
	
	public void addOutcomingConnection(Connection outcomingConnection){
		/* TODO need to close connection
		 * because we lost link to this object now,
		 * socket must be closed,
		 * check f connection not null 
		 */
		mOutcomingConnection = outcomingConnection;
		mOutcomingConnection.setConnectionHandler(this);
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
	
	/**
	 * check there is outcoming connection for this buddy
	 * TODO may be need to change this logic, connection may be closed already,
	 * or refused
	 * @return
	 */
	public boolean hasOutComingConnection(){
		return mOutcomingConnection != null;
	}
	
	/**
	 * check there is incoming connection for this buddy
	 * TODO may be need to change this logic, connection may be closed already,
	 * or refused
	 * @return
	 */
	public boolean hasInComingConnection(){
		return mIncomingConnection != null;
	}
	
	/**
	 * check is buddy ready for chat.
	 * I assume buddy is ready for chat if: 
	 * it has 2 connections incoming and outcoming
	 * it has onion address of recipient
	 * handshake is complete
	 * @return
	 */
	public boolean isReadyForChat(){
		//TODO need to complete
		return true;
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
