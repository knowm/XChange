package org.knowm.xchange.bybit.dto.trade.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.bybit.dto.trade.BybitOrderStatus;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.dto.trade.BybitSide;

@SuperBuilder
@Data
public class BybitOrderDetail {

  @JsonProperty("symbol")
  String symbol;

  @JsonProperty("orderType")
  BybitOrderType orderType;

  @JsonProperty("side")
  BybitSide side;

  @JsonProperty("qty")
  BigDecimal qty;

  @JsonProperty("cumExecQty")
  BigDecimal cumExecQty;

  @JsonProperty("feeCurrency")
  String feeCurrency;

  @JsonProperty("cumExecFee")
  BigDecimal cumExecFee;

  @JsonProperty("orderId")
  String orderId;

  @JsonProperty("createdTime")
  Date createdTime;

  @JsonProperty("price")
  BigDecimal price;

  @JsonProperty("avgPrice")
  BigDecimal avgPrice;

  @JsonProperty("orderStatus")
  BybitOrderStatus orderStatus;

  @JsonProperty("marketUnit")
  String marketUnit;

  @JsonProperty("cumFeeDetail")
  Map<String, String> cumFeeDetail;
}
