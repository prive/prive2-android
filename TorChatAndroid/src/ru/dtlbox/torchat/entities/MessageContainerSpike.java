package ru.dtlbox.torchat.entities;

import java.util.List;
import java.util.Map;

public class MessageContainerSpike {

	
	Map<String, List<ChatMessage>> messages;
	
	protected MessageContainerSpike() {

	}
	
	public static MessageContainerSpike getInstanse() {
		return SingleToneHolder.instance;
	}
	
	protected static class SingleToneHolder {
		static MessageContainerSpike instance = new MessageContainerSpike();
	} 
	
	
	
	
}
