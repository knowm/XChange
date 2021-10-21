package info.bitrich.xchangestream.binance.futures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.ProductBinanceWebSocketTransaction;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.*;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.PositionSide;
import org.knowm.xchange.binance.futures.dto.trade.WorkingType;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;

import java.math.BigDecimal;
import java.util.Date;

public class OrderTradeUpdateBinanceUserTransaction extends BaseBinanceWebSocketTransaction {

  protected final long transactionTime;
  protected final OrderTradeUpdate orderTradeUpdate;

  public OrderTradeUpdateBinanceUserTransaction(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") String eventTime,
      @JsonProperty("T") long transactionTime,
      @JsonProperty("o") OrderTradeUpdate orderTradeUpdate) {
    super(eventType, eventTime);
    this.transactionTime = transactionTime;
    this.orderTradeUpdate = orderTradeUpdate;
  }

  public OrderTradeUpdate getOrderTradeUpdate() {
    return orderTradeUpdate;
  }

  public UserTrade toUserTrade() {
    return new UserTrade.Builder()
            .type(BinanceAdapters.convert(orderTradeUpdate.getSide()))
            .originalAmount(orderTradeUpdate.getLastExecutedQuantity())
            .currencyPair(BinanceAdapters.adaptSymbol(orderTradeUpdate.getSymbol()))
            .price(orderTradeUpdate.getLastExecutedPrice())
            .timestamp(getEventTime())
            .id(Long.toString(orderTradeUpdate.getTradeId()))
            .orderId(Long.toString(orderTradeUpdate.getOrderId()))
            .feeAmount(orderTradeUpdate.getCommissionAmount())
            .feeCurrency(Currency.getInstance(orderTradeUpdate.getCommissionAsset()))
            .orderUserReference(orderTradeUpdate.getClientOrderId())
            .build();
  }

  public Order toOrder() {
    return BinanceFuturesAdapter.adaptOrder(new BinanceFuturesOrder(
            orderTradeUpdate.getAveragePrice(),
            orderTradeUpdate.getClientOrderId(),
            orderTradeUpdate.getCumulativeFilledQuantity(),
            orderTradeUpdate.getCumulativeFilledQuantity(),
            orderTradeUpdate.getOrderId(),
            orderTradeUpdate.getOrderQuantity(),
            orderTradeUpdate.getOriginalOrderType(),
            orderTradeUpdate.getOrderPrice(),
            orderTradeUpdate.isReduceOnly(),
            orderTradeUpdate.getSide(),
            orderTradeUpdate.getPositionSide(),
            orderTradeUpdate.getCurrentOrderStatus(),
            orderTradeUpdate.getStopPrice(),
            orderTradeUpdate.isCloseAll(),
            orderTradeUpdate.getSymbol(),
            orderTradeUpdate.getTimestamp(),
            orderTradeUpdate.getTimeInForce(),
            orderTradeUpdate.getOrderType(),
            orderTradeUpdate.getActivationPrice(),
            orderTradeUpdate.getCallbackRate(),
            transactionTime,
            orderTradeUpdate.getStopPriceWorkingType(),
            false));
  }
}
