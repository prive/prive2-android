package prof7bit.torchat.android.gui;

import prof7bit.torchat.android.service.Backend;
import prof7bit.torchat.android.service.Backend.MessageListener;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.sax.StartElementListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class TestChatActivity extends Activity implements MessageListener{
	
	final static String USER_STRING = "user";
	final static String MESSAGE_STRING = "message";
	boolean mIsBound = false;
	TextView tvChat;
	
	ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
			mBackend.removeListener(TestChatActivity.this);
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			
			mBackend = ((Backend.LocalBinder)service).getService();
			
		}
	};
	
	Backend mBackend = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ScrollView svScroll = new ScrollView(TestChatActivity.this);
		svScroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		tvChat = new TextView(TestChatActivity.this);
		tvChat.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		svScroll.addView(tvChat);
		
		setContentView(svScroll);
		
		if(getIntent().getExtras()!=null) {
			Bundle bundle = getIntent().getExtras();
			if(bundle.containsKey(USER_STRING)&&bundle.containsKey(MESSAGE_STRING)) {
				String user = bundle.getString(USER_STRING);
				setTitle(user != null ? user : "not_detected");
				tvChat.append(bundle.getString(MESSAGE_STRING));
			}
				
		}
		
		
		
	}
	
	@Override
	protected void onPause() {
		doUnbindService();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		doBindService();
		super.onResume();
		
		
	}
	
	
	public static void openTestChatActivityWithMessage(Context context, String user, String message) {
		Intent i = new Intent(context, TestChatActivity.class);
		i.putExtra(USER_STRING, user);
		i.putExtra(MESSAGE_STRING, message);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		
	}

	@Override
	public void onMessage(String message) {
		
		tvChat.append(message);
	}
	
	void doBindService() {
	   
		bindService(new Intent(this, Backend.class), mConnection, Context.BIND_AUTO_CREATE);
	    mIsBound = true;
	}

	void doUnbindService() {
	    if (mIsBound) {
	       
	        unbindService(mConnection);
	        mIsBound = false;
	    }
	}
	

}
