package prof7bit.torchat.android.gui;

import prof7bit.torchat.android.R;
import ru.dtlbox.torchat.customviews.AvatarView;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		
		
		return v;
	}

}
