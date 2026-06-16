package org.knowm.xchange.gateio.dto.trade;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.gateio.config.converter.DoubleToInstantConverter;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.gateio.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@Jacksonized
public class GateioSpotOrderResponse {

  @JsonProperty("id")
  private String id;

  @JsonProperty("text")
  private String clientOrderId;

  @JsonProperty("amend_text")
  private String amendText;

  @JsonProperty("create_time_ms")
  @JsonDeserialize(converter = DoubleToInstantConverter.class)
  private Instant createdAt;

  @JsonProperty("update_time_ms")
  @JsonDeserialize(converter = DoubleToInstantConverter.class)
  private Instant updatedAt;

  @JsonProperty("status")
  @JsonAlias("event")
  private String status;

  @JsonProperty("currency_pair")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  private Instrument currencyPair;

  @JsonProperty("type")
  private String type;

  @JsonProperty("account")
  private String account;

  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  private OrderType side;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("time_in_force")
  private String timeInForce;

  @JsonProperty("iceberg")
  private BigDecimal icebergAmount;

  @JsonProperty("left")
  private BigDecimal amountLeftToFill;

  @JsonProperty("filled_total")
  private BigDecimal filledTotalQuote;

  @JsonProperty("avg_deal_price")
  private BigDecimal avgDealPrice;

  @JsonProperty("fee")
  private BigDecimal fee;

  @JsonProperty("fee_currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency feeCurrency;

  @JsonProperty("point_fee")
  private BigDecimal pointFee;

  @JsonProperty("gt_fee")
  private BigDecimal gtFee;

  @JsonProperty("gt_maker_fee")
  private BigDecimal gtMakerFee;

  @JsonProperty("gt_taker_fee")
  private BigDecimal gtTakerFee;

  @JsonProperty("gt_discount")
  private Boolean gtDiscount;

  @JsonProperty("rebated_fee")
  private BigDecimal rebatedFee;

  @JsonProperty("rebated_fee_currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency rebatedFeeCurrency;

  @JsonProperty("stp_id")
  private Integer stpId;

  @JsonProperty("stp_act")
  private String stpAction;

  @JsonProperty("finish_as")
  private String finishAs;

}
