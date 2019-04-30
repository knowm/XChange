package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitTrades {

  @JsonProperty("trades")
  private List<DeribitTrade> trades;

  @JsonProperty("has_more")
  private boolean hasMore;


  public List<DeribitTrade> getTrades() {
    return trades;
  }

  public void setTrades(List<DeribitTrade> trades) {
    this.trades = trades;
  }

  public boolean isHasMore() {
    return hasMore;
  }

  public void setHasMore(boolean hasMore) {
    this.hasMore = hasMore;
  }

  @Override
  public String toString() {
    return "DeribitTrades{" +
            "trades=" + trades +
            ", hasMore=" + hasMore +
            '}';
  }
}
