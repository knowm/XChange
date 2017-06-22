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

  public ClientDeal(@JsonProperty("number") Long number, @JsonProperty("time") Long time, @JsonProperty("price") BigDecimal price,
      @JsonProperty("volume") BigDecimal volume, @JsonProperty("baseVolume") BigDecimal baseVolume, @JsonProperty("type") String type,
      @JsonProperty("instrumentId") Integer instrumentId, @JsonProperty("commission") BigDecimal commission, @JsonProperty("orderNumber") Long orderNumber) {

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
