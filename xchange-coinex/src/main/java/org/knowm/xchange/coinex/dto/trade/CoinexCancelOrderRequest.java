package org.knowm.xchange.coinex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.coinex.config.converter.CurrencyPairToStringConverter;
import org.knowm.xchange.coinex.dto.account.CoinexMarketType;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@Jacksonized
public class CoinexCancelOrderRequest {

  @JsonProperty("market")
  @JsonSerialize(converter = CurrencyPairToStringConverter.class)
  Instrument instrument;

  @JsonProperty("market_type")
  private CoinexMarketType marketType;

  @JsonProperty("order_id")
  private Long orderId;

}
