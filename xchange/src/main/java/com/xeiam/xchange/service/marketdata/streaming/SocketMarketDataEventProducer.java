package com.xeiam.xchange.service.marketdata.streaming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
   * Constructor
   * 
   * @param host
   * @param port
   * @param queue
   */
  SocketMarketDataEventProducer(String host, int port, BlockingQueue<MarketDataEvent> queue) {
    this.host = host;
    this.port = port;
    this.queue = queue;
  }

  @Override
  public void run() {

    log.debug("Executing against '{}:{}'", host, port);

    try {

      Socket socket;
      BufferedReader in;

      socket = new Socket(host, port);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      final String data = in.readLine();
      log.debug("Received data '{}'", data);

      MarketDataEvent marketDataEvent = new MarketDataEvent() {
        @Override
        public byte[] getRawData() {
          return data.getBytes();
        }
      };
      queue.put(marketDataEvent);

      in.close();
      socket.close();

    } catch (UnknownHostException e) {
      log.warn(e.getMessage(), e);
    } catch (IOException e) {
      log.warn(e.getMessage(), e);
    } catch (InterruptedException e) {
      log.warn(e.getMessage(), e);
    }
  }
}
