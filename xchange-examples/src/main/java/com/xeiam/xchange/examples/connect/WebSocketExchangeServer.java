/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.examples.connect;

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

import org.joda.money.BigMoney;

import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.service.marketdata.Ticker;
import com.xeiam.xchange.streaming.websocket.HandshakeData;
import com.xeiam.xchange.streaming.websocket.WebSocket;
import com.xeiam.xchange.streaming.websocket.WebSocketServer;
import com.xeiam.xchange.utils.MoneyUtils;

/**
 * <p>
 * A simple WebSocketServer implementation for an exchange
 * </p>
 * <h3>How to use it</h3>
 * <p>
 * Simply run this up through main() before attempting a connection with multiple {@link WebSocketExchangeClient} instances
 * </p>
 */
public class WebSocketExchangeServer extends WebSocketServer {

  public WebSocketExchangeServer(int port) throws UnknownHostException {
    super(new InetSocketAddress(InetAddress.getByName("localhost"), port));
  }

  public WebSocketExchangeServer(InetSocketAddress address) {
    super(address);
  }

  /**
   * Main entry point to the demo
   * 
   * @param args Command line arguments (not used)
   * @throws InterruptedException If something goes wrong
   * @throws IOException If something goes wrong
   */
  public static void main(String[] args) throws InterruptedException, IOException {
    WebSocket.DEBUG = true;
    int port = 8887;

    // Create local exchange server
    final WebSocketExchangeServer exchangeServer = new WebSocketExchangeServer(port);
    exchangeServer.start();
    System.out.println("ExchangeServer started on port: " + exchangeServer.getPort());

    // Set up a some local currencies quoted against BTC
    List<String> currencies = new ArrayList<String>(Arrays.asList("USD", "GBP", "EUR"));

    // Create a continuously updating set of trade data
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(currencies.size() * 2);

    // Create a scheduled update of values
    for (int i = 0; i < currencies.size(); i++) {

      // Schedule producers and consumers at different rates for demo
      executorService.scheduleAtFixedRate(new Runnable() {

        private final SecureRandom random = new SecureRandom();

        @Override
        public void run() {
          // TODO Fix this
          BigMoney money = MoneyUtils.parseFiat("USD " + random.nextLong());
          Ticker ticker = new Ticker(money, money, money, CurrencyPair.BTC_USD, random.nextLong());
          try {
            exchangeServer.sendToAll(ticker.toString());
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

        }
      }, 1L, 2L, TimeUnit.SECONDS);
    }

    // Keep running until a timeout occurs
    executorService.awaitTermination(5, TimeUnit.SECONDS);

  }

  @Override
  public void onClientOpen(WebSocket conn, HandshakeData handshake) {
    try {
      this.sendToAll(conn + " is monitoring trades");
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
    System.out.println(conn + " is monitoring trades");
  }

  @Override
  public void onClientClose(WebSocket conn, int code, String reason, boolean remote) {
    try {
      this.sendToAll(conn + " has disconnected");
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
    System.out.println(conn + " has disconnected");
  }

  @Override
  public void onClientMessage(WebSocket conn, String message) {
    try {
      this.sendToAll(conn + ": " + message);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
    System.out.println(conn + ": " + message);
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
  }

}
