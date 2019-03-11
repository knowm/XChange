package org.knowm.xchange.vircurex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

/** Created by David Henry on 2/20/14. */
@JsonDeserialize(using = VircurexOpenOrdersDeserializer.class)
public class VircurexOpenOrdersReturn {

  private final int orderCount;
  private final String userName;
  private final String timeStamp;
  private final String token;
  private final String function;
  private final int status;
  private List<VircurexOpenOrder> openOrders;

  public VircurexOpenOrdersReturn(
      @JsonProperty("numberorders") int orderCount,
      @JsonProperty("account") String userName,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("token") String token,
      @JsonProperty("status") int status,
      @JsonProperty("function") String function) {

    this.orderCount = orderCount;
    this.userName = userName;
    this.timeStamp = timestamp;
    this.token = token;
    this.function = function;
    this.status = status;
  }

  public int getOrderCount() {

    return orderCount;
  }

  public int getStatus() {

    return status;
  }

  public String getUserName() {

    return userName;
  }

  public String getTimeStamp() {

    return timeStamp;
  }

  public String getToken() {

    return token;
  }

  public String getFunction() {

    return function;
  }

  public List<VircurexOpenOrder> getOpenOrders() {

    return openOrders;
  }

  public void setOpenOrders(List<VircurexOpenOrder> openOrders) {

    this.openOrders = openOrders;
  }
}
