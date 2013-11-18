package prof7bit.torchat.android.gui;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;

import prof7bit.torchat.android.R;
import prof7bit.torchat.android.service.Backend;
import prof7bit.torchat.android.service.Backend.ChatListener;
import ru.dtlbox.torchat.customviews.AvatarView;
import ru.dtlbox.torchat.entities.ChatMessage;
import ru.dtlbox.torchat.tests.ChatTestActivityDesingTest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class TestChatActivity extends SherlockActivity implements ChatListener, OnClickListener{
	//TODO
	final static String LOG_TAG = "TestChatActivity";
	final static String USER_STRING = "user";
	final static String MESSAGE_STRING = "message";
	boolean mIsBound = false;
	//TextView tvChat;
	Button btnSend;
	EditText etSend;
	ListView lvChat;
	List<ChatMessage> listMessages = new ArrayList<ChatMessage>();
	ChatAdapter chatAdapter;
	
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
		getSupportActionBar().hide();
//		tvChat = (TextView)findViewById(R.id.tv_chat);
		lvChat = (ListView)findViewById(R.id.lv_messages);
		etSend = (EditText)findViewById(R.id.et_send);
		btnSend = (Button)findViewById(R.id.btn_send);
		
		btnSend.setOnClickListener(this);
		chatAdapter = new ChatAdapter(TestChatActivity.this, listMessages);
		//chatAdapter = new ChatAdapter(TestChatActivity.this, ChatTestActivityDesingTest.generateMessages());
		lvChat.setAdapter(chatAdapter);
		
		if(getIntent().getExtras()!=null) {
			Bundle bundle = getIntent().getExtras();
			if(bundle.containsKey(USER_STRING)&&bundle.containsKey(MESSAGE_STRING)) {
				user = bundle.getString(USER_STRING);
				setTitle(user != null ? user : "not_detected");
				//tvChat.append(bundle.getString(MESSAGE_STRING));
			}
				
		}
		
		
		
	}
	
	@Override
	protected void onPause() {
		if(mBackend != null)
			mBackend.removeListener(TestChatActivity.this);
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
		if(message!=null)
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
			listMessages.add(new ChatMessage("0","").setText(message));
		else
			listMessages.add(new ChatMessage("","").setText(message));
		//TODO 
		((ChatAdapter)(lvChat.getAdapter())).notifyDataSetChanged();
		
	
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
	
	public class ChatAdapter extends BaseAdapter {
		
		Context context;
		List<ChatMessage> messages;
		
		public ChatAdapter(Context context, List<ChatMessage> messages) {
			this.messages = messages;
			this.context = context;
		}

		@Override
		public int getCount() {
			return messages.size();
		}

		@Override
		public Object getItem(int position) {
			return messages.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			ChatMessage msg = messages.get(position);
			LayoutInflater inflater = LayoutInflater.from(context);
			
			if(view == null) {
				if(getItemViewType(position)==0)
					view = inflater.inflate(R.layout.message_in, null);
				else
					view = inflater.inflate(R.layout.message_out, null);
			}
			((TextView)view.findViewById(R.id.tv_message)).setText(msg.getText());
			((AvatarView)view.findViewById(R.id.iv_avatar)).setFrameImage(BitmapFactory.decodeResource(getResources(), R.drawable.face_red));
			
			return view;
		}
		
		@Override
		public int getItemViewType(int position) {
			//TODO
		    return messages.get(position).getOwner().equals("") ? 1 : 0;
		}

		@Override
		public int getViewTypeCount() {
		    return 2;
		}
		
		
		
	}

}
