package ru.dtlbox.torchat.customviews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckableGroup {

	
	List<CheckableImageButton> listButtons = new ArrayList<CheckableImageButton>();
	
	public CheckableGroup(CheckableImageButton... buttons) {
		for(CheckableImageButton button : buttons)
			listButtons.add(button);
		
	}
	
	public void changeChecked(CheckableImageButton checkedbutton) {
		for(CheckableImageButton button : listButtons)
			if(button!=checkedbutton)
				button.setChecked(false);
			
	}
	
}
