package org.knowm.xchange.coinex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.coinex.config.converter.CurrencyPairToStringConverter;
import org.knowm.xchange.coinex.config.converter.OrderTypeToStringConverter;
import org.knowm.xchange.coinex.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.coinex.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.coinex.config.converter.StringToOrderStatusConverter;
import org.knowm.xchange.coinex.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;

@Data
@Builder
@Jacksonized
public class CoinexOrder {

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("base_fee")
  private BigDecimal baseFee;

  @JsonProperty("ccy")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  @JsonProperty("client_id")
  private String clientId;

  @JsonProperty("created_at")
  private Instant createdAt;

  @JsonProperty("discount_fee")
  private BigDecimal discountFee;

  @JsonProperty("filled_amount")
  private BigDecimal filledAssetAmount;

  @JsonProperty("filled_value")
  private BigDecimal filledQuoteAmount;

  @JsonProperty("last_fill_amount")
  private BigDecimal lastFilledAssetAmount;

  @JsonProperty("last_fill_price")
  private BigDecimal lastFillPrice;

  @JsonProperty("maker_fee_rate")
  private BigDecimal makerFeeRate;

  @JsonProperty("market")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  @JsonSerialize(converter = CurrencyPairToStringConverter.class)
  CurrencyPair currencyPair;

  @JsonProperty("market_type")
  private CoinexMarketType marketType;

  @JsonProperty("order_id")
  private Long orderId;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("quote_fee")
  private BigDecimal quoteFee;

  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  @JsonSerialize(converter = OrderTypeToStringConverter.class)
  OrderType side;

  @JsonProperty("status")
  @JsonDeserialize(converter = StringToOrderStatusConverter.class)
  OrderStatus status;

  @JsonProperty("taker_fee_rate")
  private BigDecimal takerFeeRate;

  @JsonProperty("type")
  private String type;

  @JsonProperty("unfilled_amount")
  private BigDecimal unfilledAmount;

  @JsonProperty("updated_at")
  private Instant updatedAt;
}
