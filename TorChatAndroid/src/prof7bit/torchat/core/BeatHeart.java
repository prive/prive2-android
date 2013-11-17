package prof7bit.torchat.core;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import prof7bit.torchat.core.Buddy.HandshakeStatus;
import android.util.Log;

/**
 * this class manages buddies connections
 * It is necessary only for check buddy state and start reconnect if 
 * because any reasons buddy disconnected 
 * @author busylee demonlee999@gmail.com
 *
 */
public class BeatHeart extends BuddyManager {
	final static String LOG_TAG = "BeatHeart";
	final static int BEAT_HEART_INTERVAL = 3;//interval of beatheart in minutes
	
	ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	/**
	 * Function must be begin after all buddies begin connection at least one times at start client
	 * start beat heart to check once at 3 minutes, connection of all available buddies
	 * every buddy must have own status. 
	 * it can be in handshake process, or handshake was rejected
	 * 
	 */
	protected void startBeatHeart(){
		service.scheduleWithFixedDelay(new Runnable() {
			
			@Override
			public void run() {
				Log.i(LOG_TAG, "BEAT HEART START");
				for (Buddy buddy : mBuddies)
					if (buddy.mHandshakeStatus == HandshakeStatus.ABORTED || 
							buddy.mHandshakeStatus == HandshakeStatus.NOT_BEGIN )
						try {
							buddy.reconnect();
						} catch (IOException e) {
							e.printStackTrace();
						}
			}
		}, 0, BEAT_HEART_INTERVAL, TimeUnit.MINUTES);
		
	}
}
