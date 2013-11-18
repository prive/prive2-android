package ru.dtlbox.torchat.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageButton;

public class CheckableImageButton extends ImageButton implements Checkable {

	 private boolean isChecked = false;
	
	public CheckableImageButton(Context context) {
		super(context);
	}
	
	
	public CheckableImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	

	@Override
	public boolean isChecked() {
		
		return isChecked;
	}


	@Override
	public void setChecked(boolean checked) {
		isChecked = checked;
		refreshDrawableState();
		
	}

	@Override
	public void toggle() {
		isChecked = !isChecked;
		
	}
	
	
	private static final int[] CheckedStateSet = {
	    android.R.attr.state_checked,
	};

	@Override
	public int[] onCreateDrawableState(int extraSpace) {
	    final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
	    if (isChecked()) {
	        mergeDrawableStates(drawableState, CheckedStateSet);
	    }
	    return drawableState;
	}

	@Override
	public boolean performClick() {
	    toggle();
	    return super.performClick();
	}

}
