/*
 * socket.io-java-client IOConnection.java
 *
 * Copyright (c) 2011, Enno Boland
 * socket.io-java-client is a implementation of the socket.io protocol in Java.
 * 
 * See LICENSE file for more information
 */
package com.xeiam.xchange.streaming.socketio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.xeiam.xchange.streaming.socketio.IOState.STATE_READY;

/**
 * The Class IOConnection.
 */
class IOConnection {

  private final Logger log = LoggerFactory.getLogger(IOConnection.class);

    /**
     * The current state
     */
  private IOState state = IOState.STATE_INIT;

  /**
   * Socket.io path
   */
  public static final String SOCKET_IO_1 = "/socket.io/1/";

  /**
   * All available connections
   */
  private static HashMap<String, List<IOConnection>> connections = new HashMap<String, List<IOConnection>>();

  /**
   * The url for this connection
   */
  private URL url;

  /**
   * The transport for this connection (WebSocket, XHR etc)
   */
  private IOTransport transport;

  /**
   * The session id of this connection
   */
  private String sessionId;

  /**
   * The heartbeat timeout - set by the server
   */
  private long heartbeatTimeout;

  /**
   * The closing timeout - set by the server
   */
  private long closingTimeout;

  /**
   * The protocols supported by the server
   */
  private List<String> protocols = new ArrayList<String>();

  /**
   * The output buffer used to cache messages while (re-)connecting
   */
  private ConcurrentLinkedQueue<String> outputBuffer = new ConcurrentLinkedQueue<String>();

  /**
   * The sockets of this connection
   */
  private HashMap<String, SocketIO> sockets = new HashMap<String, SocketIO>();

  /**
   * The first socket to be connected (the socket.io server does not send a connected response to this one)
   */
  private SocketIO firstSocket = null;

  /**
   * The reconnect timer. IOConnect waits a second before trying to reconnect
   */
  final private Timer backgroundTimer = new Timer("backgroundTimer");

  /**
   * A String representation of {@link #url}
   */
  private String urlStr;

  /**
   * The last occurred exception, which will be given to the user if
   * IOConnection gives up
   */
  private Exception lastException;

  /**
   * The next ID to use
   */
  private int nextId = 1;

  /**
   * Acknowledgments
   */
  HashMap<Integer, IOAcknowledge> acknowledge = new HashMap<Integer, IOAcknowledge>();

  /**
   * True if there is a keep-alive in {@link #outputBuffer}.
   */
  private boolean keepAliveInQueue;

  /**
   * The heartbeat timeout task (only null before connection has been initialised)
   */
  private HeartbeatTimeoutTask heartbeatTimeoutTask;

  /**
   * <p>Handles dropping this IOConnection if no heartbeat is received within lifetime</p>
   */
  private class HeartbeatTimeoutTask extends TimerTask {

    @Override
    public void run() {
      setState(IOState.STATE_INVALID);
      error(new SocketIOException(
        "Timeout Error. No heartbeat from server within lifetime of the socket. Closed.",
        lastException));
    }
  }

  /**
   * The reconnect task (null if no reconnection is in progress)
   */
  private ReconnectTask reconnectTask = null;

  /**
   * <p>Handles reconnect attempts</p>
   */
  private class ReconnectTask extends TimerTask {

    @Override
    public void run() {
      connectTransport();
      if (!keepAliveInQueue) {
        sendPlain("2::");
        keepAliveInQueue = true;
      }
    }
  }

  /**
   * <p>Handles connecting to the server with an {@link IOTransport}</p>
   */
  private class ConnectThread extends Thread {

    /**
     * Instantiates a new background thread.
     */
    public ConnectThread() {
      super("ConnectThread");
    }

    /**
     * Tries handshaking if necessary and connects with corresponding
     * transport afterwards.
     */
    public void run() {
      if (IOState.STATE_INIT == IOConnection.this.getState()) {
        handshake();
      }
      connectTransport();
    }

    /**
     * Initial handshake to establish protocols and versions
     */
    private void handshake() {
      URL url;
      String response;
      URLConnection connection;
      try {
        setState(IOState.STATE_HANDSHAKE);
        // Append the socket.io version 1 path to get the supported protocols back
        url = new URL(IOConnection.this.url.toString() + SOCKET_IO_1);
        if ("https".equals(url.getProtocol())) {
          // Create a trust manager that does not validate certificate chains
          TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
              public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
              }

              public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {
              }

              public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {
              }
            }
          };

          // Install the all-trusting trust manager
          SSLContext sc = SSLContext.getInstance("SSL");
          sc.init(null, trustAllCerts, new java.security.SecureRandom());
          HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

          // Now you can access an https URL without having the certificate in the truststore
          connection = url.openConnection();
        } else {
          connection = url.openConnection();
        }
        int connectTimeout = 10000;
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(connectTimeout);

        log.trace("Opening input stream for '" + url.toString() + "'");
        InputStream stream = connection.getInputStream();

        Scanner in = new Scanner(stream);
        response = in.nextLine();
        log.trace("Server response: " + response);
        if (response.contains(":")) {
          String[] data = response.split(":");
          heartbeatTimeout = Long.parseLong(data[1]) * 1000;
          closingTimeout = Long.parseLong(data[2]) * 1000;
          protocols = Arrays.asList(data[3].split(","));
          sessionId = data[0];
        }
      } catch (IOException e) {
        error(new SocketIOException("Error while handshaking (IO)", e));
      } catch (NoSuchAlgorithmException e) {
        error(new SocketIOException("Error while handshaking (SSL)", e));
      } catch (KeyManagementException e) {
        error(new SocketIOException("Error while handshaking (SSL Keys)", e));
      }
    }

  }

  ;

  /**
   * Creates a new connection or returns the corresponding one.
   *
   * @param origin the origin
   * @param socket the socket
   *
   * @return a IOConnection object
   */
  static public IOConnection register(String origin, SocketIO socket) {
    List<IOConnection> list = connections.get(origin);
    if (list == null) {
      list = new LinkedList<IOConnection>();
      connections.put(origin, list);
    } else {
      for (IOConnection connection : list) {
        if (connection.register(socket))
          return connection;
      }
    }

    IOConnection connection = new IOConnection(origin, socket);
    list.add(connection);
    return connection;
  }

  /**
   * Determine which transport to use using the results from the handshake
   */
  private void connectTransport() {
    if (IOState.STATE_INVALID == getState()) {
      return;
    }
    setState(IOState.STATE_CONNECTING);
    if (protocols.contains(WebSocketTransport.TRANSPORT_NAME)) {
      transport = WebSocketTransport.create(url, this);
    } else if (protocols.contains(XhrTransport.TRANSPORT_NAME)) {
      transport = XhrTransport.create(url, this);
    } else {
      error(new SocketIOException("This client does not support any of the offered server transports"));
      return;
    }

    transport.connect();
  }

  /**
   * Creates a new {@link IOAcknowledge} instance which sends its arguments
   * back to the server.
   *
   * @param message the message
   *
   * @return an {@link IOAcknowledge} instance, may be <code>null</code> if
   *         server doesn't request one.
   */
  private IOAcknowledge remoteAcknowledge(IOMessage message) {
    if (message.getId().endsWith("+") == false)
      return null;
    final String id = message.getId();
    final String endPoint = message.getEndpoint();
    return new IOAcknowledge() {
      @Override
      public void ack(Object... args) {
        JSONArray array = new JSONArray();
        for (Object o : args) {
          try {
            array.put(o == null ? JSONObject.NULL : o);
          } catch (Exception e) {
            error(new SocketIOException(
              "You can only put values in IOAcknowledge.ack() which can be handled by JSONArray.put()",
              e));
          }

        }
        IOMessage ackMsg = new IOMessage(IOMessage.TYPE_ACK, endPoint, id + array.toString());
        sendPlain(ackMsg.toString());
      }
    };
  }

  /**
   * Synthesize ack.
   *
   * @param message the message
   * @param ack     the ack
   */
  private void synthesizeAck(IOMessage message, IOAcknowledge ack) {
    if (ack != null) {
      int id = nextId++;
      acknowledge.put(id, ack);
      message.setId(id + "+");
    }
  }

  /**
   * Instantiates a new IOConnection.
   *
   * @param url    the URL
   * @param socket the socket
   */
  private IOConnection(String url, SocketIO socket) {
    try {
      this.url = new URL(url);
      this.urlStr = url;
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    firstSocket = socket;
    sockets.put(socket.getNamespace(), socket);
    new ConnectThread().start();
  }

  /**
   * Populates an error to the connected {@link IOCallback}s and shuts down.
   *
   * @param e an exception
   */
  protected void error(SocketIOException e) {
    for (SocketIO socket : sockets.values()) {
      socket.getCallback().onError(e);
    }
    cleanup();
  }

  /**
   * Connects a socket to the IOConnection.
   *
   * @param socket the socket to be connected
   *
   * @return True if the socket was successfully registered
   */
  public boolean register(SocketIO socket) {
    String namespace = socket.getNamespace();
    if (sockets.containsKey(namespace)) {
      return false;
    } else {
      sockets.put(namespace, socket);
      IOMessage connect = new IOMessage(IOMessage.TYPE_CONNECT,
        socket.getNamespace(), "");
      sendPlain(connect.toString());
      return true;
    }
  }

  /**
   * Cleanup. IOConnection is not usable after this calling this.
   */
  private void cleanup() {
    setState(IOState.STATE_INVALID);
    if (transport != null)
      transport.disconnect();
    sockets.clear();
    connections.remove(urlStr);
    log.trace("Cleanup");
    backgroundTimer.cancel();
  }

  /**
   * Disconnect a socket from the IOConnection. Shuts down this IOConnection
   * if no further connections are available for this IOConnection.
   *
   * @param socket the socket to be shut down
   */
  public void unregister(SocketIO socket) {
    sendPlain("0::" + socket.getNamespace());
    sockets.remove(socket.getNamespace());
    socket.getCallback().onDisconnect();

    if (sockets.size() == 0) {
      cleanup();
    }
  }

  /**
   * Sends a plain message to the {@link IOTransport}.
   *
   * @param text the Text to be send.
   */
  private void sendPlain(String text) {
    synchronized (outputBuffer) {
      if (getState() == STATE_READY)
        try {
          log.trace("> " + text);
          transport.send(text);
        } catch (Exception e) {
          log.trace("IOEx: saving");
          outputBuffer.add(text);
        }
      else {
        outputBuffer.add(text);
      }
    }
  }

  /**
   * Invalidates an {@link IOTransport}, used for forced reconnecting.
   */
  private void invalidateTransport() {
    transport.invalidate();
    transport = null;
  }

  /**
   * Reset timeout.
   */
  private void resetTimeout() {
    if (heartbeatTimeoutTask != null) {
      heartbeatTimeoutTask.cancel();
    }
    heartbeatTimeoutTask = new HeartbeatTimeoutTask();
    backgroundTimer.schedule(heartbeatTimeoutTask, closingTimeout
      + heartbeatTimeout);
  }

  /**
   * Transport connected.
   *
   * {@link IOTransport} calls this when a connection is established.
   */
  public void transportConnected() {
    log.trace("Transport connected...");
    setState(STATE_READY);
    if (reconnectTask != null) {
      reconnectTask.cancel();
      reconnectTask = null;
    }
    resetTimeout();
    synchronized (outputBuffer) {
      if (transport.canSendBulk()) {
        ConcurrentLinkedQueue<String> outputBuffer = this.outputBuffer;
        this.outputBuffer = new ConcurrentLinkedQueue<String>();
        try {
          String[] texts = outputBuffer.toArray(new String[outputBuffer.size()]);
          // DEBUG
          log.trace("Bulk start:");
          for (String text : texts) {
            log.trace("> " + text);
          }
          log.trace("Bulk end");
          // DEBUG END
          transport.sendBulk(texts);
        } catch (IOException e) {
          this.outputBuffer = outputBuffer;
        }
      } else {
        // Send any text in the output buffer
        String text;
        while ((text = outputBuffer.poll()) != null) {
          sendPlain(text);
        }
      }
      // All data sent, so no keep alive in the output buffer
      this.keepAliveInQueue = false;
    }
  }

  /**
   * Transport disconnected.
   *
   * {@link IOTransport} calls this when a connection has been shut down.
   */
  public void transportDisconnected() {
    this.lastException = null;
    reconnect();
  }

  /**
   * Transport error.
   *
   * @param error the error {@link IOTransport} calls this, when an exception
   *              has occured and the transport is not usable anymore.
   */
  public void transportError(Exception error) {
    this.lastException = error;
    setState(IOState.STATE_INTERRUPTED);
    reconnect();
  }

  /**
   * Incoming transport message 
   *
   * @param text The incoming message text 
   */
  public void transportMessage(String text) {
    String trace = "< " + text;
    IOMessage message;
    try {
      message = new IOMessage(text);
    } catch (Exception e) {
      error(new SocketIOException("Garbage from server: " + text, e));
      return;
    }
    resetTimeout();
    switch (message.getType()) {
      case IOMessage.TYPE_DISCONNECT:
        log.trace("{} [DISCONNECT]",trace);
        if ("".equals(message.getEndpoint())) {
          for (SocketIO socket : sockets.values()) {
            socket.getCallback().onDisconnect();
          }
        } else
          try {
            findCallback(message).onDisconnect();
          } catch (Exception e) {
            error(new SocketIOException(
              "Exception was thrown in onDisconnect()", e));
          }
        break;
      case IOMessage.TYPE_CONNECT:
        log.trace("{} [CONNECT]",trace);
        try {
          if (firstSocket != null && message.getEndpoint().equals("")) {
            if (firstSocket.getNamespace().equals("")) {
              firstSocket.getCallback().onConnect();
            } else {
              IOMessage connect = new IOMessage(
                IOMessage.TYPE_CONNECT,
                firstSocket.getNamespace(), "");
              sendPlain(connect.toString());
            }
          } else {
            findCallback(message).onConnect();
          }
          firstSocket = null;
        } catch (Exception e) {
          error(new SocketIOException(
            "Exception was thrown in onConnect()", e));
        }
        break;
      case IOMessage.TYPE_HEARTBEAT:
        log.trace("{} [HEARTBEAT]",trace);
        sendPlain("2::");
        break;
      case IOMessage.TYPE_MESSAGE:
        try {
          findCallback(message).onMessage(message.getData(),
            remoteAcknowledge(message));
        } catch (Exception e) {
          error(new SocketIOException(
            "Exception was thrown in onMessage(String).\n"
              + "Message was: " + message.toString(), e));
        }
        break;
      case IOMessage.TYPE_JSON_MESSAGE:
        log.trace("{} [JSON_MESSAGE]",trace);
        try {
          JSONObject obj = null;
          String data = message.getData();
          if (data.trim().equals("null") == false)
            obj = new JSONObject(data);
          try {
            findCallback(message).onMessage(obj,
              remoteAcknowledge(message));
          } catch (Exception e) {
            error(new SocketIOException(
              "Exception was thrown in onMessage(JSONObject)\n"
                + "Message was: " + message.toString(), e));
          }
        } catch (JSONException e) {
          warning("Malformed JSON received");
        }
        break;
      case IOMessage.TYPE_EVENT:
        log.trace("{} [EVENT]",trace);
        try {
          JSONObject event = new JSONObject(message.getData());
          JSONArray args = event.getJSONArray("args");
          Object[] argsArray = new Object[args.length()];
          for (int i = 0; i < args.length(); i++) {
            if (args.isNull(i) == false)
              argsArray[i] = args.get(i);
          }
          String eventName = event.getString("name");
          try {
            findCallback(message).on(eventName,
              remoteAcknowledge(message), argsArray);
          } catch (Exception e) {
            error(new SocketIOException(
              "Exception was thrown in on(String, JSONObject[])"
                + "Message was: " + message.toString(), e));
          }
        } catch (JSONException e) {
          warning("Malformed JSON received");
        }
        break;

      case IOMessage.TYPE_ACK:
        log.trace("{} [ACK]",trace);
        String[] data = message.getData().split("\\+", 2);
        if (data.length == 2) {
          try {
            int id = Integer.parseInt(data[0]);
            IOAcknowledge ack = acknowledge.get(id);
            if (ack == null)
              warning("Received unknown ack packet");
            else {
              JSONArray array = new JSONArray(data[1]);
              Object[] args = new Object[array.length()];
              for (int i = 0; i < args.length; i++) {
                args[i] = array.get(i);
              }
              ack.ack(args);
            }
          } catch (NumberFormatException e) {
            warning("Received malformed Acknowledge! This is potentially filling up the acknowledges!");
          } catch (JSONException e) {
            warning("Received malformed Acknowledge data!");
          }
        } else if (data.length == 1) {
          sendPlain("6:::" + data[0]);
        }
        break;
      case IOMessage.TYPE_ERROR:
        log.trace("{} [ERROR]",trace);
        if ("".equals(message.getEndpoint())) {
          // Inform all sockets that a connection error has occurred
          for (SocketIO socket : sockets.values()) {
            socket.getCallback().onError(new SocketIOException(message.getData()));
          }
        } else {
          // Inform the relevant socket that a connection error has occurred
          findCallback(message).onError(new SocketIOException(message.getData()));
        }
        // Check for a disconnect
        if (message.getData().endsWith("+0")) {
          // We are advised to disconnect
          cleanup();
        }
        break;
      case IOMessage.TYPE_NO_OP:
        log.trace("{} [NO OP]",trace);
        break;
      default:
        warning("Unknown IOMessage type received: " + message.getType());
        break;
    }
  }

  /**
   * forces a reconnect. This had become useful on some android devices which
   * do not shut down TCP-connections when switching from HSDPA to Wifi
   */
  public void reconnect() {
    synchronized (this) {
      if (getState() != IOState.STATE_INVALID) {
        if (transport != null)
          invalidateTransport();
        transport = null;
        setState(IOState.STATE_INTERRUPTED);
        if (reconnectTask != null) {
          reconnectTask.cancel();
        }
        reconnectTask = new ReconnectTask();
        backgroundTimer.schedule(reconnectTask, 1000);
      }
    }
  }

  /**
   * finds the corresponding callback object to an incoming message. Returns a
   * dummy callback if no corresponding callback can be found
   *
   * @param message the message
   *
   * @return the iO callback
   */
  private IOCallback findCallback(IOMessage message) {
    SocketIO socket = sockets.get(message.getEndpoint());
    if (socket == null) {
      warning("Cannot find socket for '" + message.getEndpoint() + "'");
      return DUMMY_CALLBACK;
    }
    return socket.getCallback();
  }

  /**
   * Handles a non-fatal error.
   *
   * @param message the message
   */
  private void warning(String message) {
    System.err.println(message);
  }

  /**
   * Returns the session id. This should be called from the
   *
   * @return the session id {@link IOTransport} to connect to the right
   *         Session.
   */
  public String getSessionId() {
    return sessionId;
  }

  /**
   * A dummy callback used when IOConnection receives a unexpected message.
   */
  final static public IOCallback DUMMY_CALLBACK = new IOCallback() {
    private void out(String msg) {
      log.trace("DUMMY CALLBACK: " + msg);
    }

    @Override
    public void onDisconnect() {
      out("Disconnect");
    }

    @Override
    public void onConnect() {
      out("Connect");
    }

    @Override
    public void onMessage(String data, IOAcknowledge ack) {
      out("Message:\n" + data + "\n-------------");
    }

    @Override
    public void onMessage(JSONObject json, IOAcknowledge ack) {
      out("Message:\n" + json.toString() + "\n-------------");
    }

    @Override
    public void on(String event, IOAcknowledge ack, Object... args) {
      out("Event '" + event + "':\n");
      for (Object arg : args)
        out(arg.toString());
      out("\n-------------");
    }

    @Override
    public void onError(SocketIOException socketIOException) {
      out("Error");
      throw new RuntimeException(socketIOException);
    }

  };

  /**
   * sends a String message from {@link SocketIO} to the {@link IOTransport}.
   *
   * @param socket the socket
   * @param ack    acknowledge package which can be called from the server
   * @param text   the text
   */
  public void send(SocketIO socket, IOAcknowledge ack, String text) {
    IOMessage message = new IOMessage(IOMessage.TYPE_MESSAGE,
      socket.getNamespace(), text);
    synthesizeAck(message, ack);
    sendPlain(message.toString());
  }

  /**
   * sends a JSON message from {@link SocketIO} to the {@link IOTransport}.
   *
   * @param socket the socket
   * @param ack    acknowledge package which can be called from the server
   * @param json   the json
   */
  public void send(SocketIO socket, IOAcknowledge ack, JSONObject json) {
    IOMessage message = new IOMessage(IOMessage.TYPE_JSON_MESSAGE,
      socket.getNamespace(), json.toString());
    synthesizeAck(message, ack);
    sendPlain(message.toString());
  }

  /**
   * emits an event from {@link SocketIO} to the {@link IOTransport}.
   *
   * @param socket the socket
   * @param event  the event
   * @param ack    acknowledge package which can be called from the server
   * @param args   the arguments to be send
   */
  public void emit(SocketIO socket, String event, IOAcknowledge ack,
                   Object... args) {
    try {
      JSONObject json = new JSONObject().put("name", event).put("args",
        new JSONArray(Arrays.asList(args)));
      IOMessage message = new IOMessage(IOMessage.TYPE_EVENT,
        socket.getNamespace(), json.toString());
      synthesizeAck(message, ack);
      sendPlain(message.toString());
    } catch (JSONException e) {
      error(new SocketIOException(
        "Error while emitting an event. Make sure you only try to send arguments, which can be serialized into JSON."));
    }

  }

  /**
   * Checks if IOConnection is currently connected.
   *
   * @return true, if is connected
   */
  public boolean isConnected() {
    return getState() == STATE_READY;
  }

  /**
   * Gets the current state of this IOConnection
   *
   * @return current state
   */
  private synchronized IOState getState() {
    return state;
  }

  /**
   * Sets the current state of this IOConnection
   *
   * @param state The state
   */
  private synchronized void setState(IOState state) {
    this.state = state;
    log.trace("State changed to " + state.name());
  }

  /**
   * gets the currently used transport
   *
   * @return currently used transport
   */
  public IOTransport getTransport() {
    return transport;
  }
}
