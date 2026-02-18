package info.bitrich.xchangestream.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import java.math.BigDecimal;
import lombok.Getter;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;
import org.knowm.xchange.dto.Order;

@Getter
public class OrderTradeUpdateBinanceWebSocketTransaction extends BaseBinanceWebSocketTransaction {

  private final OrderUpdateBinanceWebSocketTransaction updateTransaction;
  private final long transactionTime;

  public OrderTradeUpdateBinanceWebSocketTransaction(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") String eventTime,
      @JsonProperty("T") long transactionTime,
      @JsonProperty("o") OrderUpdateBinanceWebSocketTransaction updateTransaction) {
    super(eventType, eventTime);
    this.transactionTime = transactionTime;
    this.updateTransaction = updateTransaction;
  }

  @Getter
  public static class OrderUpdateBinanceWebSocketTransaction {

    private final String symbol;
    private final String clientOrderId;
    private final OrderSide side;
    private final OrderType orderType;
    private final TimeInForce timeInForce;
    private final BigDecimal originalQuantity;
    private final BigDecimal originalPrice;
    private final BigDecimal averagePrice;
    private final BigDecimal stopPrice;
    private final String executionType;
    private final OrderStatus orderStatus;
    private final Long orderId;
    private final BigDecimal lastFilledQuantity;
    private final BigDecimal filledAccumulatedQuantity;
    private final BigDecimal lastFilledPrice;
    private final String commissionAsset;
    private final String commission;
    private final long tradeTime;
    private final String tradeId;
    private final String bidsNotional;
    private final String asksNotional;
    private final boolean isMakerSide;
    private final boolean isReduceOnly;
    private final String stopPriceWorkingType;
    private final String originalOrderType;
    private final String positionSide;
    private final boolean isCloseAll;
    private final String activationPrice;
    private final String callBackRate;
    private final boolean isPriceProtectionOn;
    private final String realisedProfit;
    private final String stpMode;
    private final String priceMatchMode;
    private final long gtdTime;

    @JsonCreator
    public OrderUpdateBinanceWebSocketTransaction(
        @JsonProperty("s") String symbol,
        @JsonProperty("c") String clientOrderId,
        @JsonProperty("S") OrderSide side,
        @JsonProperty("o") OrderType orderType,
        @JsonProperty("f") TimeInForce timeInForce,
        @JsonProperty("q") BigDecimal originalQuantity,
        @JsonProperty("p") BigDecimal originalPrice,
        @JsonProperty("ap") BigDecimal averagePrice,
        @JsonProperty("sp") BigDecimal stopPrice,
        @JsonProperty("x") String executionType,
        @JsonProperty("X") OrderStatus orderStatus,
        @JsonProperty("i") Long orderId,
        @JsonProperty("l") BigDecimal lastFilledQuantity,
        @JsonProperty("z") BigDecimal filledAccumulatedQuantity,
        @JsonProperty("L") BigDecimal lastFilledPrice,
        @JsonProperty("N") String commissionAsset,
        @JsonProperty("n") String commission,
        @JsonProperty("T") long tradeTime,
        @JsonProperty("t") String tradeId,
        @JsonProperty("b") String bidsNotional,
        @JsonProperty("a") String asksNotional,
        @JsonProperty("m") boolean isMakerSide,
        @JsonProperty("R") boolean isReduceOnly,
        @JsonProperty("wt") String stopPriceWorkingType,
        @JsonProperty("ot") String originalOrderType,
        @JsonProperty("ps") String positionSide,
        @JsonProperty("cp") boolean isCloseAll,
        @JsonProperty("AP") String activationPrice,
        @JsonProperty("cr") String callBackRate,
        @JsonProperty("pP") boolean isPriceProtectionOn,
        @JsonProperty("rp") String realisedProfit,
        @JsonProperty("V") String stpMode,
        @JsonProperty("pm") String priceMatchMode,
        @JsonProperty("gtd") long gtdTime) {
      this.symbol = symbol;
      this.clientOrderId = clientOrderId;
      this.side = side;
      this.orderType = orderType;
      this.timeInForce = timeInForce;
      this.originalQuantity = originalQuantity;
      this.originalPrice = originalPrice;
      this.averagePrice = averagePrice;
      this.stopPrice = stopPrice;
      this.executionType = executionType;
      this.orderStatus = orderStatus;
      this.orderId = orderId;
      this.lastFilledQuantity = lastFilledQuantity;
      this.filledAccumulatedQuantity = filledAccumulatedQuantity;
      this.lastFilledPrice = lastFilledPrice;
      this.commissionAsset = commissionAsset;
      this.commission = commission;
      this.tradeTime = tradeTime;
      this.tradeId = tradeId;
      this.bidsNotional = bidsNotional;
      this.asksNotional = asksNotional;
      this.isMakerSide = isMakerSide;
      this.isReduceOnly = isReduceOnly;
      this.stopPriceWorkingType = stopPriceWorkingType;
      this.originalOrderType = originalOrderType;
      this.positionSide = positionSide;
      this.isCloseAll = isCloseAll;
      this.activationPrice = activationPrice;
      this.callBackRate = callBackRate;
      this.isPriceProtectionOn = isPriceProtectionOn;
      this.realisedProfit = realisedProfit;
      this.stpMode = stpMode;
      this.priceMatchMode = priceMatchMode;
      this.gtdTime = gtdTime;
    }

    public Order toOrder(boolean isFuture) {
      return BinanceAdapters.adaptOrder(
          new BinanceOrder(
              getSymbol(),
              orderId,
              clientOrderId,
              lastFilledPrice.compareTo(BigDecimal.ZERO) == 0 ? originalPrice : lastFilledPrice,
              originalQuantity,
              filledAccumulatedQuantity,
              averagePrice,
              orderStatus,
              timeInForce,
              orderType,
              side,
              tradeTime),
          isFuture);
    }
  }
}
