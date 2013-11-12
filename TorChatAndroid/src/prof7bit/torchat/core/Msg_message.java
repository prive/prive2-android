package prof7bit.torchat.core;

import java.io.EOFException;

import android.util.Log;

/**
 * This class handles the protocol message "message"
 * 
 * ****************************** 
 * "message" packet example:
 * message Hi, Bob! I
 * am Alice! <message-text>
 * ******************************
 * 
 * @author busylee
 * 
 */
public class Msg_message extends Msg {

	final static String MSG_COMMAND = "message";
	String mMessage = null;

	public Msg_message(Connection connection) {
		super(connection);
		mCommand = MSG_COMMAND;
	}

	@Override
	public void parse(MessageBuffer buf) throws XMessageParseException {
		try {
			mMessage = "";
			/* get text of message
			 * spike yet
			 */
			while(true)
				mMessage += buf.readString() + " ";

		} catch (EOFException e) {
			
		}

	}

	@Override
	public MessageBuffer serialize() {
		MessageBuffer mb = new MessageBuffer();
		
		//write "message" command first
		writeCommand(mb);
		
		writeMessage(mb);
		
		return mb;
	}

	@Override
	public void execute() {
		mConnection.onMessageReceived(this);
	}
	
	protected void writeMessage(MessageBuffer buffer){
		if(mMessage != null)
			buffer.writeString(mMessage);
	}
	
	/*********************
	 * GETTERS AND SETTERS
	 ********************/
	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String mMessage) {
		this.mMessage = mMessage;
	}

}
