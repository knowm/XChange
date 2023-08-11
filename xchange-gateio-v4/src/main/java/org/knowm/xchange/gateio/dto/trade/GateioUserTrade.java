package org.knowm.xchange.gateio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.config.DoubleToInstantConverter;
import org.knowm.xchange.gateio.config.StringToCurrencyConverter;
import org.knowm.xchange.gateio.config.TimestampSecondsToInstantConverter;

@Data
@Builder
@Jacksonized
public class GateioUserTrade {

  @JsonProperty("id")
  Long id;

  @JsonProperty("create_time")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  private Instant time;

  @JsonProperty("create_time_ms")
  @JsonDeserialize(converter = DoubleToInstantConverter.class)
  private Instant timeMs;

  @JsonProperty("currency_pair")
  String currencyPair;

  @JsonProperty("side")
  private String side;

  @JsonProperty("role")
  String role;

  @JsonProperty("amount")
  BigDecimal amount;

  @JsonProperty("price")
  BigDecimal price;

  @JsonProperty("order_id")
  Long orderId;

  @JsonProperty("fee")
  BigDecimal fee;

  @JsonProperty("fee_currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  Currency feeCurrency;

  @JsonProperty("point_fee")
  BigDecimal pointFee;

  @JsonProperty("gt_fee")
  BigDecimal gtFee;

  @JsonProperty("amend_text")
  String remark;

}
