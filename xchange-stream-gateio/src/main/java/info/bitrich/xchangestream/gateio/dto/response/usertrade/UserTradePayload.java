package info.bitrich.xchangestream.gateio.dto.response.usertrade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.gateio.config.converter.*;
import org.knowm.xchange.gateio.dto.trade.Role;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class UserTradePayload {

  @JsonProperty("id")
  Long id;

  @JsonProperty("user_id")
  Long userId;

  @JsonProperty("order_id")
  Long orderId;

  @JsonProperty("currency_pair")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  CurrencyPair currencyPair;

  @JsonProperty("create_time")
  @JsonDeserialize(converter = DoubleMillisecondsToInstantConverter.class)
  Instant time;

  @JsonProperty("create_time_ms")
  @JsonDeserialize(converter = StringMillisecondsToInstantConverter.class)
  Instant timeMs;

  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  @JsonSerialize(converter = OrderTypeToStringConverter.class)
  OrderType side;

  @JsonProperty("amount")
  BigDecimal amount;

  @JsonProperty("role")
  Role role;

  @JsonProperty("price")
  BigDecimal price;

  @JsonProperty("fee")
  BigDecimal fee;

  @JsonProperty("fee_currency")
  Currency feeCurrency;

  @JsonProperty("point_fee")
  BigDecimal pointFee;

  @JsonProperty("gt_fee")
  BigDecimal gtFee;

  @JsonProperty("text")
  String remark;
}
