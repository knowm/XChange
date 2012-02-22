package com.xeiam.xchange.streaming.websocket;

import com.xeiam.xchange.streaming.websocket.exeptions.InvalidDataException;

public interface FrameBuilder extends FrameData {

  public abstract void setFin(boolean fin);

  public abstract void setOptcode(Opcode optcode);

  public abstract void setPayload(byte[] payload) throws InvalidDataException;

  public abstract void setTransferemasked(boolean transferemasked);

}