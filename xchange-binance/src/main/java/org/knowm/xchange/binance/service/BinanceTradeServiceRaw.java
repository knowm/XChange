package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.trade.BinanceCancelledOrder;
import org.knowm.xchange.binance.dto.trade.BinanceListenKey;
import org.knowm.xchange.binance.dto.trade.BinanceNewOrder;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.BinanceTrade;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;
import org.knowm.xchange.currency.CurrencyPair;

public class BinanceTradeServiceRaw extends BinanceBaseService {

  protected BinanceTradeServiceRaw(BinanceExchange exchange, BinanceAuthenticated binance) {
    super(exchange, binance);
  }

  public List<BinanceOrder> openOrders() throws BinanceException, IOException {
    return binance.openOrders(
        null, getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator);
  }

  public List<BinanceOrder> openOrders(CurrencyPair pair) throws BinanceException, IOException {
    return binance.openOrders(
        BinanceAdapters.toSymbol(pair),
        getRecvWindow(),
        getTimestampFactory(),
        apiKey,
        signatureCreator);
  }

  public BinanceNewOrder newOrder(
      CurrencyPair pair,
      OrderSide side,
      OrderType type,
      TimeInForce timeInForce,
      BigDecimal quantity,
      BigDecimal price,
      String newClientOrderId,
      BigDecimal stopPrice,
      BigDecimal icebergQty)
      throws IOException, BinanceException {
    return binance.newOrder(
        BinanceAdapters.toSymbol(pair),
        side,
        type,
        timeInForce,
        quantity,
        price,
        newClientOrderId,
        stopPrice,
        icebergQty,
        getRecvWindow(),
        getTimestampFactory(),
        apiKey,
        signatureCreator);
  }

  public void testNewOrder(
      CurrencyPair pair,
      OrderSide side,
      OrderType type,
      TimeInForce timeInForce,
      BigDecimal quantity,
      BigDecimal price,
      String newClientOrderId,
      BigDecimal stopPrice,
      BigDecimal icebergQty)
      throws IOException, BinanceException {
    binance.testNewOrder(
        BinanceAdapters.toSymbol(pair),
        side,
        type,
        timeInForce,
        quantity,
        price,
        newClientOrderId,
        stopPrice,
        icebergQty,
        getRecvWindow(),
        getTimestampFactory(),
        apiKey,
        signatureCreator);
  }

  public BinanceOrder orderStatus(CurrencyPair pair, long orderId, String origClientOrderId)
      throws IOException, BinanceException {
    return binance.orderStatus(
        BinanceAdapters.toSymbol(pair),
        orderId,
        origClientOrderId,
        getRecvWindow(),
        getTimestampFactory(),
        super.apiKey,
        super.signatureCreator);
  }

  public BinanceCancelledOrder cancelOrder(
      CurrencyPair pair, long orderId, String origClientOrderId, String newClientOrderId)
      throws IOException, BinanceException {
    return binance.cancelOrder(
        BinanceAdapters.toSymbol(pair),
        orderId,
        origClientOrderId,
        newClientOrderId,
        getRecvWindow(),
        getTimestampFactory(),
        super.apiKey,
        super.signatureCreator);
  }

  public List<BinanceOrder> allOrders(CurrencyPair pair, Long orderId, Integer limit)
      throws BinanceException, IOException {
    return binance.allOrders(
        BinanceAdapters.toSymbol(pair),
        orderId,
        limit,
        getRecvWindow(),
        getTimestampFactory(),
        apiKey,
        signatureCreator);
  }

  public List<BinanceTrade> myTrades(
      CurrencyPair pair, Integer limit, Long startTime, Long endTime, Long fromId)
      throws BinanceException, IOException {
    return binance.myTrades(
        BinanceAdapters.toSymbol(pair),
        limit,
        startTime,
        endTime,
        fromId,
        getRecvWindow(),
        getTimestampFactory(),
        apiKey,
        signatureCreator);
  }

  public BinanceListenKey startUserDataStream() throws IOException {
    return binance.startUserDataStream(apiKey);
  }

  public void keepAliveDataStream(String listenKey) throws IOException {
    binance.keepAliveUserDataStream(apiKey, listenKey);
  }

  public void closeDataStream(String listenKey) throws IOException {
    binance.closeUserDataStream(apiKey, listenKey);
  }
}
