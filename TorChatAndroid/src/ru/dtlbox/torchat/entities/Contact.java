package ru.dtlbox.torchat.entities;

public class Contact {

	
	private String onionAddress;
	private String nickName;
	
	
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
	
	
}
