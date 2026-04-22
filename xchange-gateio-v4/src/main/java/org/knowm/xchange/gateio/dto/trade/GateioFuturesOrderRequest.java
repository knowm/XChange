package org.knowm.xchange.gateio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;


@Data
@Builder
@Jacksonized
public class GateioFuturesOrderRequest {

  @JsonProperty("contract")
  String contract;

  @JsonProperty("size")
  BigDecimal size;

  @JsonProperty("iceberg")
  BigDecimal iceberg;

  @JsonProperty("price")
  BigDecimal price;

  @JsonProperty("close")
  Boolean close;

  @JsonProperty("reduce_only")
  Boolean reduceOnly;

  @JsonProperty("tif")
  String timeInForce;

  @JsonProperty("text")
  String text;

  @JsonProperty("auto_size")
  String autoSize;

  @JsonProperty("stp_act")
  String stpAct;

  @JsonProperty("pid")
  Long pid;

  @JsonProperty("market_order_slip_ratio")
  BigDecimal marketOrderSlipRatio;

  @JsonProperty("pos_margin_mode")
  String posMarginMode;

}
