package info.bitrich.xchangestream.gateio.dto.response.usertrade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.gateio.config.converter.DoubleToInstantConverter;
import info.bitrich.xchangestream.gateio.config.converter.StringToCurrencyPairConverter;
import info.bitrich.xchangestream.gateio.config.converter.TimestampSecondsToInstantConverter;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

@Data
public class UserTradeDTO {

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
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  private Instant time;

  @JsonProperty("create_time_ms")
  @JsonDeserialize(converter = DoubleToInstantConverter.class)
  private Instant timeMs;

  @JsonProperty("side")
  private String side;

  @JsonProperty("amount")
  BigDecimal amount;

  @JsonProperty("role")
  String role;

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
