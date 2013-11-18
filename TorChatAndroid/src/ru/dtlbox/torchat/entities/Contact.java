package ru.dtlbox.torchat.entities;

import android.graphics.Bitmap;
import android.util.Log;

public class Contact {

	
	public static final String KEY_ONION_ADDRESS = "address";
	public static final String KEY_NICKNAME = "nickname";
	
	
	private String onionAddress;
	private String nickName;
	
	private Bitmap avatar;
	
	
	public Contact(String onionAddress, String nickName) {
		this.onionAddress = onionAddress;
		this.nickName = nickName;
	}
	
	public String getOnionAddress() {
		return onionAddress;
	}
	
	public void setOnionAddress(String onionAddress) {
		this.onionAddress = onionAddress;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public Bitmap getAvatar() {
		return avatar;
	}
	
	public Contact setAvatar(Bitmap avatar) {
		Log.d("xxxxxxxxxxx","set avatar");
		this.avatar = avatar;
		return this;
	}
	
	public boolean hasAvatar() {
		return avatar!=null;
	}
	
	
}
