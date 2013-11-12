package prof7bit.torchat.android.gui;

import info.guardianproject.onionkit.ui.OrbotHelper;

import java.io.IOException;

import prof7bit.torchat.android.R;
import prof7bit.torchat.android.service.Backend;
import prof7bit.torchat.android.service.PrintlnRedirect;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;


/**
 * Main activity that is visible on start of application. It mainly shows the
 * roster (buddy list) and has an options menu where all global settings can be
 * set. It will start the service if it is not already running and it is also
 * the only place from where the service can be stopped.
 * 
 * @author Bernd Kreuss <prof7bit@gmail.com>
 */
public class TorChat extends SherlockActivity {
	final static String LOG_TAG = "TorChat";
	final static String HOST_NAME = "hs_host";
	final static int HS_PORT = 11009;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PrintlnRedirect.Install("TorChat");
		System.out.println("onCreate");
		setContentView(R.layout.l_roster);
		initializeLayout();
		startTorService();
	}
	
	protected void initializeLayout(){
		Button btnStartConnection = (Button) findViewById(R.id.btnStartConnection);
		btnStartConnection.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TorChat.this, Backend.class);
				intent.setAction(Backend.ACTION_OPEN_CONNECTION);
				intent.putExtra(Backend.EXTRA_STRING_ONION_ADDRESS, "av5qba24owb7ia2s");
				Log.i(LOG_TAG, "try to start connection");
				startService(intent);			
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
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.m_roster, menu);
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
			doStartService();

		}

		if (requestCode == 1) {
			openHS(HS_PORT);

			return;
		}

	}
	
	protected void setMyTorDomain(String hsHostName) {
		setTitle(hsHostName);
	}
	
}
