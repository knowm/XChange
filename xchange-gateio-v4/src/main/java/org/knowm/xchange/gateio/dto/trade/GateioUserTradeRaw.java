package org.knowm.xchange.gateio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.gateio.config.converter.*;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@Builder
@Jacksonized
public class GateioUserTradeRaw {

  @JsonProperty("id")
  Long id;

  @JsonProperty("create_time")
  @JsonDeserialize(converter = StringSecondsToInstantConverter.class)
  Instant time;

  @JsonProperty("create_time_ms")
  @JsonDeserialize(converter = StringMillisecondsToInstantConverter.class)
  Instant timeMs;

  @JsonProperty("currency_pair")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  CurrencyPair currencyPair;

  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  @JsonSerialize(converter = OrderTypeToStringConverter.class)
  OrderType side;

  @JsonProperty("role")
  Role role;

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
