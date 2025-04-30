package org.knowm.xchange.bitget.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitget.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.bitget.config.deserializer.FeeDetailDeserializer;
import org.knowm.xchange.dto.Order;

@Data
@Builder
@Jacksonized
public class BitgetOrderInfoDto {

  @JsonProperty("userId")
  private String acccountId;

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("orderId")
  private String orderId;

  @JsonProperty("clientOid")
  private String clientOid;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("size")
  private BigDecimal size;

  @JsonProperty("orderType")
  private OrderType orderType;

  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  private Order.OrderType orderSide;

  @JsonProperty("status")
  private BitgetOrderStatus orderStatus;

  @JsonProperty("priceAvg")
  private BigDecimal priceAvg;

  @JsonProperty("baseVolume")
  private BigDecimal baseVolume;

  @JsonProperty("quoteVolume")
  private BigDecimal quoteVolume;

  @JsonProperty("enterPointSource")
  private String enterPointSource;

  @JsonProperty("cTime")
  private Instant createdAt;

  @JsonProperty("uTime")
  private Instant updatedAt;

  @JsonProperty("orderSource")
  private OrderSource orderSource;

  @JsonProperty("feeDetail")
  @JsonDeserialize(using = FeeDetailDeserializer.class)
  private FeeDetail feeDetail;

  public BigDecimal getFee() {
    return Optional.ofNullable(feeDetail)
        .map(FeeDetail::getNewFees)
        .map(NewFees::getTotalFee)
        .map(BigDecimal::abs)
        .orElse(null);
  }

  public static enum OrderType {
    @JsonProperty("limit")
    LIMIT,

    @JsonProperty("market")
    MARKET
  }

  public static enum BitgetOrderStatus {
    @JsonProperty("live")
    PENDING,

    @JsonProperty("partially_filled")
    PARTIALLY_FILLED,

    @JsonProperty("filled")
    FILLED,

    @JsonProperty("cancelled")
    CANCELLED
  }

  public static enum OrderSource {
    @JsonProperty("normal")
    NORMAL,

    @JsonProperty("market")
    MARKET,

    @JsonProperty("spot_trader_buy")
    SPOT_TRADER_BUY,

    @JsonProperty("spot_follower_buy")
    SPOT_FOLLOWER_BUY,

    @JsonProperty("spot_trader_sell")
    SPOT_TRADER_SELL,

    @JsonProperty("spot_follower_sell")
    SPOT_FOLLOWER_SELL
  }

  @Data
  @Builder
  @Jacksonized
  public static class NewFees {
    @JsonProperty("t")
    private BigDecimal totalFee;
  }

  @Data
  @Builder
  @Jacksonized
  public static class FeeDetail {
    @JsonProperty("newFees")
    private NewFees newFees;
  }
}
