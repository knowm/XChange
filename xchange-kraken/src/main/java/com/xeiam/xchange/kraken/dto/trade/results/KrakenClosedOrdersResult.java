package com.xeiam.xchange.kraken.dto.trade.results;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrder;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenClosedOrdersResult.KrakenClosedOrders;

public class KrakenClosedOrdersResult extends KrakenResult<KrakenClosedOrders> {

  /**
   * Constructor
   * 
   * @param result
   * @param error
   */
  public KrakenClosedOrdersResult(@JsonProperty("result") KrakenClosedOrders result, @JsonProperty("error") String[] error) {

    super(result, error);
  }

  public static class KrakenClosedOrders {

    private final Map<String, KrakenOrder> orders;

    public KrakenClosedOrders(@JsonProperty("closed") Map<String, KrakenOrder> orders) {

      this.orders = orders;
    }

    public Map<String, KrakenOrder> getOrders() {

      return orders;
    }
  }
}
