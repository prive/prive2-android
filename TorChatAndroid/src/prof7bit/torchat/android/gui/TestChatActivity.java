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
		
		TextView tvChat = new TextView(TestChatActivity.this);
		tvChat.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		svScroll.addView(tvChat);
		
		setContentView(svScroll);
		
		if(getIntent().getExtras()!=null) {
			Bundle bundle = getIntent().getExtras();
			if(bundle.containsKey(USER_STRING)&&bundle.containsKey(MESSAGE_STRING)) {
				setTitle(bundle.getString(USER_STRING));
				tvChat.append(bundle.getString(MESSAGE_STRING));
			}
				
		}
		
		
		
	}
	
	
	@Override
	protected void onResume() {
	
		super.onResume();
		
		
	}
	
	
	public static void openTestChatActivityWithMessage(Context context, String user, String message) {
		Intent i = new Intent(context, TestChatActivity.class);
		i.putExtra(USER_STRING, user);
		i.putExtra(MESSAGE_STRING, message);
		context.startActivity(i);
		
	}

	@Override
	public void onMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	
	void doBindService() {
	    // Establish a connection with the service.  We use an explicit
	    // class name because we want a specific service implementation that
	    // we know will be running in our own process (and thus won't be
	    // supporting component replacement by other applications).
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
