package prof7bit.torchat.core;

import java.io.IOException;

import prof7bit.reactor.Reactor;
import android.util.Log;

/**
 * this class represent one chat handler for handshake handling, and received
 * and sending messages
 * 
 * @author busylee demonlee999@gmail.com
 * 
 */
public class Buddy implements ConnectionHandler {
	final static String LOG_TAG = "Buddy";

	Connection mIncomingConnection = null;
	Connection mOutcomingConnection = null;

	String mOnionAddressRecepient = null;

	Client mClient = null;

	public Buddy(Client client) {
		mClient = client;
	}

	@Override
	public void onPingReceived(Msg_ping msg) {
		// store onion address if null
		if (mOnionAddressRecepient == null)
			mOnionAddressRecepient = msg.getOnionAddress();

		if (mOutcomingConnection == null) {
			try {
				Connection c;

				c = new Connection(new Reactor(), msg.getOnionAddress()
						+ Client.ONION_DOMAIN, Client.TORCHAT_DEFAULT_PORT,
						mClient);

				c.recipientOnionAddress = msg.getOnionAddress();

				// send ping
				Msg_ping msgPing = new Msg_ping(c);
				msgPing.setOnionAddress(mClient.mMyOnionAddress);
				msgPing.setRandomString(mClient.mMyRandomString);
				c.sendMessage(msgPing);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		logInfo("onPingReceived");
	}

	@Override
	public void onPongReceived(Msg_pong msg) {
		logInfo("onPongReceived");
	}

	@Override
	public void onMessageReceived(Msg_message msg) {
		logInfo("onMessageReceived");
	}

	@Override
	public void onStatusReceived(Msg_status msg) {
		logInfo("onStatusReceived");

	}

	@Override
	public void onConnect(Connection connection) {
		logInfo(connection.getStringConnectionType() + " onConnect");
	}

	@Override
	public void onDisconnect(Connection connection, String reason) {
		logInfo(connection.getStringConnectionType() + " onDisconnect: "
				+ reason);

	}

	protected void logInfo(String text) {
		Log.i(LOG_TAG + mOnionAddressRecepient != null ? mOnionAddressRecepient
				: "undefinedOnionAddress", text);
	}

	/**
	 * store incoming connection
	 * 
	 * @param incomingConnection
	 */
	public void addIncomingConnection(Connection incomingConnection) {
		/*
		 * TODO need to close connection because we lost link to this object
		 * now, socket must be closed, check f connection not null
		 */
		mIncomingConnection = incomingConnection;
		mIncomingConnection.setConnectionHandler(this);
	}

	/**
	 * store outcoming connection
	 * 
	 * @param outcomingConnection
	 */
	public void addOutcomingConnection(Connection outcomingConnection) {
		/*
		 * TODO need to close connection because we lost link to this object
		 * now, socket must be closed, check f connection not null
		 */
		mOutcomingConnection = outcomingConnection;
		mOutcomingConnection.setConnectionHandler(this);
	}

	/**
	 * check is this buddy for given onion address
	 * 
	 * @param onionAddress
	 * @return
	 */
	public boolean isOnionAddressLike(String onionAddress) {

		if (mOnionAddressRecepient == null) {
			Log.w(LOG_TAG, "onion address of recipient is null");
			return false;
		}

		if (onionAddress == null) {
			Log.w(LOG_TAG, "getted onion address is null");
			return false;
		}
		return mOnionAddressRecepient.equals(onionAddress);
	}

	/**
	 * check there is outcoming connection for this buddy TODO may be need to
	 * change this logic, connection may be closed already, or refused
	 * 
	 * @return
	 */
	public boolean hasOutComingConnection() {
		return mOutcomingConnection != null;
	}

	/**
	 * check there is incoming connection for this buddy TODO may be need to
	 * change this logic, connection may be closed already, or refused
	 * 
	 * @return
	 */
	public boolean hasInComingConnection() {
		return mIncomingConnection != null;
	}

	/**
	 * check is buddy ready for chat. I assume buddy is ready for chat if: it
	 * has 2 connections incoming and outcoming it has onion address of
	 * recipient handshake is complete
	 * 
	 * @return
	 */
	public boolean isReadyForChat() {
		return hasInComingConnection() && hasOutComingConnection()
				&& mOnionAddressRecepient != null; // TODO need to check
													// handshake state
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
