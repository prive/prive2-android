package ru.dtlbox.torchat.entities;

public class ChatMessage {
	
	private String recipient;
	private String owner;
	
	private String text;
	
//	ChatMessageType type;
//	
//	enum ChatMessageType {
//		TEXT_MESSAGE
//	}
	
	
	public ChatMessage(String owner, String recipient) {
		this.recipient = recipient;
		this.owner = owner;
	}

	public ChatMessage setText(String text) {
		this.text = text;
		return this;
	}
	
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public String getRecipient() {
		return recipient;
	}
	
	public String getText() {
		return text;
	}
	
	
	 

}
