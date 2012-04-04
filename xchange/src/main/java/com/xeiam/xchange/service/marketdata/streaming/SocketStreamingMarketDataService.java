package com.xeiam.xchange.service.marketdata.streaming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * <p>Streaming market data service to provide the following to streaming market data API:</p>
 * <ul>
 * <li>Connection to an upstream market data source through a direct socket connection</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class SocketStreamingMarketDataService extends BaseStreamingMarketDataService {

  /**
   * A direct socket connection only requires a host and a port (no protocol)
   * @param host The host name
   * @param port The port to connect to
   */
  public SocketStreamingMarketDataService(String host, int port) {
    super(host, port);
  }

  public void connect() {
    Socket echoSocket;
    BufferedReader in;

    try {
      echoSocket = new Socket(host, port);
      in = new BufferedReader(new InputStreamReader(
        echoSocket.getInputStream()));

      int lineCount = 10;
      while (lineCount > 0) {
        System.out.println("Data: " + in.readLine());
        lineCount--;
      }

      in.close();
      echoSocket.close();


    } catch (UnknownHostException e) {
      System.err.println("Unknown host: " + e.getMessage());
      System.exit(1);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }

  @Override
  public void disconnect() {

  }

}
