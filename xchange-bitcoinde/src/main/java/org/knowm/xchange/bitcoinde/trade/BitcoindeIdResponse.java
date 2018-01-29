package org.knowm.xchange.bitcoinde.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author kaiserfr
 *
 */
public class BitcoindeIdResponse {
  private final String id;

  public BitcoindeIdResponse(@JsonProperty("order_id") String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CoinbaseExIdResponse [id=");
    builder.append(id);
    builder.append("]");
    return builder.toString();
  }

}
