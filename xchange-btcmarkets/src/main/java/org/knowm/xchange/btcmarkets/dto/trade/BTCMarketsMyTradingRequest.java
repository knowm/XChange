package org.knowm.xchange.btcmarkets.dto.trade;

import java.util.Date;

import org.knowm.xchange.utils.jackson.UnixTimestampSerializer;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Used for open orders, order history, trade history.
 */
@JsonPropertyOrder({"currency", "instrument", "limit", "since"})
public class BTCMarketsMyTradingRequest {

  private final String currency;

  private final String instrument;

  private final Integer limit;

  @JsonSerialize(using = UnixTimestampSerializer.class)
  private final Date since;

  public BTCMarketsMyTradingRequest(String currency, String instrument, Integer limit, Date since) {
    this.currency = currency;
    this.instrument = instrument;
    this.limit = limit;
    this.since = since;
  }

  public String getCurrency() {
    return currency;
  }

  public String getInstrument() {
    return instrument;
  }

  public Integer getLimit() {
    return limit;
  }

  public Date getSince() {
    return since;
  }
}
