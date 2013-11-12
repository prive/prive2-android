package prof7bit.torchat.android.gui;

import prof7bit.torchat.android.R;
import prof7bit.torchat.android.service.Backend;
import prof7bit.torchat.android.service.Backend.MessageListener;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestChatActivity extends Activity implements MessageListener, OnClickListener{
	
	final static String LOG_TAG = "TestChatActivity";
	final static String USER_STRING = "user";
	final static String MESSAGE_STRING = "message";
	boolean mIsBound = false;
	TextView tvChat;
	Button btnSend;
	EditText etSend;
	
	String user = null;
	
	ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
			mBackend.removeListener(TestChatActivity.this);
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			
			mBackend = ((Backend.LocalBinder)service).getService();
			mBackend.addListener(TestChatActivity.this);
		}
	};
	
	Backend mBackend = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		tvChat = (TextView)findViewById(R.id.tv_chat);
		etSend = (EditText)findViewById(R.id.et_send);
		btnSend = (Button)findViewById(R.id.btn_send);
		
		btnSend.setOnClickListener(this);
		
		
		
		if(getIntent().getExtras()!=null) {
			Bundle bundle = getIntent().getExtras();
			if(bundle.containsKey(USER_STRING)&&bundle.containsKey(MESSAGE_STRING)) {
				user = bundle.getString(USER_STRING);
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
	public void onMessage(final String message) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				addMessageToChat(message, false);
			}
		});
		
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
	
	void addMessageToChat(String message, boolean isMy){
		if(isMy)
			message = "-->" + message;
		else
			message = "<--" + message;
		
		tvChat.append("\r\n" + message);	
	}

	@Override
	public void onClick(View v) {
		String text = "Hellow world";
		if (etSend.getText().toString().equals(""))
			Log.w(LOG_TAG + "/onClick", "text is empty. hellow string will be sended");
		else
			text = etSend.getText().toString();
		
		addMessageToChat(text, true);
		
		//clear Edit text
		etSend.getText().clear();
		
		if(user != null)
			if(mBackend != null)
				mBackend.sendMessage(user, text);
			else{
				Log.w(LOG_TAG + "onClick", "mBackend is null");
				addMessageToChat("error", true);
			}
		else{
			Log.w(LOG_TAG + "onClick", "user is null");
			addMessageToChat("error", true);
		}
	}
	

}
