package org.knowm.xchange.bitget.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitget.config.converter.OrderTypeToStringConverter;
import org.knowm.xchange.bitget.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.bitget.dto.trade.BitgetOrderInfoDto.OrderType;
import org.knowm.xchange.dto.Order;

@Data
@Builder
@Jacksonized
public class BitgetPlaceOrderDto {

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  @JsonSerialize(converter = OrderTypeToStringConverter.class)
  private Order.OrderType orderSide;

  @JsonProperty("orderType")
  private OrderType orderType;

  @JsonProperty("force")
  private TimeInForce timeInForce;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("size")
  private BigDecimal size;

  @JsonProperty("clientOid")
  private String clientOid;

  @JsonProperty("triggerPrice")
  private BigDecimal triggerPrice;

  @JsonProperty("tpslType")
  private TpSlType tpSlType;

  @JsonProperty("requestTime")
  private Instant requestTime;

  @JsonProperty("receiveWindow")
  private Instant receiveWindow;

  @JsonProperty("stpMode")
  private StpMode stpMode;

  @JsonProperty("presetTakeProfitPrice")
  private BigDecimal presetTakeProfitPrice;

  @JsonProperty("executeTakeProfitPrice")
  private BigDecimal executeTakeProfitPrice;

  @JsonProperty("presetStopLossPrice")
  private BigDecimal presetStopLossPrice;

  @JsonProperty("executeStopLossPrice")
  private BigDecimal executeStopLossPrice;

  public enum TimeInForce {
    @JsonProperty("gtc")
    GOOD_TIL_CANCELLED,

    @JsonProperty("post_only")
    POST_ONLY,

    @JsonProperty("fok")
    FILL_OR_KILL,

    @JsonProperty("ioc")
    IMMEDIATE_OR_CANCEL
  }

  public enum TpSlType {
    @JsonProperty("normal")
    NORMAL,

    @JsonProperty("tpsl")
    SPOT_TP_SL
  }

  public enum StpMode {
    @JsonProperty("none")
    NONE,

    @JsonProperty("cancel_taker")
    CANCEL_TAKER,

    @JsonProperty("cancel_maker")
    CANCEL_MAKER,

    @JsonProperty("cancel_both")
    CANCEL_BOTH
  }
}
