package org.knowm.xchange.itbit.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItBitPlaceOrderRequest {

  @JsonProperty("type")
  protected String type;

  @JsonProperty("currency")
  protected String baseCurrency;

  @JsonProperty("side")
  private String side;

  @JsonProperty("amount")
  private String amount;

  @JsonProperty("price")
  private String price;

  @JsonProperty("instrument")
  private String instrument;

  public ItBitPlaceOrderRequest(
      String side,
      String type,
      String baseCurrency,
      String amount,
      String price,
      String instrument) {

    super();
    this.side = side;
    this.type = type;
    this.baseCurrency = baseCurrency;
    this.amount = amount;
    this.price = price;
    this.instrument = instrument;
  }

  public String getSide() {

    return side;
  }

  public String getType() {

    return type;
  }

  public String getBaseCurrency() {

    return baseCurrency;
  }

  public String getAmount() {

    return amount;
  }

  public String getPrice() {

    return price;
  }

  public String getInstrument() {

    return instrument;
  }
}
