package com.xeiam.xchange.streaming.websocket;

import com.xeiam.xchange.streaming.websocket.exeptions.InvalidDataException;
import com.xeiam.xchange.streaming.websocket.exeptions.InvalidFrameException;
import com.xeiam.xchange.utils.CharsetUtils;

import java.nio.ByteBuffer;

public class DefaultFrameData implements FrameBuilder {
  protected static byte[] emptyArray = {};
  protected boolean fin;
  protected Opcode opCode;
  private ByteBuffer unmaskedPayload;
  protected boolean transferMasked;

  public DefaultFrameData() {
  }

  public DefaultFrameData(Opcode op) {
    this.opCode = op;
    unmaskedPayload = ByteBuffer.wrap(emptyArray);
  }

  public DefaultFrameData(FrameData f) {
    fin = f.isFin();
    opCode = f.getOpcode();
    unmaskedPayload = ByteBuffer.wrap(f.getPayloadData());
    transferMasked = f.isTransferMasked();
  }

  @Override
  public boolean isFin() {
    return fin;
  }

  @Override
  public Opcode getOpcode() {
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
  public void setOpCode(Opcode opCode) {
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
  public void append(FrameData nextframe) throws InvalidFrameException {
    if (unmaskedPayload == null) {
      unmaskedPayload = ByteBuffer.wrap(nextframe.getPayloadData());
    } else {
      // TODO might be inefficient. Consider a global buffer pool
      ByteBuffer tmp = ByteBuffer.allocate(nextframe.getPayloadData().length + unmaskedPayload.capacity());
      tmp.put(unmaskedPayload.array());
      tmp.put(nextframe.getPayloadData());
      unmaskedPayload = tmp;
    }
    fin = nextframe.isFin();
  }

  @Override
  public String toString() {
    return "FrameData{ opCode:" + getOpcode() + ", fin:" + isFin() + ", payloadlength:" + unmaskedPayload.limit() + ", payload:" + CharsetUtils.utf8Bytes(new String(unmaskedPayload.array())) + "}";
  }

}
