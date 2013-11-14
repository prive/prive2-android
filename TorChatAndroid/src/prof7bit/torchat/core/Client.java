package prof7bit.torchat.core;

import java.io.IOException;

import prof7bit.reactor.ListenPort;
import prof7bit.reactor.ListenPortHandler;
import prof7bit.reactor.Reactor;
import prof7bit.reactor.TCP;
import prof7bit.reactor.TCPHandler;
import android.util.Log;

public class Client extends ConnectionManager implements ListenPortHandler {
	final static String LOG_TAG = "Client";
	final static String ONION_DOMAIN = ".onion";
	final static int TORCHAT_DEFAULT_PORT = 11009;

	private ClientHandler clientHandler;
	private Reactor reactor;
	private ListenPort listenPort;

	private String mMyOnionAddress = null;
	private String mMyRandomString = "213543857986565313";

	public Client(ClientHandler clientHandler, int port) throws IOException {
		this.clientHandler = clientHandler;
		this.reactor = new Reactor();
		this.listenPort = new ListenPort(reactor, this);
		this.listenPort.listen(port);
	}

	public void close() throws InterruptedException {
		this.reactor.close();
	}

	@Override
	public TCPHandler onAccept(TCP tcp) {
		Log.i(LOG_TAG, "new connection was accepted");
		/* new connection has not onion address,
		 * it appear to be visible after ping received
		 * in connection class it will cause function for 
		 * set certain ConnectionHandler for this connection
		 */
		Connection c = new Connection(tcp, this);
		addNewConnection(c);
		return c;
	}

	public void setMyOnionAddress(String onionAddress) {
		mMyOnionAddress = onionAddress.split(".onion")[0];
		Log.i(LOG_TAG + "setMyOnionAddress", "my onion address is "
				+ mMyOnionAddress);

	}

	/**
	 * Function for request to open new connection on onion address
	 * TODO Just spike yet, need to change in next time 
	 * @param onionAddress
	 * @throws IOException
	 */
	public void startConnection(String onionAddress) throws IOException {

		Log.i(LOG_TAG, "start connection request");

		// check there is buddy for this onion address
		Buddy buddy = getBuddyByOnionAddress(onionAddress);

		if (buddy == null)
			buddy = new Buddy();

		// I think it is spike
		if (buddy.isReadyForChat()) {
			clientHandler.onStartChat(buddy.getOnionAddressRecepient());
			return;
		}

		//if buddy has not outcoming connection
		if (!buddy.hasOutComingConnection()) {

			Connection c;

			c = new Connection(new Reactor(), onionAddress + ONION_DOMAIN,
					TORCHAT_DEFAULT_PORT, this);

			c.recipientOnionAddress = onionAddress;

			addNewConnection(c);// TODO delete, i think no important now to store link of this object
		}

	}

	// // TODO change logic
	// @Override
	// public void onPingReceived(Msg_ping msg) {
	// Connection connection = msg.getConnection();
	// Log.i(LOG_TAG + "/onPingReceived",
	// (connection.type == Connection.Type.INCOMING ? "incoming"
	// : "outcoming")
	// + " ping "
	// + msg.getOnionAddress()
	// + " "
	// + msg.getRandomString());
	//
	// clientHandler.onStartHandshake(msg.getOnionAddress(),
	// msg.getRandomString());
	//
	// if (connection.type == Connection.Type.INCOMING) {
	// /*
	// * if it is incoming connection handshake is starting since this
	// * moment send ping pong
	// */
	//
	// // set handshake state to start
	// if (connection.handshakeState != Connection.HandshakeState.SUCCESS)
	// connection.handshakeState = Connection.HandshakeState.START;
	//
	// // send message "ping"
	// Msg_ping msgPing = new Msg_ping(connection);
	// msgPing.setOnionAddress(mMyOnionAddress);
	// msgPing.setRandomString(mMyRandomString);
	// connection.sendMessage(msgPing);
	//
	// // send message 'pong"
	// Msg_pong msgPong = new Msg_pong(connection);
	// msgPong.setRandomString(msg.getRandomString());
	// connection.sendMessage(msgPong);
	//
	// // send message "status"
	// Msg_status msgStatus = new Msg_status(connection);
	// msgStatus.setAvailiable();
	// connection.sendMessage(msgStatus);
	//
	// // send message "version" for appearing online
	// Msg_version msgVersion = new Msg_version(connection);
	// connection.sendMessage(msgVersion);
	//
	// } else if (connection.type == Connection.Type.OUTCOMING) {
	// /*
	// * if it is outcoming connection handshake not change need to send
	// * pong, status, version
	// */
	//
	// // send "pong"
	// Msg_pong msgPong = new Msg_pong(connection);
	// msgPong.setRandomString(msg.getRandomString());
	// connection.sendMessage(msgPong);
	//
	// // send message "status"
	// Msg_status msgStatus = new Msg_status(connection);
	// msgStatus.setAvailiable();
	// connection.sendMessage(msgStatus);
	//
	// // send message "version" for appearing online
	// Msg_version msgVersion = new Msg_version(connection);
	// connection.sendMessage(msgVersion);
	// } else
	// Log.w(LOG_TAG, "undefined connection type");
	//
	// }
	//
	// @Override
	// public void onPongReceived(Msg_pong msg) {
	// Connection connection = msg.getConnection();
	// Log.i(LOG_TAG,
	// (connection.type == Connection.Type.INCOMING ? "incoming"
	// : "outcoming") + " pong " + msg.getRandomString());
	// // check is random string is my random string
	// if (msg.getRandomString().equals(mMyRandomString)) {
	// /*
	// * if it is my string handshake is complete need to notify client
	// * handler if it is outgoing connection need to send pong
	// */
	//
	// clientHandler.onHandshakeComplete(connection.recipientOnionAddress);
	//
	// // set handshake state to success
	// connection.handshakeState = Connection.HandshakeState.SUCCESS;
	//
	// } else {
	// Log.e(LOG_TAG, "string is not my string");
	// clientHandler.onHandshakeAbort("string is not my string");
	// }
	// }
	//
	// @Override
	// public void onMessageReceived(Msg_message msg) {
	// Log.i(LOG_TAG + "onMessageReceived", "message was received");
	// String onionAddress = msg.getConnection().recipientOnionAddress;
	// if (onionAddress != null)
	// clientHandler.onMessage(onionAddress, msg.getMessage());
	// else
	// Log.w(LOG_TAG + "onMessageReceived",
	// "onionAddress of recepient is null");
	//
	// }
	//
	// @Override
	// public void onStatusReceived(Msg_status msg) {
	// Log.i(LOG_TAG + "onStatusReceived", "status-message was received");
	// String onionAddress = msg.getConnection().recipientOnionAddress;
	// if (onionAddress != null)
	// clientHandler.onMessage(onionAddress, "<-s-" + msg.getStatus());
	// else
	// Log.w(LOG_TAG + "onMessageReceived",
	// "onionAddress of recepient is null");
	//
	// }
	//
	// @Override
	// public void onDisconnect(String reason) {
	// // TODO implement
	// }
	//
	// /**
	// * This function will be called then connection will be established
	// *
	// */
	// @Override
	// public void onConnect(Connection connection) {
	// Log.i(LOG_TAG + "/onConnect", "onConnect");
	// // startHandshake(connection);
	// }

	/**
	 * Function for start handshake process into establishing connection
	 * 
	 * @param connection
	 */
	protected void startHandshake(Connection connection) {
		// set handshake state to start
		connection.handshakeState = Connection.HandshakeState.START;

		// send ping for notify recipient of starting handshake
		Msg_ping msgPing = new Msg_ping(connection);
		msgPing.setOnionAddress(mMyOnionAddress);
		msgPing.setRandomString(mMyRandomString);
		connection.sendMessage(msgPing);

	}

	/**
	 * Function sends message for certain connection given by onion address
	 * 
	 * @param onionAddress
	 * @param textMessage
	 */
	public void sendMessage(String onionAddress, String textMessage) {
		Connection connection = getConnectionByOnionAddress(onionAddress,
				Connection.Type.OUTCOMING);
		if (connection != null) {
			Msg_message message = new Msg_message(connection);
			message.setMessage(textMessage);
			connection.sendMessage(message);
			Log.i(LOG_TAG + "sendMessage", "message was sended");
		} else {
			Log.w(LOG_TAG + "sendMessage",
					"no connection found for this onion address");
		}
	}
}
