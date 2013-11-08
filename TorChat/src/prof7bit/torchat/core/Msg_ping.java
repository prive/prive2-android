package prof7bit.torchat.core;

import java.io.EOFException;

/**
 * This class handles the protocol message "ping".
 * ******************************
 * "ping" packet example:
 * ping alice34343434343 42610670386469110587786764749331231960
 *      <onion-address>  <random-string>
 * ******************************
 * @author <demonlee999@gmail.com>
 *
 */
public class Msg_ping extends Msg {
	
	final static String MSG_COMMAND = "ping";
	String mRandomString = null;
	String mOnionAddress = null;

	public Msg_ping(Connection connection) {
		super(connection);
		mCommand = MSG_COMMAND;
	}

	@Override
	public void parse(MessageBuffer buf) throws XMessageParseException {
		// TODO Auto-generated method stub
		try {
			//get onion address first
			mOnionAddress = buf.readString();
			
			//get received random string
			mRandomString = buf.readString();
		} catch (EOFException e){
			new XMessageParseException(e.getLocalizedMessage());
		}
	}

	@Override
	public MessageBuffer serialize() {
		MessageBuffer mb = new MessageBuffer();
		
		//write "ping" command first
		writeCommand(mb);
		
		writeOnionAddress(mb);
		
		writeRandomString(mb);
		
		return mb;
	}

	@Override
	public void execute() {
		System.out.println("Msg_ping.execute()");
		// TODO Auto-generated method stub
	}
	
	/**
	 * write onion address into passed buffer
	 * @param buffer
	 */
	protected void writeOnionAddress(MessageBuffer buffer){
		if(mOnionAddress != null)
			buffer.writeString(mOnionAddress);
	}	
	
	protected void writeRandomString(MessageBuffer buffer){
		if(mRandomString != null)
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
	
	public String getOnionAddress() {
		return mOnionAddress;
	}

	public void setOnionAddress(String mOnionAddress) {
		this.mOnionAddress = mOnionAddress;
	}

}
