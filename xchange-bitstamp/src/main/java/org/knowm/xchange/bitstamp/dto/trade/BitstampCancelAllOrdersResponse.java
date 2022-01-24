package org.knowm.xchange.bitstamp.dto.trade;

import java.util.List;

public class BitstampCancelAllOrdersResponse {

  public List<BitstampOrderCancelResponse> canceled;
  public boolean success;
}
