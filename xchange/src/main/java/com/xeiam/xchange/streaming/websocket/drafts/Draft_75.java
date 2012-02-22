package com.xeiam.xchange.streaming.websocket.drafts;

import com.xeiam.xchange.streaming.websocket.*;
import com.xeiam.xchange.streaming.websocket.FrameData.OpCode;
import com.xeiam.xchange.streaming.websocket.exceptions.InvalidDataException;
import com.xeiam.xchange.streaming.websocket.exceptions.InvalidHandshakeException;
import com.xeiam.xchange.streaming.websocket.exceptions.NotSendableException;
import com.xeiam.xchange.utils.CharsetUtils;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Draft_75 extends Draft {

  /**
   * The byte representing CR, or Carriage Return, or \r
   */
  public static final byte CR = (byte) 0x0D;
  /**
   * The byte representing LF, or Line Feed, or \n
   */
  public static final byte LF = (byte) 0x0A;
  /**
   * The byte representing the beginning of a WebSocket text frame.
   */
  public static final byte START_OF_FRAME = (byte) 0x00;
  /**
   * The byte representing the end of a WebSocket text frame.
   */
  public static final byte END_OF_FRAME = (byte) 0xFF;

  private boolean readingState = false;
  private boolean inframe = false;

  private ByteBuffer currentFrame;

  @Override
  public HandshakeState acceptHandshakeAsClient(HandshakeData request, HandshakeData response) {
    return request.getFieldValue("WebSocket-Origin").equals(response.getFieldValue("Origin")) && basicAccept(response) ? HandshakeState.MATCHED : HandshakeState.NOT_MATCHED;
  }

  @Override
  public HandshakeState acceptHandshakeAsServer(HandshakeData handshakeData) {
    if (handshakeData.hasFieldValue("Origin") && basicAccept(handshakeData)) {
      return HandshakeState.MATCHED;
    }
    return HandshakeState.NOT_MATCHED;
  }

  @Override
  public ByteBuffer createBinaryFrame(FrameData frameData) {
    byte[] pay = frameData.getPayloadData();
    ByteBuffer b = ByteBuffer.allocate(pay.length + 2);
    b.put(START_OF_FRAME);
    b.put(pay);
    b.put(END_OF_FRAME);
    b.flip();
    return b;
  }

  @Override
  public List<FrameData> createFrames(byte[] binary, boolean mask) {
    throw new RuntimeException("not yet implemented");
  }

  @Override
  public List<FrameData> createFrames(String text, boolean mask) {
    FrameBuilder frame = new DefaultFrameData();
    try {
      frame.setPayload(CharsetUtils.utf8Bytes(text));
    } catch (InvalidDataException e) {
      throw new NotSendableException(e);
    }
    frame.setFin(true);
    frame.setOpCode(OpCode.TEXT);
    frame.setTransferMasked(mask);
    return Collections.singletonList((FrameData) frame);
  }

  @Override
  public HandshakeBuilder postProcessHandshakeRequestAsClient(HandshakeBuilder request) throws InvalidHandshakeException {
    request.put("Upgrade", "WebSocket");
    request.put("Connection", "Upgrade");
    if (!request.hasFieldValue("Origin")) {
      request.put("Origin", "random" + new Random().nextInt());
    }

    return request;
  }

  @Override
  public HandshakeBuilder postProcessHandshakeResponseAsServer(HandshakeData request, HandshakeBuilder response) throws InvalidHandshakeException {
    response.setHttpStatusMessage("Web Socket Protocol Handshake");
    response.put("Upgrade", "WebSocket");
    response.put("Connection", request.getFieldValue("Connection")); // to respond to a Connection keep alive
    response.put("WebSocket-Origin", request.getFieldValue("Origin"));
    String location = "ws://" + request.getFieldValue("Host") + request.getResourceDescriptor();
    response.put("WebSocket-Location", location);
    // TODO handle Sec-WebSocket-Protocol and Set-Cookie
    return response;
  }

  @Override
  public List<FrameData> translateFrame(ByteBuffer buffer) throws InvalidDataException {
    List<FrameData> frames = new LinkedList<FrameData>();
    while (buffer.hasRemaining()) {
      byte newestByte = buffer.get();
      if (newestByte == START_OF_FRAME && !readingState) { // Beginning of Frame
        this.currentFrame = null;
        readingState = true;
      } else if (newestByte == END_OF_FRAME && readingState) { // End of Frame
        // currentFrame will be null if END_OF_FRAME was send directly after
        // START_OF_FRAME, thus we will send 'null' as the sent message.
        if (this.currentFrame != null) {
          DefaultFrameData curframe = new DefaultFrameData();
          curframe.setPayload(currentFrame.array());
          curframe.setFin(true);
          curframe.setOpCode(inframe ? OpCode.CONTINUOUS : OpCode.TEXT);
          frames.add(curframe);
        }
        readingState = false;
        inframe = false;
      } else { // Regular frame data, add to current frame buffer //TODO This code is very expensive and slow
        ByteBuffer frame = ByteBuffer.allocate(checkAlloc((this.currentFrame != null ? this.currentFrame.capacity() : 0) + 1));
        if (this.currentFrame != null) {
          this.currentFrame.rewind();
          frame.put(this.currentFrame);
        }
        frame.put(newestByte);
        this.currentFrame = frame;
      }
    }
    if (readingState) {
      DefaultFrameData curframe = new DefaultFrameData();
      curframe.setPayload(currentFrame.array());
      curframe.setFin(false);
      curframe.setOpCode(inframe ? OpCode.CONTINUOUS : OpCode.TEXT);
      inframe = true;
      frames.add(curframe);
    }
    return frames;
  }

  @Override
  public void reset() {
    readingState = false;
    this.currentFrame = null;
  }

}
