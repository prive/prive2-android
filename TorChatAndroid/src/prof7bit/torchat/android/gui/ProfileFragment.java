package prof7bit.torchat.android.gui;

import prof7bit.torchat.android.R;
import prof7bit.torchat.android.service.Backend;
import ru.dtlbox.torchat.customviews.AvatarView;
import ru.dtlbox.torchat.entities.Contact;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("profile","on create view");
		View v = inflater.inflate(R.layout.fragment_profile, null);
		AvatarView avAvatar = (AvatarView)v.findViewById(R.id.av_avatar);
		avAvatar.setFrameImage(BitmapFactory.decodeResource(getResources(), R.drawable.face_big_blue));
		avAvatar.setImageResource(R.drawable.cat1);
		
		((TextView)v.findViewById(R.id.tv_onion_address)).setText(((TorChat)getActivity()).getOnionAddress());
		
		Button btnStartChat = (Button)v.findViewById(R.id.btn_start_chat);
		btnStartChat.setVisibility(View.GONE);
		Bundle bundle = getArguments();
		
		if(bundle != null) {
			if(bundle.containsKey(Contact.KEY_NICKNAME) && bundle.containsKey(Contact.KEY_ONION_ADDRESS)) {
				String nickname = bundle.getString(Contact.KEY_NICKNAME);
				String address = bundle.getString(Contact.KEY_ONION_ADDRESS);
				((TextView)v.findViewById(R.id.tv_onion_address)).setText(address);
				((TextView)v.findViewById(R.id.tv_nickname)).setText(nickname);
				btnStartChat.setVisibility(View.VISIBLE);
				btnStartChat.setTag(new Contact(address, nickname));
				btnStartChat.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Contact contact = (Contact)(v.getTag());
						Intent intent = new Intent(getActivity(), Backend.class);
						intent.setAction(Backend.ACTION_OPEN_CONNECTION);
						intent.putExtra(Backend.EXTRA_STRING_ONION_ADDRESS, contact.getOnionAddress());
						try {
							((TorChat)getActivity()).startService(intent);
						} catch (Exception e) {
							// TODO: handle exception
						}
						TestChatActivity.openTestChatActivityWithMessage(getActivity(), contact.getOnionAddress(), null);
						
					}
				});
			}
		}
		
		
			
			
			
		
		
		return v;
	}

}
