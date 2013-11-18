package ru.dtlbox.torchat.tests;

import java.util.ArrayList;
import java.util.List;

import ru.dtlbox.torchat.entities.ChatMessage;

public class ChatTestActivityDesingTest {
	
	
	public static List<ChatMessage> generateMessages() {
		List<ChatMessage> result = new ArrayList<ChatMessage>();
		result.add(new ChatMessage("", "").setText("Привет"));
		result.add(new ChatMessage("", "").setText("как дела?"));
		result.add(new ChatMessage("0", "").setText("нормально"));
		result.add(new ChatMessage("0", "").setText("дота го?"));
		result.add(new ChatMessage("", "").setText("ага"));
		result.add(new ChatMessage("0", "").setText("погнали"));
		
		
			
		
		return result;
	}

}
