package org.knowm.xchange.gateio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.gateio.config.converter.DoubleToInstantConverter;
import org.knowm.xchange.gateio.config.converter.OrderTypeToStringConverter;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.gateio.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.gateio.config.converter.TimestampSecondsToInstantConverter;

@Data
@Builder
@Jacksonized
public class GateioUserTradeRaw {

  @JsonProperty("id")
  Long id;

  @JsonProperty("create_time")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  Instant time;

  @JsonProperty("create_time_ms")
  @JsonDeserialize(converter = DoubleToInstantConverter.class)
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
