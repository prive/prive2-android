package prof7bit.torchat.core;

import java.io.EOFException;

/**
 * This class handles the protocol message "status"
 * 
 * ****************************** 
 * "status" packet example:
 * status availiable
 * ******************************
 * 
 * @author busylee demonlee999@gmail.com
 * 
 */

public class Msg_status extends Msg {
	final static String MSG_COMMAND = "status";
	
	final static String STATUS_AVAILIABLE = "availiable";
	final static String STATUS_AWAY = "away";
	final static String STATUS_EXTENDED_AWAY= "xa";
	final static String STATUS_UNDEFINED = "undefined";
	
	String mStatus = STATUS_UNDEFINED;

	public Msg_status(Connection connection) {
		super(connection);
		mCommand = MSG_COMMAND;
	}

	@Override
	public void parse(MessageBuffer buf) throws XMessageParseException {
		try {
			//read status string
			mStatus = buf.readString();
		} catch(EOFException e){
			throw new XMessageParseException(e.getLocalizedMessage());
		}

	}

	@Override
	public MessageBuffer serialize() {
		MessageBuffer mb = new MessageBuffer();
		
		//write commend first
		writeCommand(mb);
		
		//write status
		writeStatus(mb);
		
		return mb;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		mConnection.onStatusReceived(this);
	}
	
	protected void writeStatus(MessageBuffer buffer){
		buffer.writeString(mStatus != null ? mStatus : STATUS_UNDEFINED);
	}
	
	public void setAvailiable(){
		mStatus = STATUS_AVAILIABLE;
	}
	
	/*********************
	 * GETTERS AND SETTERS
	 ********************/
	
	public String getStatus() {
		return mStatus;
	}

	protected void setStatus(String status) {
		this.mStatus = status;
	}

}
