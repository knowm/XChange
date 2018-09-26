package org.knowm.xchange.getbtc.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GetbtcOpenOrders {

  @JsonProperty("open-orders")
  private OpenOrders openOrders;

  /** No args constructor for use in serialization */
  public GetbtcOpenOrders() {}

  /** @param openOrders */
  public GetbtcOpenOrders(OpenOrders openOrders) {
    super();
    this.openOrders = openOrders;
  }

  @JsonProperty("open-orders")
  public OpenOrders getOpenOrders() {
    return openOrders;
  }

  @JsonProperty("open-orders")
  public void setOpenOrders(OpenOrders openOrders) {
    this.openOrders = openOrders;
  }

  public static class OpenOrders {

    @JsonProperty("bid")
    private List<Object> bid = null;

    @JsonProperty("ask")
    private List<Object> ask = null;

    @JsonProperty("request_currency")
    private String requestCurrency;

    /** No args constructor for use in serialization */
    public OpenOrders() {}

    /**
     * @param requestCurrency
     * @param ask
     * @param bid
     */
    public OpenOrders(List<Object> bid, List<Object> ask, String requestCurrency) {
      super();
      this.bid = bid;
      this.ask = ask;
      this.requestCurrency = requestCurrency;
    }

    @JsonProperty("bid")
    public List<Object> getBid() {
      return bid;
    }

    @JsonProperty("bid")
    public void setBid(List<Object> bid) {
      this.bid = bid;
    }

    @JsonProperty("ask")
    public List<Object> getAsk() {
      return ask;
    }

    @JsonProperty("ask")
    public void setAsk(List<Object> ask) {
      this.ask = ask;
    }

    @JsonProperty("request_currency")
    public String getRequestCurrency() {
      return requestCurrency;
    }

    @JsonProperty("request_currency")
    public void setRequestCurrency(String requestCurrency) {
      this.requestCurrency = requestCurrency;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("bid", bid)
          .append("ask", ask)
          .append("requestCurrency", requestCurrency)
          .toString();
    }
  }
}
