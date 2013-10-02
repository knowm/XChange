package org.xchange.kraken.dto.trade;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class KrakenOuterOpen {

  private Map<String, KrakenOpenOrder> orders;

  /**
   * @param orders
   */
  public KrakenOuterOpen(@JsonProperty("open") Map<String, KrakenOpenOrder> orders) {

    this.orders = orders;
  }

  public Map<String, KrakenOpenOrder> getOrders() {

    return orders;
  }
}
