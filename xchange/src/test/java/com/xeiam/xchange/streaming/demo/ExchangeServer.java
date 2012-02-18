package com.xeiam.xchange.streaming.demo;

import com.xeiam.xchange.streaming.HandshakeData;
import com.xeiam.xchange.streaming.websocket.WebSocket;
import com.xeiam.xchange.streaming.websocket.WebSocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * A simple WebSocketServer implementation for an exchange
 */
public class ExchangeServer extends WebSocketServer {

	public ExchangeServer(int port) throws UnknownHostException {
		super( new InetSocketAddress( InetAddress.getByName( "localhost" ), port ) );
	}
	
	public ExchangeServer(InetSocketAddress address) {
		super( address );
	}

	public void onClientOpen( WebSocket conn, HandshakeData handshake ) {
		try {
			this.sendToAll( conn + " is monitoring trades" );
		} catch ( InterruptedException ex ) {
			ex.printStackTrace();
		}
		System.out.println( conn + " is monitoring trades" );
	}

	public void onClientClose( WebSocket conn, int code, String reason, boolean remote ) {
		try {
			this.sendToAll( conn + " has disconnected" );
		} catch ( InterruptedException ex ) {
			ex.printStackTrace();
		}
		System.out.println( conn + " has disconnected" );
	}

	public void onClientMessage( WebSocket conn, String message ) {
		try {
			this.sendToAll( conn + ": " + message );
		} catch ( InterruptedException ex ) {
			ex.printStackTrace();
		}
		System.out.println( conn + ": " + message );
	}

	public static void main( String[] args ) throws InterruptedException , IOException {
		WebSocket.DEBUG = true;
		int port = 8887;
		try {
			port = Integer.parseInt( args[ 0 ] );
		} catch ( Exception ex ) {
		}
		ExchangeServer s = new ExchangeServer( port );
		s.start();
		System.out.println( "ExchangeServer started on port: " + s.getPort() );

		BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
		while ( true ) {
			String in = sysin.readLine();
			s.sendToAll( in );
		}
	}

	@Override
	public void onError( WebSocket conn, Exception ex ) {
		ex.printStackTrace();
	}
}
