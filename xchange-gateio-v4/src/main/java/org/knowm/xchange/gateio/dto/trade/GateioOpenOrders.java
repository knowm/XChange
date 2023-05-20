package org.knowm.xchange.gateio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;

/** Created by David Henry on 2/19/14. */
public class GateioOpenOrders extends GateioBaseResponse {

  private final List<GateioOpenOrder> orders;

  /**
   * Constructor
   *
   * @param result
   * @param orders
   * @param msg
   */
  public GateioOpenOrders(
      @JsonProperty("result") Boolean result,
      @JsonProperty("orders") List<GateioOpenOrder> orders,
      @JsonProperty("msg") String msg) {

    super(result, msg);
    this.orders = orders;
  }

  public List<GateioOpenOrder> getOrders() {

    return orders;
  }

  @Override
  public String toString() {

    return "BTEROpenOrdersReturn [orders=" + orders + "]";
  }
}
