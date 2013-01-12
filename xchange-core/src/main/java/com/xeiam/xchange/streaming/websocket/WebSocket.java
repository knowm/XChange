package com.xeiam.xchange.streaming.websocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.streaming.websocket.Draft.HandshakeState;
import com.xeiam.xchange.streaming.websocket.FrameData.OpCode;
import com.xeiam.xchange.streaming.websocket.drafts.Draft_10;
import com.xeiam.xchange.streaming.websocket.drafts.Draft_17;
import com.xeiam.xchange.streaming.websocket.drafts.Draft_75;
import com.xeiam.xchange.streaming.websocket.drafts.Draft_76;
import com.xeiam.xchange.streaming.websocket.exceptions.IncompleteHandshakeException;
import com.xeiam.xchange.streaming.websocket.exceptions.InvalidDataException;
import com.xeiam.xchange.streaming.websocket.exceptions.InvalidFrameException;
import com.xeiam.xchange.streaming.websocket.exceptions.InvalidHandshakeException;
import com.xeiam.xchange.utils.CharsetUtils;

/**
 * Represents one end (client or server) of a single WebSocket connection. Takes care of the "handshake" phase, then allows for easy sending of text frames, and recieving frames through an event-based
 * model. This is an inner class, used by <tt>WebSocketClient</tt> and <tt>WebSocketServer</tt>, and should never need to be instantiated directly by your code. However, instances are exposed in
 * <tt>WebSocketServer</tt> through the <i>onClientOpen</i>, <i>onClientClose</i>, <i>onClientMessage</i> callbacks.
 * 
 * @author Nathan Rajlich
 */
public final class WebSocket {

  private final Logger log = LoggerFactory.getLogger(WebSocket.class);

  public enum Role {
    CLIENT, SERVER
  }

  /**
   * The default port of WebSockets, as defined in the spec. If the default constructor is used, DEFAULT_PORT will be the port the WebSocketServer is binded to. Note that ports under 1024 usually
   * require root permissions.
   */
  public static final int DEFAULT_PORT = 80;

  public static/* final */boolean DEBUG = false; // must be final in the future in order to take advantage of VM optimization

  /**
   * Internally used to determine whether to receive data as part of the remote handshake, or as part of a text frame.
   */
  private boolean handshakeComplete = false;

  private boolean closeHandshakeSent = false;
  private boolean connectionClosed = false;
  /**
   * The listener to notify of WebSocket events.
   */
  private WebSocketListener wsl;
  /**
   * Buffer where data is read to from the socket
   */
  private ByteBuffer socketBuffer;
  /**
   * Queue of buffers that need to be sent to the client.
   */
  private BlockingQueue<ByteBuffer> bufferQueue;

  private Draft draft = null;

  private Role role;

  private FrameData currentframe;

  private HandshakeData handshakerequest = null;

  public List<Draft> known_drafts;

  private static final byte[] FLASH_POLICY_REQUEST = CharsetUtils.toByteArrayUtf8("<policy-file-request/>");

  private int flash_policy_index = 0;

  private SocketChannel socketChannel;

  /**
   * Used in {@link WebSocketServer} and {@link WebSocketClient}.
   * 
   * @param draft Which draft to use
   * @param socketChannel The <tt>SocketChannel</tt> instance to read and write to. The channel should already be registered with a Selector before construction of this object.
   * @param listener The {@link WebSocketListener} to notify of events when they occur.
   */
  public WebSocket(WebSocketListener listener, Draft draft, SocketChannel socketChannel) {

    init(listener, draft, socketChannel);
  }

  public WebSocket(WebSocketListener listener, List<Draft> drafts, SocketChannel socketChannel) {

    init(listener, null, socketChannel);
    this.role = Role.SERVER;
    if (known_drafts == null || known_drafts.isEmpty()) {
      known_drafts = new ArrayList<Draft>(1);
      known_drafts.add(new Draft_17());
      known_drafts.add(new Draft_10());
      known_drafts.add(new Draft_76());
      known_drafts.add(new Draft_75());
    } else {
      known_drafts = drafts;
    }
  }

  private void init(WebSocketListener listener, Draft draft, SocketChannel sockchannel) {

    this.socketChannel = sockchannel;
    this.bufferQueue = new LinkedBlockingQueue<ByteBuffer>(10);
    this.socketBuffer = ByteBuffer.allocate(65558);
    socketBuffer.flip();
    this.wsl = listener;
    this.role = Role.CLIENT;
    this.draft = draft;
  }

  /**
   * Should be called when a Selector has a key that is writable for this WebSocket's SocketChannel connection.
   * 
   * @throws java.io.IOException When socket related I/O errors occur.
   */
  void handleRead() throws IOException {

    if (!socketBuffer.hasRemaining()) {
      socketBuffer.rewind();
      socketBuffer.limit(socketBuffer.capacity());
      if (socketChannel.read(socketBuffer) == -1) {
        close(CloseFrame.ABNROMAL_CLOSE);
      }

      socketBuffer.flip();
    }

    if (socketBuffer.hasRemaining()) {
      if (DEBUG) {
        log.trace("process(" + socketBuffer.remaining() + "): {"
            + (socketBuffer.remaining() > 1000 ? "too big to display" : new String(socketBuffer.array(), socketBuffer.position(), socketBuffer.remaining())) + "}");
      }
      if (!handshakeComplete) {
        HandshakeData handshake;
        HandshakeState handshakeState;

        handshakeState = isFlashEdgeCase(socketBuffer);
        if (handshakeState == HandshakeState.MATCHED) {
          channelWriteDirect(ByteBuffer.wrap(CharsetUtils.toByteArrayUtf8(wsl.getFlashPolicy(this))));
          return;
        }
        socketBuffer.mark();
        try {
          if (role == Role.SERVER) {
            if (draft == null) {
              for (Draft d : known_drafts) {
                try {
                  d.setParseMode(role);
                  socketBuffer.reset();
                  handshake = d.translateHandshake(socketBuffer);
                  handshakeState = d.acceptHandshakeAsServer(handshake);
                  if (handshakeState == HandshakeState.MATCHED) {
                    HandshakeBuilder response = wsl.onHandshakeRecievedAsServer(this, d, handshake);
                    writeDirect(d.createHandshake(d.postProcessHandshakeResponseAsServer(handshake, response), role));
                    draft = d;
                    open(handshake);
                    handleRead();
                    return;
                  } else if (handshakeState == HandshakeState.MATCHING) {
                    if (draft != null) {
                      throw new InvalidHandshakeException("multiple drafts matching");
                    }
                    draft = d;
                  }
                } catch (InvalidHandshakeException e) {
                  // go on with an other draft
                } catch (IncompleteHandshakeException e) {
                  if (socketBuffer.limit() == socketBuffer.capacity()) {
                    close(CloseFrame.TOOBIG, "handshake is too big");
                  }
                  // read more bytes for the handshake
                  socketBuffer.position(socketBuffer.limit());
                  socketBuffer.limit(socketBuffer.capacity());
                  return;
                }
              }
              if (draft == null) {
                close(CloseFrame.PROTOCOL_ERROR, "no draft matches");
              }
              return;
            } else {
              // special case for multiple step handshakes
              handshake = draft.translateHandshake(socketBuffer);
              handshakeState = draft.acceptHandshakeAsServer(handshake);

              if (handshakeState == HandshakeState.MATCHED) {
                open(handshake);
                handleRead();
              } else if (handshakeState != HandshakeState.MATCHING) {
                close(CloseFrame.PROTOCOL_ERROR, "the handshake finally did not match");
              }
              return;
            }
          } else if (role == Role.CLIENT) {
            draft.setParseMode(role);
            handshake = draft.translateHandshake(socketBuffer);
            handshakeState = draft.acceptHandshakeAsClient(handshakerequest, handshake);
            if (handshakeState == HandshakeState.MATCHED) {
              open(handshake);
              handleRead();
            } else if (handshakeState == HandshakeState.MATCHING) {
              return;
            } else {
              close(CloseFrame.PROTOCOL_ERROR, "draft " + draft + " refuses handshake");
            }
          }
        } catch (InvalidHandshakeException e) {
          close(e);
        }
      } else {
        // Receiving frames
        List<FrameData> frames;
        try {
          frames = draft.translateFrame(socketBuffer);
          for (FrameData f : frames) {
            if (DEBUG) {
              log.trace("matched frame: " + f);
            }
            OpCode curop = f.getOpCode();
            if (curop == OpCode.CLOSING) {
              int code = CloseFrame.NOCODE;
              String reason = "";
              if (f instanceof CloseFrame) {
                CloseFrame cf = (CloseFrame) f;
                code = cf.getCloseCode();
                reason = cf.getMessage();
              }
              if (closeHandshakeSent) {
                // complete the close handshake by disconnecting
                closeConnection(code, reason, true);
              } else {
                // echo close handshake
                close(code, reason);
                closeConnection(code, reason, false);
              }
              continue;
            } else if (curop == OpCode.PING) {
              wsl.onPing(this, f);
              continue;
            } else if (curop == OpCode.PONG) {
              wsl.onPong(this, f);
              continue;
            } else {
              // process non control frames
              if (currentframe == null) {
                if (f.getOpCode() == OpCode.CONTINUOUS) {
                  throw new InvalidFrameException("unexpected continious frame");
                } else if (f.isFin()) {
                  // receive normal onframe message
                  deliverMessage(f);
                } else {
                  // remember the frame whose payload is about to be continued
                  currentframe = f;
                }
              } else if (f.getOpCode() == OpCode.CONTINUOUS) {
                currentframe.append(f);
                if (f.isFin()) {
                  deliverMessage(currentframe);
                  currentframe = null;
                }
              } else {
                throw new InvalidDataException(CloseFrame.PROTOCOL_ERROR, "non control or continious frame expected");
              }
            }
          }
        } catch (InvalidDataException e1) {
          wsl.onError(this, e1);
          close(e1);
          return;
        }
      }
    }
  }

  /**
   * sends the closing handshake. may be send in response to an other handshake.
   * 
   * @param code The error code
   * @param message The message
   */
  public void close(int code, String message) {

    try {
      closeDirect(code, message);
    } catch (IOException e) {
      closeConnection(CloseFrame.ABNROMAL_CLOSE, true);
    }
  }

  public void closeDirect(int code, String message) throws IOException {

    if (!closeHandshakeSent) {
      if (handshakeComplete) {
        flush();
        try {
          sendFrameDirect(new CloseFrameBuilder(code, message));
        } catch (InvalidDataException e) {
          wsl.onError(this, e);
          closeConnection(CloseFrame.ABNROMAL_CLOSE, "generated frame is invalid", false);
        }
      } else {
        closeConnection(CloseFrame.NEVERCONNECTED, false);
      }
      if (code == CloseFrame.PROTOCOL_ERROR) {
        closeConnection(code, false);
      }
      closeHandshakeSent = true;
      return;
    }
  }

  /**
   * closes the socket no matter if the closing handshake completed. Does not send any not yet written data before closing. Calling this method more than once will have no effect.
   * 
   * @param code The error code
   * @param message The message
   * @param remote Indicates who "generated" <code>code</code>.<br>
   *          <code>true</code> means that this endpoint received the <code>code</code> from the other endpoint.<br>
   *          false means this endpoint decided to send the given code,<br>
   *          <code>remote</code> may also be true if this endpoint started the closing handshake since the other endpoint may not simply echo the <code>code</code> but close the connection the same
   *          time this endpoint does do but with an other <code>code</code>. <br>
   */
  public void closeConnection(int code, String message, boolean remote) {

    if (connectionClosed) {
      return;
    }
    connectionClosed = true;
    try {
      socketChannel.close();
    } catch (IOException e) {
      wsl.onError(this, e);
    }
    this.wsl.onClose(this, code, message, remote);
    if (draft != null) {
      draft.reset();
    }
    currentframe = null;
    handshakerequest = null;
  }

  public void closeConnection(int code, boolean remote) {

    closeConnection(code, "", remote);
  }

  public void close(int code) {

    close(code, "");
  }

  public void close(InvalidDataException e) {

    close(e.getCloseCode(), e.getMessage());
  }

  /**
   * @param text The text to send
   * @throws InterruptedException If something goes wrong
   */
  public void send(String text) throws InterruptedException {

    if (text == null) {
      throw new IllegalArgumentException("Cannot send 'null' data to a WebSocket.");
    }
    send(draft.createFrames(text, role == Role.CLIENT));
  }

  public void send(byte[] bytes) throws IllegalArgumentException, NotYetConnectedException, InterruptedException {

    if (bytes == null) {
      throw new IllegalArgumentException("Cannot send 'null' data to a WebSocket.");
    }
    send(draft.createFrames(bytes, role == Role.CLIENT));
  }

  private void send(Collection<FrameData> frames) throws InterruptedException {

    if (!this.handshakeComplete) {
      throw new NotYetConnectedException();
    }
    for (FrameData f : frames) {
      sendFrame(f);
    }
  }

  public void sendFrame(FrameData frameData) throws InterruptedException {

    if (DEBUG) {
      log.trace("send frame: " + frameData);
    }
    channelWrite(draft.createBinaryFrame(frameData));
  }

  private void sendFrameDirect(FrameData frameData) throws IOException {

    if (DEBUG) {
      log.trace("send frame: " + frameData);
    }
    channelWriteDirect(draft.createBinaryFrame(frameData));
  }

  boolean hasBufferedData() {

    return !this.bufferQueue.isEmpty();
  }

  /**
   * Flushes the socket write buffer
   * 
   * @throws java.io.IOException If something goes wrong
   */
  public void flush() throws IOException {

    ByteBuffer buffer = this.bufferQueue.peek();
    while (buffer != null) {
      socketChannel.write(buffer);
      if (buffer.remaining() > 0) {
        continue;
      } else {
        this.bufferQueue.poll(); // Buffer finished. Remove it.
        buffer = this.bufferQueue.peek();
      }
    }
  }

  public HandshakeState isFlashEdgeCase(ByteBuffer request) {

    if (flash_policy_index >= FLASH_POLICY_REQUEST.length) {
      return HandshakeState.NOT_MATCHED;
    }
    request.mark();
    for (; request.hasRemaining() && flash_policy_index < FLASH_POLICY_REQUEST.length; flash_policy_index++) {
      if (FLASH_POLICY_REQUEST[flash_policy_index] != request.get()) {
        request.reset();
        return HandshakeState.NOT_MATCHED;
      }
    }
    return request.remaining() >= FLASH_POLICY_REQUEST.length ? HandshakeState.MATCHED : HandshakeState.MATCHING;
  }

  public void startHandshake(HandshakeBuilder handshakedata) throws InvalidHandshakeException, InterruptedException {

    if (handshakeComplete) {
      throw new IllegalStateException("Handshake has allready been sent.");
    }
    this.handshakerequest = handshakedata;
    channelWrite(draft.createHandshake(draft.postProcessHandshakeRequestAsClient(handshakedata), role));
  }

  private void channelWrite(ByteBuffer buf) throws InterruptedException {

    if (DEBUG) {
      log.trace("write(" + buf.limit() + "): {" + (buf.limit() > 1000 ? "too big to display" : new String(buf.array())) + "}");
    }
    buf.rewind();
    bufferQueue.put(buf);
    wsl.onWriteDemand(this);
  }

  private void channelWrite(List<ByteBuffer> bufs) throws InterruptedException {

    for (ByteBuffer b : bufs) {
      channelWrite(b);
    }
  }

  private void channelWriteDirect(ByteBuffer buf) throws IOException {

    while (buf.hasRemaining()) {
      socketChannel.write(buf);
    }
  }

  private void writeDirect(List<ByteBuffer> bufs) throws IOException {

    for (ByteBuffer b : bufs) {
      channelWriteDirect(b);
    }
  }

  private void deliverMessage(FrameData d) throws InvalidDataException {

    if (d.getOpCode() == OpCode.TEXT) {
      wsl.onMessage(this, CharsetUtils.toStringUtf8(d.getPayloadData()));
    } else if (d.getOpCode() == OpCode.BINARY) {
      wsl.onMessage(this, d.getPayloadData());
    } else {
      if (DEBUG) {
        log.trace("Ignoring frame:" + d.toString());
      }
    }
  }

  private void open(HandshakeData d) throws IOException {

    if (DEBUG) {
      log.trace("open using draft: " + draft.getClass().getSimpleName());
    }
    handshakeComplete = true;
    wsl.onOpen(this, d);
  }

  public InetSocketAddress getRemoteSocketAddress() {

    return (InetSocketAddress) socketChannel.socket().getRemoteSocketAddress();
  }

  public InetSocketAddress getLocalSocketAddress() {

    return (InetSocketAddress) socketChannel.socket().getLocalSocketAddress();
  }

  public boolean isClosed() {

    return connectionClosed;
  }

}
