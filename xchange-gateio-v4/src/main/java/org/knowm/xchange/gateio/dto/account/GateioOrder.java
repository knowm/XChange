package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.gateio.config.converter.CurrencyPairToStringConverter;
import org.knowm.xchange.gateio.config.converter.OrderTypeToStringConverter;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.gateio.config.converter.StringToOrderTypeConverter;

@Data
@Builder
@Jacksonized
public class GateioOrder {

  @JsonProperty("id")
  String id;

  @JsonProperty("text")
  String clientOrderId;

  @JsonProperty("amend_text")
  String amendText;

  @JsonProperty("create_time_ms")
  Instant createdAt;

  @JsonProperty("update_time_ms")
  Instant updatedAt;

  @JsonProperty("status")
  String status;

  @JsonProperty("currency_pair")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  @JsonSerialize(converter = CurrencyPairToStringConverter.class)
  CurrencyPair currencyPair;

  @JsonProperty("type")
  String type;

  @JsonProperty("account")
  String account;

  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  @JsonSerialize(converter = OrderTypeToStringConverter.class)
  OrderType side;

  @JsonProperty("amount")
  BigDecimal amount;

  @JsonProperty("price")
  BigDecimal price;

  @JsonProperty("time_in_force")
  String timeInForce;

  @JsonProperty("iceberg")
  BigDecimal icebergAmount;

  @JsonProperty("auto_borrow")
  Boolean autoBorrow;

  @JsonProperty("auto_repay")
  Boolean autoRepay;

  @JsonProperty("left")
  BigDecimal amountLeftToFill;

  @JsonProperty("filled_total")
  BigDecimal filledTotalQuote;

  @JsonProperty("avg_deal_price")
  BigDecimal avgDealPrice;

  @JsonProperty("fee")
  BigDecimal fee;

  @JsonProperty("fee_currency")
  String feeCurrency;

  @JsonProperty("point_fee")
  BigDecimal pointFee;

  @JsonProperty("gt_fee")
  BigDecimal gtFee;

  @JsonProperty("gt_maker_fee")
  BigDecimal gtMakerFee;

  @JsonProperty("gt_taker_fee")
  BigDecimal gtTakerFee;

  @JsonProperty("gt_discount")
  Boolean gtDiscount;

  @JsonProperty("rebated_fee")
  BigDecimal rebatedFee;

  @JsonProperty("rebated_fee_currency")
  String rebatedFeeCurrency;

  @JsonProperty("stp_id")
  Integer stpId;

  @JsonProperty("stp_act")
  String stpAction;

  @JsonProperty("finish_as")
  String finishAs;
  
}
