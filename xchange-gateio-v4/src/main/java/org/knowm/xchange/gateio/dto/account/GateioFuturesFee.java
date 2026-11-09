package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GateioFuturesFee {
  @JsonProperty("taker_fee")
  BigDecimal takerFee;

  @JsonProperty("maker_fee")
  BigDecimal makerFee;
}
