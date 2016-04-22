package org.knowm.xchange.cointrader.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.knowm.xchange.cointrader.dto.CointraderRequest;

public class CointraderTradeHistoryRequest extends CointraderRequest {

  @JsonProperty
  @JsonSerialize(using = ToStringSerializer.class)
  private final Integer limit;

  @JsonProperty
  @JsonSerialize(using = ToStringSerializer.class)
  private final Integer offset;

  @JsonProperty
  @JsonSerialize(using = ToStringSerializer.class)
  private final Long since;

  public CointraderTradeHistoryRequest(Integer limit, Integer offset, Long sinceTradeId) {
    this.limit = limit;
    this.offset = offset;
    this.since = sinceTradeId;
  }
}
