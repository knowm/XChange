package org.knowm.xchange.kraken.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.trade.KrakenOrder;
import org.knowm.xchange.kraken.dto.trade.results.KrakenClosedOrdersResult.KrakenClosedOrders;

public class KrakenClosedOrdersResult extends KrakenResult<KrakenClosedOrders> {

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  public KrakenClosedOrdersResult(
      @JsonProperty("result") KrakenClosedOrders result, @JsonProperty("error") String[] error) {

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
