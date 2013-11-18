package ru.dtlbox.torchat.entities;

import android.graphics.Bitmap;
import android.util.Log;

public class Contact {

	public enum ContactStatus {
		ONLINE, OFFLINE, CONNECTING
	}
	
	public static final String KEY_ONION_ADDRESS = "address";
	public static final String KEY_NICKNAME = "nickname";
	
	
	
	private String onionAddress;
	private String nickName;
	private ContactStatus status;
	private Bitmap avatar;
	private String tag;
	
	public Contact(String onionAddress, String nickName) {
		this.onionAddress = onionAddress;
		this.nickName = nickName;
		this.status = ContactStatus.OFFLINE;
		this.tag = "";
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
	
	public Contact setStatus(ContactStatus status) {
		this.status = status;
		return this;
	}
	
	public ContactStatus getStatus() {
		return status;
	}
	
	public Contact setTag(String tag) {
		this.tag = tag;
		return this;
	}
	
	public String getTag() {
		return tag;
	}
	
}
