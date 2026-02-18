package info.bitrich.xchangestream.kraken.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.kraken.config.converters.StringToCurrencyConverter;
import info.bitrich.xchangestream.kraken.config.converters.StringToCurrencyPairConverter;
import info.bitrich.xchangestream.kraken.config.converters.StringToOrderTypeConverter;
import info.bitrich.xchangestream.kraken.dto.response.KrakenExecutionsMessage.Payload;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class KrakenExecutionsMessage extends KrakenDataMessage<Payload> {

  @Data
  @Builder
  @Jacksonized
  public static class Payload {

    @JsonProperty("avg_price")
    private BigDecimal averageFillPrice;

    @JsonProperty("cash_order_qty")
    private BigDecimal quoteVolume;

    @JsonProperty("cl_ord_id")
    private String clientOid;

    @JsonProperty("cost")
    private BigDecimal cost;

    @JsonProperty("cum_cost")
    private BigDecimal cumulativeCost;

    @JsonProperty("cum_qty")
    private BigDecimal cumulativeQty;

    @JsonProperty("display_qty")
    private BigDecimal displayQty;

    @JsonProperty("display_qty_remain")
    private BigDecimal displayQtyRemaining;

    @JsonProperty("exec_id")
    private String executionId;

    @JsonProperty("exec_type")
    private KrakenExecutionType krakenExecutionType;

    @JsonProperty("fees")
    private List<KrakenFee> krakenFees;

    @JsonProperty("limit_price")
    private BigDecimal limitPrice;

    @JsonProperty("last_price")
    private BigDecimal averageTradePrice;

    @JsonProperty("last_qty")
    private BigDecimal assetAmount;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("order_qty")
    private BigDecimal orderAssetAmount;

    @JsonProperty("order_type")
    private KrakenOrderType krakenOrderType;

    @JsonProperty("order_status")
    private KrakenOrderStatus krakenOrderStatus;

    @JsonProperty("side")
    @JsonDeserialize(converter = StringToOrderTypeConverter.class)
    private OrderType orderSide;

    @JsonProperty("symbol")
    @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
    private CurrencyPair currencyPair;

    @JsonProperty("timestamp")
    private Instant createdAt;

    @JsonProperty("trade_id")
    private String tradeId;

    @JsonIgnore
    public BigDecimal getFeeAmount() {
      if (krakenFees != null && !krakenFees.isEmpty()) {
        return krakenFees.stream()
            .map(KrakenFee::getAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
      }
      return null;
    }

    @JsonIgnore
    public Currency getFeeCurrency() {
      if (krakenFees != null) {
        return krakenFees.stream().map(KrakenFee::getCurrency).findFirst().orElse(null);
      }
      return null;
    }
  }

  public enum KrakenExecutionType {
    @JsonProperty("pending_new")
    PENDING_NEW,

    @JsonProperty("new")
    NEW,

    @JsonProperty("trade")
    TRADE,

    @JsonProperty("filled")
    FILLED,

    @JsonProperty("canceled")
    CANCELED,

    @JsonProperty("iceberg_refill")
    ICEBERG_REFILL,

    @JsonProperty("expired")
    EXPIRED,

    @JsonProperty("amended")
    AMENDED,

    @JsonProperty("restated")
    RESTATED,

    @JsonProperty("status")
    STATUS
  }

  @Data
  @Builder
  @Jacksonized
  public static class KrakenFee {

    @JsonProperty("asset")
    @JsonDeserialize(converter = StringToCurrencyConverter.class)
    private Currency currency;

    @JsonProperty("qty")
    private BigDecimal amount;
  }

  public enum KrakenOrderType {
    @JsonProperty("limit")
    LIMIT,

    @JsonProperty("market")
    MARKET,

    @JsonProperty("iceberg")
    ICEBERG,

    @JsonProperty("stop-loss")
    STOP_LOSS,

    @JsonProperty("stop-loss-limit")
    STOP_LOSS_LIMIT,

    @JsonProperty("take-profit")
    TAKE_PROFIT,

    @JsonProperty("take-profit-limit")
    TAKE_PROFIT_LIMIT,

    @JsonProperty("trailing-stop")
    TRAILING_STOP,

    @JsonProperty("trailing-stop-limit")
    TRAILING_STOP_LIMIT,

    @JsonProperty("settle-position")
    SETTLE_POSITION,
  }

  public enum KrakenOrderStatus {
    @JsonProperty("pending_new")
    PENDING_NEW,

    @JsonProperty("new")
    NEW,

    @JsonProperty("partially_filled")
    PARTIALLY_FILLED,

    @JsonProperty("filled")
    FILLED,

    @JsonProperty("canceled")
    CANCELED,

    @JsonProperty("expired")
    EXPIRED
  }
}
