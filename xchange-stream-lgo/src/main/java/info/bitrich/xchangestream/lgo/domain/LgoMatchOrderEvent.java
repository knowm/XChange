package info.bitrich.xchangestream.lgo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

/** Order matched against another order */
public class LgoMatchOrderEvent extends LgoBatchOrderEvent {
  /** Trade identifier */
  private final String tradeId;

  /** Trade price (quote currency) */
  private final BigDecimal tradePrice;

  /** Trade quantity (base currency) */
  private final BigDecimal filledQuantity;

  /** Remaining amount (base currency) */
  private final BigDecimal remainingQuantity;

  /** Fees (quote currency) */
  private final BigDecimal fees;

  /** Trade liquidity (T for taker, M for maker) */
  private final String liquidity;

  /** Trade type (BID/ASK): taker order type */
  private Order.OrderType orderType;

  public LgoMatchOrderEvent(
      @JsonProperty("type") String type,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("time")
          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          Date time,
      @JsonProperty("trade_id") String tradeId,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("filled_quantity") BigDecimal filledQuantity,
      @JsonProperty("remaining_quantity") BigDecimal remainingQuantity,
      @JsonProperty("fees") BigDecimal fees,
      @JsonProperty("liquidity") String liquidity) {
    super(type, orderId, time);
    this.tradeId = tradeId;
    this.tradePrice = price;
    this.filledQuantity = filledQuantity;
    this.remainingQuantity = remainingQuantity;
    this.fees = fees;
    this.liquidity = liquidity;
  }

  public LgoMatchOrderEvent(
      Long batchId,
      String type,
      String orderId,
      Date time,
      String tradeId,
      BigDecimal tradePrice,
      BigDecimal filledQuantity,
      BigDecimal remainingQuantity,
      BigDecimal fees,
      String liquidity,
      Order.OrderType orderType) {
    super(batchId, type, orderId, time);
    this.tradeId = tradeId;
    this.tradePrice = tradePrice;
    this.filledQuantity = filledQuantity;
    this.remainingQuantity = remainingQuantity;
    this.fees = fees;
    this.liquidity = liquidity;
    this.orderType = orderType;
  }

  public String getTradeId() {
    return tradeId;
  }

  public BigDecimal getTradePrice() {
    return tradePrice;
  }

  public BigDecimal getFilledQuantity() {
    return filledQuantity;
  }

  public BigDecimal getRemainingQuantity() {
    return remainingQuantity;
  }

  public BigDecimal getFees() {
    return fees;
  }

  public String getLiquidity() {
    return liquidity;
  }

  public Order.OrderType getOrderType() {
    return orderType;
  }

  public void setOrderType(Order.OrderType orderType) {
    this.orderType = orderType;
  }

  @Override
  public Order applyOnOrders(CurrencyPair currencyPair, Map<String, Order> allOrders) {
    Order matchedOrder = allOrders.get(getOrderId());
    matchedOrder.setOrderStatus(Order.OrderStatus.PARTIALLY_FILLED);
    matchedOrder.setCumulativeAmount(matchedOrder.getOriginalAmount().subtract(remainingQuantity));
    BigDecimal fee = matchedOrder.getFee() == null ? fees : matchedOrder.getFee().add(fees);
    matchedOrder.setFee(fee);
    return matchedOrder;
  }
}
