package org.xchange.kraken.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;


public class KrakenOrderDescription {
  private String order;
  public KrakenOrderDescription(@JsonProperty("order") String order){
    this.order=order;
  }
  public String getOrderDescription(){
    return order;
  }
}
