package prof7bit.torchat.android.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import prof7bit.torchat.android.R;
import prof7bit.torchat.android.gui.TestChatActivity;
import prof7bit.torchat.android.gui.TorChat;
import prof7bit.torchat.core.Client;
import prof7bit.torchat.core.ClientHandler;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Backend extends Service implements ClientHandler {
	public final static String LOG_TAG = "Backend";

	public final static String ACTION_OPEN_CONNECTION = "prof7bit.torchat.android.service.Backend.OPEN_CONNECTION";
	public final static String EXTRA_STRING_ONION_ADDRESS = "prof7bit.torchat.android.service.Backend.EXTRA_ONION_ADDRESS";
	public final static String EXTRA_STRING_MY_ONION_ADDRESS = "prof7bit.torchat.android.service.Backend.EXTRA_MY_ONION_ADDRESS";
	
	private NotificationManager nMgr;
	private int NOTIFICATION = 10429; // Any unique number for this notification

	private Client client;
	
	private final IBinder mBinder = new LocalBinder();
	
	private List<MessageListener> listListeners = new ArrayList<Backend.MessageListener>();
	
	@SuppressWarnings("deprecation")
	private void showNotification() {
		CharSequence title = getText(R.string.service_running);
		CharSequence title2 = getText(R.string.service_running_detail);

		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(
				R.drawable.ic_stat_service_running, title,
				System.currentTimeMillis());
		notification.flags = Notification.FLAG_ONGOING_EVENT;

		// The PendingIntent to launch our activity if the user selects this
		// notification
		Intent toLaunch = new Intent(this, TorChat.class);
		toLaunch.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		toLaunch.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				toLaunch, 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this, title, title2, contentIntent);

		// Send the notification.
		nMgr.notify(NOTIFICATION, notification);
	}

	private void removeNotification() {
		nMgr.cancel(NOTIFICATION);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d("onBind","DONE");
		return mBinder;
	}

	@Override
	public void onCreate() {
		Log.i(LOG_TAG, "service created");
		nMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		PrintlnRedirect.Install("TorChat");

		try {
			client = new Client(this, 11009);
			showNotification();
		} catch (IOException e) {
			// TODO what to do now?
			e.printStackTrace();
		}
	}

	public void removeListener(MessageListener listener) {
		
		listListeners.remove(listener);
		
	}
	
	public void addListener(MessageListener listener) {
		
		listListeners.add(listener);
		
	}
	
	@Override	
	public void onDestroy() {	
		try {
			client.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		removeNotification();
		Toast.makeText(this, R.string.toast_service_stopped, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onStart(final Intent intent, int startid) {
		if(intent == null)
			return;
		//request for open new connection
		if(intent.getAction() == ACTION_OPEN_CONNECTION){
			if(intent.getStringExtra(EXTRA_STRING_ONION_ADDRESS) != null)
				(new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							String onionAddress = intent.getStringExtra(EXTRA_STRING_ONION_ADDRESS);
							if(onionAddress != null)
								client.startConnection(onionAddress);
							else
								Log.w(LOG_TAG, "onion address is null");
						} catch (IOException e) {
							e.printStackTrace();
						}		
					}
				})).start();
				
			else
				Log.w(LOG_TAG, "extra 'onion address' is null");
		}
		
		/*if intent includes onion address, add it to client
		 * TODO it is spike yet
		 */
		if (intent.getStringExtra(EXTRA_STRING_MY_ONION_ADDRESS) != null)
			if (client != null)
				client.setMyOnionAddress(intent.getStringExtra(EXTRA_STRING_MY_ONION_ADDRESS));
	}

	/**
	 * This function will be called then incoming connection establish.
	 * TODO may be it is place for making decision about trusting of this user.
	 * 
	 * @param onionAddress onion address like "gnr34ry53gmymwr"
	 * @param randomString random string received from chatter
	 * 
	 */
	@Override
	public void onStartHandshake(String onionAddress, String randomString) {
		Log.i(LOG_TAG, "handshake starting...");
		
	}

	/**
	 * This function will be called then chatters complete their handshake
	 */
	@Override
	public void onHandshakeComplete(String user) {
		Log.i(LOG_TAG, "handshake complete!");
		TestChatActivity.openTestChatActivityWithMessage(Backend.this, user, "Handshake complete");
	}

	/**
	 * This function will be called if handshake was aborted
	 */
	@Override
	public void onHandshakeAbort(String reason) {
		Log.i(LOG_TAG, "handshake abort. reason: " + reason != null ? reason : "undefined");
		
	}

	@Override
	public void onMessage(String user, String message) {
		if(listListeners.size()==0) {
			TestChatActivity.openTestChatActivityWithMessage(Backend.this, user, message);
		}
		else {
			//TODO for test
			for(MessageListener listener : listListeners)
				listener.onMessage(message);
				
		}
		
	}
	
	public interface MessageListener{
		public void onMessage(String message);
	}
	
	public class LocalBinder extends Binder {
        public Backend getService() {
            return Backend.this;
        }
    }
}
