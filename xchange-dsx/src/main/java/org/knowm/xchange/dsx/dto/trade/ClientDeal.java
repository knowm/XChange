package org.knowm.xchange.dsx.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class ClientDeal {

  private final Long number;
  private final Long time;
  private final BigDecimal price;
  private final BigDecimal volume;
  private final BigDecimal baseVolume;
  private final String type;
  private final Integer instrumentId;

  private final BigDecimal commission;
  private final Long orderNumber;

  public ClientDeal(@JsonProperty("number") Long number, @JsonProperty("time") Long time, @JsonProperty("price") BigDecimal price, @JsonProperty
      ("volume") BigDecimal volume, @JsonProperty("base_volume") BigDecimal baseVolume, @JsonProperty("type") String type, @JsonProperty
      ("instrument_id") Integer instrumentId, @JsonProperty("commission") BigDecimal commission, @JsonProperty("order_number") Long orderNumber) {
    this.number = number;
    this.time = time;
    this.price = price;
    this.volume = volume;
    this.baseVolume = baseVolume;
    this.type = type;
    this.instrumentId = instrumentId;
    this.commission = commission;
    this.orderNumber = orderNumber;
  }
}
