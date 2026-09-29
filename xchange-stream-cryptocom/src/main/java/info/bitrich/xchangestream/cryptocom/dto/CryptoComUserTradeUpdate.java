package info.bitrich.xchangestream.cryptocom.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Payload of the {@code user.trade.<instrument>} channel. */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoComUserTradeUpdate {

  @JsonProperty("trade_id")
  private String tradeId;

  @JsonProperty("order_id")
  private String orderId;

  @JsonProperty("instrument_name")
  private String instrumentName;

  @JsonProperty("side")
  private String side;

  @JsonProperty("price")
  private String price;

  @JsonProperty("quantity")
  private String quantity;

  @JsonProperty("fee")
  private String fee;

  @JsonProperty("fee_currency")
  private String feeCurrency;

  @JsonProperty("create_time")
  private Long createTime;
}
