package prof7bit.torchat.core;

import java.io.EOFException;

/**
 * This class handles the protocol message "pong".
 * 
 * ****************************** 
 * "pong" packet example: 
 * pong 42610670386469110587786764749331231960 
 *      <random-string>
 * ******************************
 * 
 * @author Bernd Kreuss <demonlee999@gmail.com>
 *
 */
public class Msg_pong extends Msg {

	final static String MSG_COMMAND = "pong";
	String mRandomString = null;
	
	public Msg_pong(Connection connection) {
		super(connection);
		mCommand = MSG_COMMAND;
	}

	@Override
	public void parse(MessageBuffer buf) throws XMessageParseException {
		try{
			//read random string
			mRandomString = buf.readString();
		} catch (EOFException e){
			throw new XMessageParseException(e.getLocalizedMessage());
		}
	}

	@Override
	public MessageBuffer serialize() {
		MessageBuffer mb = new MessageBuffer();
		
		//write command first
		writeCommand(mb);
		
		writeRandomString(mb);
		
		return mb;
	}

	@Override
	public void execute() {
		System.out.println("Msg_pong.execute()");
		mConnection.getConnectionHandler().onPongReceived(this);
	}
	
	/**
	 * write random string into passed buffer
	 * 
	 * @param buffer
	 */
	protected void writeRandomString(MessageBuffer buffer) {
		if (mRandomString != null)
			buffer.writeString(mRandomString);
	}
	
	/*********************
	 * GETTERS AND SETTERS
	 ********************/
	public String getRandomString() {
		return mRandomString;
	}

	public void setRandomString(String mRandomString) {
		this.mRandomString = mRandomString;
	}

}
