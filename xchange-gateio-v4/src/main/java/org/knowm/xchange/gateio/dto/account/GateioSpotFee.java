package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GateioSpotFee {

  @JsonProperty("user_id")
  Long userId;

  @JsonProperty("taker_fee")
  BigDecimal takerFee;

  @JsonProperty("maker_fee")
  BigDecimal makerFee;

  @JsonProperty("gt_discount")
  Boolean gtDiscount;

  @JsonProperty("gt_taker_fee")
  BigDecimal gtTakerFee;

  @JsonProperty("gt_maker_fee")
  BigDecimal gtMakerFee;

  @JsonProperty("loan_fee")
  BigDecimal loanFee;

  @JsonProperty("point_type")
  String pointType;

  @JsonProperty("futures_taker_fee")
  BigDecimal futuresTakerFee;

  @JsonProperty("futures_maker_fee")
  BigDecimal futuresMakerFee;

  @JsonProperty("delivery_taker_fee")
  BigDecimal deliveryTakerFee;

  @JsonProperty("delivery_maker_fee")
  BigDecimal deliveryMakerFee;

  @JsonProperty("debit_fee")
  Integer debitFee;

  @JsonProperty("rpi_maker_fee")
  BigDecimal rpiMakerFee;

  @JsonProperty("futures_rpi_maker_fee")
  BigDecimal futuresRpiMakerFee;

  @JsonProperty("rpi_mm")
  Integer rpiMm;

}
