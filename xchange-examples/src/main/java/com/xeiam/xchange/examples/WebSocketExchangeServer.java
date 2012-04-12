package com.xeiam.xchange.examples;

import com.xeiam.xchange.SymbolPair;
import com.xeiam.xchange.service.marketdata.Ticker;
import com.xeiam.xchange.streaming.websocket.HandshakeData;
import com.xeiam.xchange.streaming.websocket.WebSocket;
import com.xeiam.xchange.streaming.websocket.WebSocketServer;
import com.xeiam.xchange.utils.MoneyUtils;
import org.joda.money.BigMoney;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>A simple WebSocketServer implementation for an exchange</p>
 * <h3>How to use it</h3>
 * <p>Simply run this up through main() before attempting a connection with multiple {@link WebSocketExchangeClient} instances</p>
 */
public class WebSocketExchangeServer extends WebSocketServer {

  public WebSocketExchangeServer(int port) throws UnknownHostException {
		super( new InetSocketAddress( InetAddress.getByName( "localhost" ), port ) );
	}
	
	public WebSocketExchangeServer(InetSocketAddress address) {
		super( address );
	}

  /**
   * Main entry point to the demo
   * @param args Command line arguments (not used)
   * @throws InterruptedException If something goes wrong
   * @throws IOException If something goes wrong
   */
  public static void main( String[] args ) throws InterruptedException , IOException {
    WebSocket.DEBUG = true;
    int port = 8887;
    
    // Create local exchange server
    final WebSocketExchangeServer exchangeServer = new WebSocketExchangeServer( port );
    exchangeServer.start();
    System.out.println("ExchangeServer started on port: " + exchangeServer.getPort());

    // Set up a some local currencies quoted against BTC
    List<String> currencies = new ArrayList<String>(Arrays.asList("USD","GBP","EUR"));

    // Create a continuously updating set of trade data
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(currencies.size()*2);

    // Create a scheduled update of values
    for (int i = 0; i < currencies.size(); i++) {

      // Schedule producers and consumers at different rates for demo
      executorService.scheduleAtFixedRate(new Runnable() {
        
        private final SecureRandom random = new SecureRandom();

        @Override
        public void run() {
          // TODO Fix this
          BigMoney money = MoneyUtils.parseFiat("USD "+random.nextLong());
          Ticker ticker = new Ticker(money, SymbolPair.BTC_USD,random.nextLong());
          try {
            exchangeServer.sendToAll(ticker.toString());
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

        }
      }, 1L, 2L,TimeUnit.SECONDS);
    }

    // Keep running until a timeout occurs
    executorService.awaitTermination(5, TimeUnit.SECONDS);

  }

	public void onClientOpen( WebSocket conn, HandshakeData handshake ) {
		try {
			this.sendToAll( conn + " is monitoring trades" );
		} catch ( InterruptedException ex ) {
			ex.printStackTrace();
		}
		System.out.println(conn + " is monitoring trades");
	}

	public void onClientClose( WebSocket conn, int code, String reason, boolean remote ) {
		try {
			this.sendToAll( conn + " has disconnected" );
		} catch ( InterruptedException ex ) {
			ex.printStackTrace();
		}
    System.out.println(conn + " has disconnected");
	}

	public void onClientMessage( WebSocket conn, String message ) {
		try {
			this.sendToAll( conn + ": " + message );
		} catch ( InterruptedException ex ) {
			ex.printStackTrace();
		}
    System.out.println(conn + ": " + message);
	}

	@Override
	public void onError( WebSocket conn, Exception ex ) {
		ex.printStackTrace();
	}

}
