package ru.dtlbox.torchat.tests;

import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import prof7bit.torchat.android.R;
import ru.dtlbox.torchat.entities.Contact;

public class AvatarSpike {
	
	
	
	public static final int[] avatarId = new int[]{
		R.drawable.avatar_0,
		R.drawable.avatar_1,
		R.drawable.avatar_2,
		R.drawable.avatar_3,
		R.drawable.avatar_4,
		R.drawable.avatar_5,
		R.drawable.avatar_6,
		R.drawable.avatar_7,
		R.drawable.cat1, R.drawable.cat2};
	
	
	
	public static void setAvatars(Resources res, List<Contact> contacts) {
		for(int i = 0; i<contacts.size(); i++)
			if(i<AvatarSpike.avatarId.length-1) {
				Bitmap bitmap = BitmapFactory.decodeResource(res, AvatarSpike.avatarId[i+1]);
				Log.d("set avatar", bitmap==null ? "bitmap NULL" : "bitmap ok");
				contacts.get(i).setAvatar(bitmap);
			}
	}
	
	public Bitmap getMyAvatar(Resources res) {
		return BitmapFactory.decodeResource(res, AvatarSpike.avatarId[0]);
	}

}
