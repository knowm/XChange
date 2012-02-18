package com.xeiam.xchange.streaming.websocket;

import com.xeiam.xchange.streaming.HandshakeData;

public interface HandshakeBuilder extends HandshakeData {

  public abstract void setContent(byte[] content);

  public abstract void setResourceDescriptor(String resourcedescriptor);

  public abstract void setHttpStatusMessage(String message);

  public abstract void put(String name, String value);

}