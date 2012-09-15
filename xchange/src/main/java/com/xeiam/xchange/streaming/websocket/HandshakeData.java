package com.xeiam.xchange.streaming.websocket;

import java.util.Iterator;

public interface HandshakeData {

  public String getHttpStatusMessage();

  public String getResourceDescriptor();

  public Iterator<String> iterateHttpFields();

  public String getFieldValue(String name);

  public boolean hasFieldValue(String name);

  public byte[] getContent();
  // public boolean isComplete();
}
