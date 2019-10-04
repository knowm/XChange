package org.knowm.xchange.bitfinex.v2.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
public class EmptyRequest {
  public static final EmptyRequest INSTANCE = new EmptyRequest();
}
