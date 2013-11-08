package prof7bit.torchat.core;

/*
 * This is the abstract base class for all protocol messages 
 */
abstract class Msg {
	protected Connection mConnection;
	protected String mCommand = null;
	
	public Msg(Connection connection){
		this.mConnection = connection;
	}
	
	public abstract void parse(MessageBuffer buf) throws XMessageParseException;
	public abstract MessageBuffer serialize();
	public abstract void execute();
	
	/**
	 * Write command for message into message buffer
	 * It needs mCommand variable not empty.
	 * @param buffer
	 */
	@SuppressWarnings("unused")
	protected void writeCommand(MessageBuffer buffer){
		if (mCommand != null)
			buffer.writeString(mCommand);
	}
	
	/**
	 * return connection of this message
	 * @return
	 */
	public Connection getConnection(){
		return mConnection;
	}
}
