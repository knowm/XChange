package org.knowm.xchange.gateio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.gateio.config.converter.CurrencyPairToStringConverter;
import org.knowm.xchange.gateio.config.converter.OrderTypeToStringConverter;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@Jacksonized
public class GateioSpotOrderRequest {

  @JsonProperty("text")
  private String clientOrderId;

  @JsonProperty("currency_pair")
  @JsonSerialize(converter = CurrencyPairToStringConverter.class)
  private Instrument currencyPair;

  @JsonProperty("type")
  private String type;

  @JsonProperty("account")
  private String account;

  @JsonProperty("side")
  @JsonSerialize(converter = OrderTypeToStringConverter.class)
  private OrderType side;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("time_in_force")
  private String timeInForce;

  @JsonProperty("iceberg")
  private BigDecimal icebergAmount;

  @JsonProperty("auto_borrow")
  private Boolean autoBorrow;

  @JsonProperty("auto_repay")
  private Boolean autoRepay;

  @JsonProperty("stp_act")
  private String stpAction;

}
