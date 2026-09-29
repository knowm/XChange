package org.knowm.xchange.cryptocom.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoComUserTrade {

  @JsonProperty("account_id")
  private String accountId;

  @JsonProperty("side")
  private String side;

  @JsonProperty("instrument_name")
  private String instrumentName;

  @JsonProperty("trade_id")
  private String tradeId;

  @JsonProperty("order_id")
  private String orderId;

  @JsonProperty("traded_price")
  private String tradedPrice;

  @JsonProperty("traded_quantity")
  private String tradedQuantity;

  @JsonProperty("fees")
  private String fees;

  @JsonProperty("fee_instrument_name")
  private String feeInstrumentName;

  @JsonProperty("create_time")
  private Long createTime;

  @JsonProperty("taker_side")
  private String takerSide;
}
