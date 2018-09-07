package org.knowm.xchange.btcmarkets.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BTCMarketsOrderDetailsRequest {
  @JsonProperty private List<Long> orderIds;

  public BTCMarketsOrderDetailsRequest(List<Long> orderIds) {
    this.orderIds = orderIds;
  }

  public List<Long> getOrderIds() {
    return orderIds;
  }

  public void setOrderIds(List<Long> orderIds) {
    this.orderIds = orderIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BTCMarketsOrderDetailsRequest that = (BTCMarketsOrderDetailsRequest) o;

    return orderIds != null ? orderIds.equals(that.orderIds) : that.orderIds == null;
  }

  @Override
  public int hashCode() {
    return orderIds != null ? orderIds.hashCode() : 0;
  }
}
