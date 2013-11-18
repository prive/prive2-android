package prof7bit.torchat.android.gui;

import com.actionbarsherlock.app.SherlockActivity;

import prof7bit.torchat.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


public class LoadScreenActivity extends SherlockActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		getSupportActionBar().hide();
		
		ImageView flLoadScreen = new ImageView(LoadScreenActivity.this);
		flLoadScreen.setScaleType(ScaleType.CENTER_CROP);
		flLoadScreen.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		flLoadScreen.setImageResource(R.drawable.loadscreen1);
		setContentView(flLoadScreen);
		
		
		(new AsyncTask<Void, Void, Void>(){
			
			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				startActivity(new Intent(LoadScreenActivity.this, TorChat.class));
				finish();
				super.onPostExecute(result);
			}
			
		}).execute();
		
	}

}
