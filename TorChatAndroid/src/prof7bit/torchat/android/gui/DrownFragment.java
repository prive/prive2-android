package prof7bit.torchat.android.gui;


import prof7bit.torchat.android.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class DrownFragment extends Fragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		super.onCreateView(inflater, container, savedInstanceState);
		
		
		
		ImageView fl = new ImageView(getActivity());
		fl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		
		fl.setBackgroundResource(R.drawable.timelinescreen);
		fl.setScaleType(ScaleType.FIT_START);
		
		
		return fl;
	}

}
