package prof7bit.torchat.core;

import java.io.IOException;

import prof7bit.reactor.ListenPort;
import prof7bit.reactor.ListenPortHandler;
import prof7bit.reactor.Reactor;
import prof7bit.reactor.TCP;
import prof7bit.reactor.TCPHandler;
import android.util.Log;

public class Client implements ListenPortHandler, ConnectionHandler {
	final static String LOG_TAG = "Client";

	private ClientHandler clientHandler;
	private Reactor reactor;
	private ListenPort listenPort;

	public Client(ClientHandler clientHandler, int port) throws IOException {
		this.clientHandler = clientHandler;
		this.reactor = new Reactor();
		this.listenPort = new ListenPort(reactor, this);
		this.listenPort.listen(port);
	}

	public void close() throws InterruptedException {
		this.reactor.close();
	}

	@Override
	public TCPHandler onAccept(TCP tcp) {
		Log.i(LOG_TAG, "new connection was accepted");
		Connection c = new Connection(tcp, this);
		return c;
	}

	// TODO change logic
	@Override
	public void onPingReceived(Msg_ping msg) {

	}

	@Override
	public void onPongReceived(Msg_pong msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessageReceived(Msg_message msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnect(String reason) {

	}
}
