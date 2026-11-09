package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.gateio.config.converter.LongToInstantConverter;
import org.knowm.xchange.gateio.config.converter.StringSecondsToInstantConverter;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@Jacksonized
public class GateioInstrumentDetails {

  @JsonProperty("name")
  String name;

  @JsonProperty("type")
  String type;

  @JsonProperty("quanto_multiplier")
  BigDecimal quantoMultiplier;

  @JsonProperty("leverage_min")
  BigDecimal leverageMin;

  @JsonProperty("leverage_max")
  BigDecimal leverageMax;

  @JsonProperty("maintenance_rate")
  BigDecimal maintenanceRate;

  @JsonProperty("mark_type")
  String markType;

  @JsonProperty("mark_price")
  BigDecimal markPrice;

  @JsonProperty("index_price")
  BigDecimal indexPrice;

  @JsonProperty("last_price")
  BigDecimal lastPrice;

  @JsonProperty("maker_fee_rate")
  BigDecimal makerFeeRate;

  @JsonProperty("taker_fee_rate")
  BigDecimal takerFeeRate;

  @JsonProperty("order_price_round")
  BigDecimal orderPriceRound;

  @JsonProperty("mark_price_round")
  BigDecimal markPriceRound;

  @JsonProperty("funding_rate")
  BigDecimal fundingRate;

  @JsonProperty("funding_interval")
  Integer fundingInterval;

  @JsonProperty("funding_next_apply")
  @JsonDeserialize(converter = StringSecondsToInstantConverter.class)
  Instant fundingNextApply;

  @JsonProperty("risk_limit_base")
  BigDecimal riskLimitBase;

  @JsonProperty("interest_rate")
  BigDecimal interestRate;

  @JsonProperty("risk_limit_step")
  BigDecimal riskLimitStep;

  @JsonProperty("risk_limit_max")
  BigDecimal riskLimitMax;

  @JsonProperty("order_size_min")
  BigDecimal orderSizeMin;

  @JsonProperty("enable_decimal")
  Boolean enableDecimal;

  @JsonProperty("order_size_max")
  BigDecimal orderSizeMax;

  @JsonProperty("order_price_deviate")
  BigDecimal orderPriceDeviate;

  @JsonProperty("ref_discount_rate")
  BigDecimal refDiscountRate;

  @JsonProperty("ref_rebate_rate")
  BigDecimal refRebateRate;

  @JsonProperty("orderbook_id")
  Long orderbookId;

  @JsonProperty("trade_id")
  Long tradeId;

  @JsonProperty("trade_size")
  BigDecimal tradeSize;

  @JsonProperty("position_size")
  BigDecimal positionSize;

  @JsonProperty("config_change_time")
  @JsonDeserialize(converter = LongToInstantConverter.class)
  Instant configChangeTime;

  @JsonProperty("in_delisting")
  Boolean inDelisting;

  @JsonProperty("orders_limit")
  Integer ordersLimit;

  @JsonProperty("enable_bonus")
  Boolean enableBonus;

  @JsonProperty("enable_credit")
  Boolean enableCredit;

  @JsonProperty("create_time")
  @JsonDeserialize(converter = LongToInstantConverter.class)
  Instant createTime;

  @JsonProperty("funding_cap_ratio")
  BigDecimal fundingCapRatio;

  @JsonProperty("status")
  String status;

  @JsonProperty("launch_time")
  @JsonDeserialize(converter = LongToInstantConverter.class)
  Instant launchTime;

  @JsonProperty("delisting_time")
  @JsonDeserialize(converter = LongToInstantConverter.class)
  Instant delistingTime;

  @JsonProperty("delisted_time")
  @JsonDeserialize(converter = LongToInstantConverter.class)
  Instant delistedTime;

  @JsonProperty("market_order_slip_ratio")
  BigDecimal marketOrderSlipRatio;

  @JsonProperty("market_order_size_max")
  BigDecimal marketOrderSizeMax;

  @JsonProperty("funding_rate_limit")
  BigDecimal fundingRateLimit;

  @JsonProperty("contract_type")
  String contractType;

  @JsonProperty("funding_impact_value")
  BigDecimal fundingImpactValue;

}
