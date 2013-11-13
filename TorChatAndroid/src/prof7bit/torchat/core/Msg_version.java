package prof7bit.torchat.core;

import android.util.Log;

public class Msg_version extends Msg {

	final static String MSG_COMMAND = "version";
	final static String VERSION = "1.0.0.0";
	
	public Msg_version(Connection connection) {
		super(connection);
		mCommand = MSG_COMMAND;
	}

	@Override
	public void parse(MessageBuffer buf) throws XMessageParseException {

	}

	@Override
	public MessageBuffer serialize() {
		MessageBuffer mb = new MessageBuffer();
		
		writeCommand(mb);
		
		writeVersion(mb);
		
		return mb;
	}
	
	protected void writeVersion(MessageBuffer buffer){
		buffer.writeString(VERSION);
	}

	@Override
	public void execute() {
		Log.i("", "version received");
	}

}
