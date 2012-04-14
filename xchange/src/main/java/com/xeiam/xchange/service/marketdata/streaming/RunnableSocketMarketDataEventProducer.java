package com.xeiam.xchange.service.marketdata.streaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
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
public class RunnableSocketMarketDataEventProducer implements RunnableMarketDataEventProducer {

  private final Logger log = LoggerFactory.getLogger(RunnableSocketMarketDataEventProducer.class);

  private final BlockingQueue<MarketDataEvent> queue;
  private final Socket socket;
  private boolean stopRequested=false;

  /**
   * Package constructor
   *
   * @param socket The underlying socket to use (operates in a
   * @param queue The market data event queue for the producer to work against
   */
  RunnableSocketMarketDataEventProducer(Socket socket, BlockingQueue<MarketDataEvent> queue) {
    this.queue = queue;
    this.socket = socket;
  }

  @Override
  public void run() {

    BufferedReader in = null;

    if (stopRequested) {
      return;
    }

    try {

      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      // Do this forever
      while (true) {
        // The readLine() will be interrupted by the external thread managing the socket
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
