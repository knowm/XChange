package org.knowm.xchange.kraken.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.trade.KrakenOrder;
import org.knowm.xchange.kraken.dto.trade.results.KrakenOpenOrdersResult.KrakenOpenOrders;

public class KrakenOpenOrdersResult extends KrakenResult<KrakenOpenOrders> {

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  public KrakenOpenOrdersResult(
      @JsonProperty("result") KrakenOpenOrders result, @JsonProperty("error") String[] error) {

    super(result, error);
  }

  public static class KrakenOpenOrders {

    private final Map<String, KrakenOrder> orders;

    /**
     * Constructor
     *
     * @param orders
     */
    public KrakenOpenOrders(@JsonProperty("open") Map<String, KrakenOrder> orders) {

      this.orders = orders;
    }

    public Map<String, KrakenOrder> getOrders() {

      return orders;
    }
  }
}
