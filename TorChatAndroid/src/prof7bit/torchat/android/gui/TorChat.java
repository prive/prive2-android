package prof7bit.torchat.android.gui;

import info.guardianproject.onionkit.ui.OrbotHelper;

import prof7bit.torchat.android.R;
import prof7bit.torchat.android.service.Backend;
import prof7bit.torchat.android.service.PrintlnRedirect;
import ru.dtlbox.torchat.customviews.CheckableGroup;
import ru.dtlbox.torchat.customviews.CheckableImageButton;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;


/**
 * Main activity that is visible on start of application. It mainly shows the
 * roster (buddy list) and has an options menu where all global settings can be
 * set. It will start the service if it is not already running and it is also
 * the only place from where the service can be stopped.
 * 
 * @author Bernd Kreuss <prof7bit@gmail.com>
 */
public class TorChat extends SherlockFragmentActivity {
	
	final static String LOG_TAG = "TorChat";
	final static String HOST_NAME = "hs_host";
	final static int HS_PORT = 11009;
	
	public String onionAddress = "";
	
	CheckableGroup mCheckableGroup;
	
	FrameLayout flContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PrintlnRedirect.Install("TorChat");
		System.out.println("onCreate");
		setContentView(R.layout.activity_main);
		initializeLayout();
		doStartService();
		startTorService();
	}
	
	protected void initializeLayout(){
		
		getSupportActionBar().hide();
		((ImageView)findViewById(R.id.btn_add)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(TorChat.this, AddUserActivity.class));
			}
		});
		
//		getSupportActionBar().setCustomView(R.layout.bar);
//		getSupportActionBar().setIcon(R.drawable.arrow_left_blue);
		flContent = (FrameLayout)findViewById(R.id.fl_content);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.fl_content, new ContactListFragment());
		ft.commit();
		
		CheckableImageButton btnContact = (CheckableImageButton)findViewById(R.id.menu_1);
		CheckableImageButton btnChats = (CheckableImageButton)findViewById(R.id.menu_2);
		CheckableImageButton btnTimeline = (CheckableImageButton)findViewById(R.id.menu_3);
		CheckableImageButton btnProfile = (CheckableImageButton)findViewById(R.id.menu_4);
		
		mCheckableGroup = new CheckableGroup(btnContact, btnChats, btnTimeline, btnProfile);
		btnContact.setChecked(true);
		btnContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
					mCheckableGroup.changeChecked((CheckableImageButton)v);
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.fl_content, new ContactListFragment());
					ft.commit();
				
			}
		});
		
		btnChats.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
					mCheckableGroup.changeChecked((CheckableImageButton)v);
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.fl_content, new DrownFragment());
					ft.commit();
				}
			
		});
		
		btnTimeline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
					mCheckableGroup.changeChecked((CheckableImageButton)v);
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.fl_content, new DrownFragment());
					ft.commit();
				
			}
		});
		
		btnProfile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("profile","onClick");
				
					mCheckableGroup.changeChecked((CheckableImageButton)v);
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.fl_content, new ProfileFragment());
					ft.commit();
				
			}
		});



		
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		System.out.println("onSaveInstanceState");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getSupportMenuInflater();
//		inflater.inflate(R.menu.m_roster, menu);
		menu.add("add contact").setIcon(android.R.drawable.btn_plus).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				startActivity(new Intent(TorChat.this, AddUserActivity.class));
				return false;
			}
		}).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		menu.add("button").setIcon(android.R.drawable.btn_star).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(TorChat.this, Backend.class);
				intent.setAction(Backend.ACTION_OPEN_CONNECTION);
				intent.putExtra(Backend.EXTRA_STRING_ONION_ADDRESS, "av5qba24owb7ia2s");
				Log.i(LOG_TAG, "try to start connection");
				startService(intent);	
				return false;
			}
		}).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_quit:
			doQuit();
			return true;
		case R.id.menu_settings:
			doShowSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Start the Background service.
	 */
	private void doStartService() {
		System.out.println("doStartService");
		startService(new Intent(this, Backend.class));
	}

	/**
	 * Stop the Background service.
	 */
	private void doStopService() {
		System.out.println("doStopService");
		stopService(new Intent(this, Backend.class));
	}

	/**
	 * Quit entirely (stop service and close the main activity).
	 */
	private void doQuit() {
		System.out.println("doQuit");
		doStopService();
		finish();
	}

	/**
	 * Show the setting dialog
	 */
	private void doShowSettings() {
		System.out.println("doShowSettings");
		// nothing yet
	}
	
	protected void startTorService() {
		OrbotHelper oh = new OrbotHelper(this);
		oh.requestOrbotStart(this);
	}
	
	protected void openHS(int port) {
		OrbotHelper oh = new OrbotHelper(this);
		oh.requestHiddenServiceOnPort(this, port);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			String hs_host = data.getStringExtra(HOST_NAME);
			setMyTorDomain(hs_host != null ? hs_host : "not defined");
			Log.i("HOST_NAME", hs_host != null ? hs_host : "null");
			Intent intent  = new Intent(this, Backend.class);
			intent.putExtra(Backend.EXTRA_STRING_MY_ONION_ADDRESS, hs_host);
			startService(intent);
		}

		if (requestCode == 1) {
			openHS(HS_PORT);

			return;
		}

	}
	
	protected void setMyTorDomain(String hsHostName) {
	 	onionAddress = hsHostName;
	}
	
	@Override
	public void setTitle(CharSequence title) {
	
	 ((TextView)findViewById(R.id.tv_title)).setText(title);
	}
	
	public String getOnionAddress() {
		return onionAddress;
	}
	
}
