package prof7bit.torchat.core;

import java.io.IOException;
import java.util.List;

import prof7bit.reactor.ListenPort;
import prof7bit.reactor.ListenPortHandler;
import prof7bit.reactor.Reactor;
import prof7bit.reactor.TCP;
import prof7bit.reactor.TCPHandler;
import ru.dtlbox.torchat.dbworking.DBManager;
import ru.dtlbox.torchat.entities.Contact;
import android.content.Context;
import android.util.Log;

/**
 * This class manages all buddies
 * 
 * @author busylee demonlee999@gmail.com
 */
public class Client extends BeatHeart implements ListenPortHandler {
	final static String LOG_TAG = "Client";
	
	public String mMyOnionAddress = null;
	public String mMyRandomString = "213543857986565313";
	
	final static String ONION_DOMAIN = ".onion";
	final static int TORCHAT_DEFAULT_PORT = 11009;
	
	private DBManager mDbManager = null;
	private ClientHandler clientHandler;
	public Reactor reactor;
	private ListenPort listenPort;

	public Client(Context context, ClientHandler clientHandler, int port) throws IOException {
		this.clientHandler = clientHandler;
		this.reactor = new Reactor();
		this.listenPort = new ListenPort(reactor, this);
		this.listenPort.listen(port);
		
		//set up DB manager
		mDbManager = new DBManager();
		mDbManager.init(context);
		
		//restore all contacts into buddy list
		restoreAllContactsBuddies();
		
	}

	public void close() throws InterruptedException {
		this.reactor.close();
	}

	@Override
	public TCPHandler onAccept(TCP tcp) {
		Log.i(LOG_TAG, "new connection was accepted");
		/*
		 * new connection has not onion address, it appear to be visible after
		 * ping received in connection class it will cause function for set
		 * certain ConnectionHandler for this connection
		 */
		Connection c = new Connection(tcp, this);
		addNewConnection(c);
		return c;
	}
	
	/**
	 * Function for restoring all buddies from contact rows
	 */
	public void restoreAllContactsBuddies(){
		List<Contact> contacts = mDbManager.getAllContact();
		for (Contact contact : contacts){
			boolean alreadyInList = false;
			for (Buddy buddy : mBuddies)
				if (buddy.getOnionAddressRecepient().equals(contact.getOnionAddress()) )
					alreadyInList = true;
			
			if (!alreadyInList){
				Buddy buddy = new Buddy(this);
				buddy.setOnionAddressRecepient(contact.getOnionAddress());
				addNewBuddy(buddy);
			}
	
		}
		
	}

	public void setMyOnionAddress(String onionAddress) {
		mMyOnionAddress = onionAddress.split(".onion")[0];
		Log.i(LOG_TAG + "setMyOnionAddress", "my onion address is "
				+ mMyOnionAddress);
		
		//now we have onion address we can start beat heart
		startBeatHeart();
	}

	/**
	 * Set handler for this connection
	 * Find the buddy for this recipient by onionAddress.
	 * If Buddy did not find, need to create new buddy
	 * @param connection
	 */
	public void setConnectionHandlerForIncomingConnection(Connection connection) {
		//check if onion address for this connection was not define
		if (connection.recipientOnionAddress == null){
			Log.w(LOG_TAG, "recepient onion address is null");
			return;
		}
		
		//find buddy with this onion address
		for (Buddy buddy : mBuddies){
			if (buddy.isOnionAddressLike(connection.recipientOnionAddress)){
				buddy.addIncomingConnection(connection);
				Log.i(LOG_TAG, "buddy for this onion address was found");
				return;
			}
		}
		
		Log.w(LOG_TAG, "buddy for this onion address was not found, create new buddy");
		Buddy buddy = new Buddy(this);
		buddy.isNew = true;
		buddy.addIncomingConnection(connection);
		addNewBuddy(buddy);
	}
	
	protected void onNewBuddy(Buddy buddy){
		//just spike yet!
		Contact contact = new Contact(buddy.getOnionAddressRecepient(), buddy.getOnionAddressRecepient());
		mDbManager.insertContact(contact);
		clientHandler.onNewBuddy(buddy.getOnionAddressRecepient());
	}
	
	protected void onMessage(String user, String message){
		clientHandler.onMessage(user, message);
	}
	
	protected void onStatusChange(String user, Buddy.Status status){
		clientHandler.onStatusChange(user, status);
	}
	
	/**
	 * Add new buddy and start connection
	 * @param onionAddress
	 */
	public void addBuddy(String onionAddress){
		Buddy buddy = getBuddyByOnionAddress(onionAddress);
		
		if (buddy != null){
			Log.w(LOG_TAG, "buddy already exists");
			return;
		}
		
		buddy = new Buddy(this);
		try {
			buddy.startConnection(onionAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addNewBuddy(buddy);
	}
	
	/**
	 * Function for get status for buddy
	 * @param onionAddress
	 * @return
	 */
	public Buddy.Status getBuddyStatus(String onionAddress){
		Buddy buddy = getBuddyByOnionAddress(onionAddress);
		if (buddy != null){
			return buddy.mBuddyStatus;
		}
		return Buddy.Status.OFFLINE;
	}
	
	/**
	 * handle complete handshake in one of all buddies
	 * @param user
	 */
	public void onChatEstablished(String user){
		clientHandler.onStartChat(user);
	}
	
	/**
	 * Function for request to open new connection on onion address
	 * TODO Just spike yet, need to change in next time 
	 * @param onionAddress
	 * @throws IOException
	 */
	public void startConnection(String onionAddress) throws IOException {
		//my onion address is important if null, we must return action
		if (mMyOnionAddress == null){
			Log.w(LOG_TAG, "myOnionAddress is null, start connection was rejected");
		}
		Log.i(LOG_TAG, "start connection request");

		// check there is buddy for this onion address
		Buddy buddy = getBuddyByOnionAddress(onionAddress);

		if (buddy == null)
			buddy = new Buddy(this);

		// I think it is spike
		if (buddy.isReadyForChat()) {
			clientHandler.onStartChat(buddy.getOnionAddressRecepient());
			return;
		}
		
		
		if (buddy.mHandshakeStatus == Buddy.HandshakeStatus.NOT_BEGIN
				|| buddy.mHandshakeStatus == Buddy.HandshakeStatus.ABORTED) {
			
			buddy.startConnection(onionAddress);

			//store this buddy
			addNewBuddy(buddy);
			
		}

	}

	/**
	 * Function for start handshake process into establishing connection
	 * 
	 * @param connection
	 */
	protected void startHandshake(Connection connection) {

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
		Buddy buddy = getBuddyByOnionAddress(onionAddress);
		if (buddy != null) {
			buddy.sendMessage(textMessage);
			Log.i(LOG_TAG + "sendMessage", "message was sended");
		} else {
			Log.w(LOG_TAG + "sendMessage",
					"no buddy found for this onion address");
		}
	}
}
