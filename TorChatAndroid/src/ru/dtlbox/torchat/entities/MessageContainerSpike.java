package ru.dtlbox.torchat.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageContainerSpike {

	
	Map<String, List<ChatMessage>> messages = new HashMap<String, List<ChatMessage>>();
	
	protected MessageContainerSpike() {

	}
	
	public static MessageContainerSpike getInstanse() {
		return SingleToneHolder.instance;
	}
	
	protected static class SingleToneHolder {
		static MessageContainerSpike instance = new MessageContainerSpike();
	}
	
	public void saveMessage(ChatMessage message) {
		if(!messages.containsKey(message.getRecipient())) {
			messages.put(message.getRecipient(), new ArrayList<ChatMessage>());
		} 
		messages.get(message.getRecipient()).add(message);
	}
	
	public List<ChatMessage> getMessages(String user) {
		if(messages.containsKey(user))
			return messages.get(user);
		return new ArrayList<ChatMessage>();
	}
	
	public Map<String, List<ChatMessage>> getAllMessages() {
		return  messages;
	}
	
}
