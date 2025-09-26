package info.bitrich.xchangestream.binance.dto.trade;

import static org.knowm.xchange.binance.BinanceAdapters.adaptSymbol;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.dto.trade.UserTrade;

@Getter
public class TradeLiteBinanceWebsocketTransaction extends BaseBinanceWebSocketTransaction {

  private final long transactionTime;
  private final String symbol;
  private final BigDecimal quantity;
  private final BigDecimal price;
  private final boolean isMaker;
  private final String clientOrderId;
  private final OrderSide side;
  private final BigDecimal lastFilledPrice;
  private final BigDecimal lastFilledQuantity;
  private final long tradeId;
  private final long orderId;

  public TradeLiteBinanceWebsocketTransaction(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") String eventTime,
      @JsonProperty("T") long transactionTime,
      @JsonProperty("s") String symbol,
      @JsonProperty("q") BigDecimal quantity,
      @JsonProperty("p") BigDecimal price,
      @JsonProperty("m") boolean isMaker,
      @JsonProperty("c") String clientOrderId,
      @JsonProperty("S") OrderSide side,
      @JsonProperty("L") BigDecimal lastFilledPrice,
      @JsonProperty("l") BigDecimal lastFilledQuantity,
      @JsonProperty("t") long tradeId,
      @JsonProperty("i") long orderId) {
    super(eventType, eventTime);
    this.transactionTime = transactionTime;
    this.symbol = symbol;
    this.quantity = quantity;
    this.price = price;
    this.isMaker = isMaker;
    this.clientOrderId = clientOrderId;
    this.side = side;
    this.lastFilledPrice = lastFilledPrice;
    this.lastFilledQuantity = lastFilledQuantity;
    this.tradeId = tradeId;
    this.orderId = orderId;
  }

  public UserTrade toUserTrade(boolean isFuture) {
    return UserTrade.builder()
        .type(BinanceAdapters.convert(side))
        .originalAmount(lastFilledQuantity)
        .instrument(adaptSymbol(symbol, isFuture))
        .price(lastFilledPrice)
        .timestamp(new Date(transactionTime))
        .id(Long.toString(tradeId))
        .orderId(Long.toString(orderId))
        .orderUserReference(clientOrderId)
        .build();
  }
}
