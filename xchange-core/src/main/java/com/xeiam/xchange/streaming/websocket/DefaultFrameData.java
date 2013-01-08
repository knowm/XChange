package com.xeiam.xchange.streaming.websocket;

import java.nio.ByteBuffer;

import com.xeiam.xchange.streaming.websocket.exceptions.InvalidDataException;
import com.xeiam.xchange.streaming.websocket.exceptions.InvalidFrameException;
import com.xeiam.xchange.utils.CharsetUtils;

public class DefaultFrameData implements FrameBuilder {

  protected static byte[] emptyArray = {};
  protected boolean fin;
  protected OpCode opCode;
  private ByteBuffer unmaskedPayload;
  protected boolean transferMasked;

  public DefaultFrameData() {

  }

  public DefaultFrameData(OpCode op) {

    this.opCode = op;
    unmaskedPayload = ByteBuffer.wrap(emptyArray);
  }

  public DefaultFrameData(FrameData f) {

    fin = f.isFin();
    opCode = f.getOpCode();
    unmaskedPayload = ByteBuffer.wrap(f.getPayloadData());
    transferMasked = f.isTransferMasked();
  }

  @Override
  public boolean isFin() {

    return fin;
  }

  @Override
  public OpCode getOpCode() {

    return opCode;
  }

  @Override
  public boolean isTransferMasked() {

    return transferMasked;
  }

  @Override
  public byte[] getPayloadData() {

    return unmaskedPayload.array();
  }

  @Override
  public void setFin(boolean fin) {

    this.fin = fin;
  }

  @Override
  public void setOpCode(OpCode opCode) {

    this.opCode = opCode;
  }

  @Override
  public void setPayload(byte[] payload) throws InvalidDataException {

    unmaskedPayload = ByteBuffer.wrap(payload);
  }

  @Override
  public void setTransferMasked(boolean transferMasked) {

    this.transferMasked = transferMasked;
  }

  @Override
  public void append(FrameData nextFrame) throws InvalidFrameException {

    if (unmaskedPayload == null) {
      unmaskedPayload = ByteBuffer.wrap(nextFrame.getPayloadData());
    } else {
      // TODO might be inefficient. Consider a global buffer pool
      ByteBuffer tmp = ByteBuffer.allocate(nextFrame.getPayloadData().length + unmaskedPayload.capacity());
      tmp.put(unmaskedPayload.array());
      tmp.put(nextFrame.getPayloadData());
      unmaskedPayload = tmp;
    }
    fin = nextFrame.isFin();
  }

  @Override
  public String toString() {

    return "FrameData{ opCode:" + getOpCode() + ", fin:" + isFin() + ", payloadlength:" + unmaskedPayload.limit() + ", payload:" + CharsetUtils.toByteArrayUtf8(new String(unmaskedPayload.array()))
        + "}";
  }

}
