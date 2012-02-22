package com.xeiam.xchange.streaming.websocket;

import com.xeiam.xchange.streaming.websocket.exceptions.InvalidFrameException;

/**
 *
 */
public interface FrameDataTemp {

  enum OpCode {
    CONTINUOUS,
    TEXT,
    BINARY,
    PING,
    PONG,
    CLOSING
    // end of enum
    ;
  }

  public boolean isFin();

  public boolean isTransferMasked();

  public OpCode getOpCode();

  public byte[] getPayloadData();

  public abstract void append(FrameDataTemp nextFrame) throws InvalidFrameException;
}
