package prof7bit.torchat.core;

/**
 * This class handles the protocol message "add_me"
 * 
 * ****************************** 
 * "add_me" packet example:
 * add_me
 * ******************************
 * 
 * @author busylee demonlee999@gmail.com
 * 
 */
public class Msg_add_me extends Msg {
	final static String MSG_COMMAND = "add_me";

	public Msg_add_me(Connection connection) {
		super(connection);
		mCommand = MSG_COMMAND;
	}

	@Override
	public void parse(MessageBuffer buf) throws XMessageParseException {
		// TODO Auto-generated method stub

	}

	@Override
	public MessageBuffer serialize() {
		MessageBuffer mb = new MessageBuffer();
		
		writeCommand(mb);
		
		return mb;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
