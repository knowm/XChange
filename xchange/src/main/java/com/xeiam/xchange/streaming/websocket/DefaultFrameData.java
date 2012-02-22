package com.xeiam.xchange.streaming.websocket;

import com.xeiam.xchange.streaming.websocket.exeptions.InvalidDataException;
import com.xeiam.xchange.streaming.websocket.exeptions.InvalidFrameException;
import com.xeiam.xchange.utils.CharsetUtils;

import java.nio.ByteBuffer;

public class DefaultFrameData implements FrameBuilder {
  protected static byte[] emptyarray = {};
  protected boolean fin;
  protected Opcode optcode;
  private ByteBuffer unmaskedpayload;
  protected boolean transferemasked;

  public DefaultFrameData() {
  }

  public DefaultFrameData(Opcode op) {
    this.optcode = op;
    unmaskedpayload = ByteBuffer.wrap(emptyarray);
  }

  public DefaultFrameData(FrameData f) {
    fin = f.isFin();
    optcode = f.getOpcode();
    unmaskedpayload = ByteBuffer.wrap(f.getPayloadData());
    transferemasked = f.getTransfereMasked();
  }

  @Override
  public boolean isFin() {
    return fin;
  }

  @Override
  public Opcode getOpcode() {
    return optcode;
  }

  @Override
  public boolean getTransfereMasked() {
    return transferemasked;
  }

  @Override
  public byte[] getPayloadData() {
    return unmaskedpayload.array();
  }

  @Override
  public void setFin(boolean fin) {
    this.fin = fin;
  }

  @Override
  public void setOptcode(Opcode optcode) {
    this.optcode = optcode;
  }

  @Override
  public void setPayload(byte[] payload) throws InvalidDataException {
    unmaskedpayload = ByteBuffer.wrap(payload);
  }

  @Override
  public void setTransferemasked(boolean transferemasked) {
    this.transferemasked = transferemasked;
  }

  @Override
  public void append(FrameData nextframe) throws InvalidFrameException {
    if (unmaskedpayload == null) {
      unmaskedpayload = ByteBuffer.wrap(nextframe.getPayloadData());
    } else {
      // TODO might be inefficient. Cosider a global buffer pool
      ByteBuffer tmp = ByteBuffer.allocate(nextframe.getPayloadData().length + unmaskedpayload.capacity());
      tmp.put(unmaskedpayload.array());
      tmp.put(nextframe.getPayloadData());
      unmaskedpayload = tmp;
    }
    fin = nextframe.isFin();
  }

  @Override
  public String toString() {
    return "FrameData{ optcode:" + getOpcode() + ", fin:" + isFin() + ", payloadlength:" + unmaskedpayload.limit() + ", payload:" + CharsetUtils.utf8Bytes(new String(unmaskedpayload.array())) + "}";
  }

}
