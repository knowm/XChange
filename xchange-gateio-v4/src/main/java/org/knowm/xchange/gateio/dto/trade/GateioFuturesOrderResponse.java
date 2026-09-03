package org.knowm.xchange.gateio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.gateio.config.converter.DoubleMillisecondsToInstantConverter;
import org.knowm.xchange.gateio.config.converter.LongToInstantConverter;
import org.knowm.xchange.gateio.config.converter.StringToFutureContractConverter;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@Jacksonized
public class GateioFuturesOrderResponse {

  @JsonProperty("id")
  Long id;

  @JsonProperty("create_time")
  @JsonDeserialize(converter = LongToInstantConverter.class)
  Instant createTime;

  @JsonProperty("create_time_ms")
  @JsonDeserialize(converter = LongToInstantConverter.class)
  Instant createTimeMs;

  @JsonProperty("finish_time")
  @JsonDeserialize(converter = DoubleMillisecondsToInstantConverter.class)
  Instant finishTime;

  @JsonProperty("finish_time_ms")
  @JsonDeserialize(converter = LongToInstantConverter.class)
  Instant finishTimeMs;

  @JsonProperty("update_time")
  @JsonDeserialize(converter = DoubleMillisecondsToInstantConverter.class)
  Instant updatedTime;

  @JsonProperty("update_time_ms")
  @JsonDeserialize(converter = LongToInstantConverter.class)
  Instant updatedTimeMs;

  @JsonProperty("finish_as")
  String finishAs;

  @JsonProperty("status")
  String status;

  @JsonProperty("contract")
  @JsonDeserialize(converter = StringToFutureContractConverter.class)
  Instrument contract;

  @JsonProperty("size")
  BigDecimal size;

  @JsonProperty("iceberg")
  BigDecimal iceberg;

  @JsonProperty("price")
  BigDecimal price;

  @JsonProperty("close")
  Boolean close;

  @JsonProperty("is_close")
  Boolean isClose;

  @JsonProperty("reduce_only")
  Boolean reduceOnly;

  @JsonProperty("is_reduce_only")
  Boolean isReduceOnly;

  @JsonProperty("type")
  String type;

  @JsonProperty("tif")
  String timeInForce;

  @JsonProperty("left")
  BigDecimal left;

  @JsonProperty("filled_total")
  BigDecimal filledTotal;

  @JsonProperty("entry_price")
  BigDecimal entryPrice;

  @JsonProperty("fill_price")
  BigDecimal fillPrice;

  @JsonProperty("text")
  String text;

  @JsonProperty("user")
  String user;

  @JsonProperty("tkfr")
  BigDecimal takerFeeRate;

  @JsonProperty("mkfr")
  BigDecimal makerFeeRate;

  @JsonProperty("fee")
  BigDecimal fee;

  @JsonProperty("refu")
  BigDecimal referralRebate;

  @JsonProperty("auto_size")
  String autoSize;

  @JsonProperty("stp_id")
  Long stpId;

  @JsonProperty("stp_act")
  String stpAct;

  @JsonProperty("amend_text")
  String amendText;

  @JsonProperty("biz_info")
  String bizInfo;

}
