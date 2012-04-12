package com.xeiam.xchange.service.marketdata.streaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

/**
 * <p>
 * Producer to provide the following to {@link MarketDataListener}:
 * </p>
 * <ul>
 * <li>Raw market data from the upstream server</li>
 * </ul>
 *
 * @since 0.0.1
 */
class SocketMarketDataEventProducer implements Runnable {

  private final Logger log = LoggerFactory.getLogger(SocketMarketDataEventProducer.class);

  private final String host;
  private final int port;
  private final BlockingQueue<MarketDataEvent> queue;

  /**
   * Package constructor
   *
   * @param host  The host name (e.g. "intersango.com")
   * @param port  The port (e.g. 1337(
   * @param queue The market data event queue for the producer to work against
   */
  SocketMarketDataEventProducer(String host, int port, BlockingQueue<MarketDataEvent> queue) {
    this.host = host;
    this.port = port;
    this.queue = queue;
  }

  @Override
  public void run() {

    log.debug("Executing against '{}:{}'", host, port);

    Socket socket = null;
    BufferedReader in = null;

    try {


      socket = new Socket(host, port);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      // Do this forever (we'll get an interrupted exception on close)
      // TODO Need a better interrupt mechanism - this one only seems to trigger when queue.put fails
      while (true) {
        final String data = in.readLine();
        log.debug("Received data '{}'", data);

        // Create an event
        MarketDataEvent marketDataEvent = new MarketDataEvent() {
          @Override
          public byte[] getRawData() {
            return data.getBytes();
          }
        };
        queue.put(marketDataEvent);
      }

    } catch (UnknownHostException e) {
      log.warn(e.getMessage(), e);
    } catch (IOException e) {
      log.warn(e.getMessage(), e);
    } catch (InterruptedException e) {
      // Cannot put onto the queue we're probably shutting down
      log.debug("Closing producer due to interrupt.");
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e1) {
          log.warn(e1.getMessage(), e1);
        }
      }
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e1) {
          log.warn(e1.getMessage(), e1);
        }
      }

    }
  }
}
