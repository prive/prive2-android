package prof7bit.torchat.android.gui;

import ru.dtlbox.torchat.dbworking.DBManager;
import ru.dtlbox.torchat.entities.Contact;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class AddUserActivity extends Activity {
	
	SimpleAddLinearLayout layout;
	EditText etAdress;
	EditText etNickname;
	Button btnAdd;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		context = AddUserActivity.this;
		
		layout = new SimpleAddLinearLayout(context);
		
		etAdress = new EditText(context);
		etNickname = new EditText(context);
		btnAdd = new Button(context);
		
		setWidthMatchParentAndHeightWrapContent(layout, etAdress, etNickname, btnAdd);
		
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.add(etAdress).add(etNickname).add(btnAdd);
		
		setContentView(layout);
		
		etAdress.setHint("onion address");
		etNickname.setHint("nickname");
		btnAdd.setText("Add");
		
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String address = etAdress.getText().toString();
				String nickname = etNickname.getText().toString();
				if(!(isCorrect(nickname)&&isCorrect(address))) {
					Toast.makeText(context, "incorrect data", Toast.LENGTH_SHORT).show();
				} else {
					DBManager mDbManager = new DBManager().init(context);
					mDbManager.insertContact(new Contact(address, nickname));
					Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});
	
	}
	
	public void setWidthMatchParentAndHeightWrapContent(View... views) {
		for(View v : views) {
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}
	
	class SimpleAddLinearLayout extends LinearLayout {

		public SimpleAddLinearLayout(Context context) {
			super(context);
		}
		
		public SimpleAddLinearLayout add(View v) {
			this.addView(v);
			return this;
		}
		
	}
	
	boolean isCorrect(String str) {
		//TODO 
		return !"".equals(str);
	}
	
	
	

}
