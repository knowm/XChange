package com.xeiam.xchange.bitcurex.dto.marketdata.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Yaroslav
 * Date: 08/05/15
 * Time: 16:12
 */
public class BitcurexOrder {

  /*
    "issued": 1415892208581,
     "volume": "10.00000000",
     "currency": "pln",
     "limit": "12.0000",
     "type": "bid",
     "id": "2014/11/13/143/3097"
   */

  private Long issued;
  private BigDecimal volume;
  private String currency;
  private BigDecimal limit;
  private String type;
  private String id;

  public BitcurexOrder(@JsonProperty("issued") Long issued,
                       @JsonProperty("volume") BigDecimal volume,
                       @JsonProperty("currency") String currency,
                       @JsonProperty("limit") BigDecimal limit,
                       @JsonProperty("type") String type,
                       @JsonProperty("id") String id) {
    this.issued = issued;
    this.volume = volume;
    this.currency = currency;
    this.limit = limit;
    this.type = type;
    this.id = id;
  }

  public Long getIssued() {
    return issued;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getLimit() {
    return limit;
  }

  public String getType() {
    return type;
  }

  public String getId() {
    return id;
  }
}
