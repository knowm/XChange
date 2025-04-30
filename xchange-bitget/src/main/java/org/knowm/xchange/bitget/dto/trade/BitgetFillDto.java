package org.knowm.xchange.bitget.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitget.config.converter.StringToBooleanConverter;
import org.knowm.xchange.bitget.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.bitget.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;

@Data
@Builder
@Jacksonized
public class BitgetFillDto {

  @JsonProperty("userId")
  private String acccountId;

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("orderId")
  private String orderId;

  @JsonProperty("tradeId")
  private String tradeId;

  @JsonProperty("orderType")
  private OrderType orderType;

  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  private Order.OrderType orderSide;

  @JsonProperty("priceAvg")
  private BigDecimal price;

  @JsonProperty("size")
  private BigDecimal assetAmount;

  @JsonProperty("amount")
  private BigDecimal quoteAmount;

  @JsonProperty("feeDetail")
  private FeeDetail feeDetail;

  @JsonProperty("tradeScope")
  private TradeScope tradeScope;

  @JsonProperty("cTime")
  private Instant createdAt;

  @JsonProperty("uTime")
  private Instant updatedAt;

  public static enum OrderType {
    @JsonProperty("limit")
    LIMIT,

    @JsonProperty("market")
    MARKET
  }

  public static enum TradeScope {
    @JsonProperty("taker")
    TAKER,

    @JsonProperty("maker")
    MAKER
  }

  @Data
  @Builder
  @Jacksonized
  public static class FeeDetail {

    @JsonProperty("deduction")
    @JsonDeserialize(converter = StringToBooleanConverter.class)
    private Boolean deduction;

    @JsonProperty("feeCoin")
    @JsonDeserialize(converter = StringToCurrencyConverter.class)
    private Currency currency;

    @JsonProperty("totalDeductionFee")
    private BigDecimal totalDeductionFee;

    @JsonProperty("totalFee")
    private BigDecimal totalFee;
  }
}
