package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceAdapters;
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

  protected BinanceTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BinanceOrder> openOrders(Long recvWindow, long timestamp)
      throws BinanceException, IOException {
    return binance.openOrders(null, recvWindow, timestamp, super.apiKey, super.signatureCreator);
  }

  public List<BinanceOrder> openOrders(CurrencyPair pair, Long recvWindow, long timestamp)
      throws BinanceException, IOException {
    return binance.openOrders(
        BinanceAdapters.toSymbol(pair),
        recvWindow,
        timestamp,
        super.apiKey,
        super.signatureCreator);
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
      BigDecimal icebergQty,
      Long recvWindow,
      long timestamp)
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
        recvWindow,
        timestamp,
        super.apiKey,
        super.signatureCreator);
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
      BigDecimal icebergQty,
      Long recvWindow,
      long timestamp)
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
        recvWindow,
        timestamp,
        super.apiKey,
        super.signatureCreator);
  }

  public BinanceOrder orderStatus(
      CurrencyPair pair, long orderId, String origClientOrderId, Long recvWindow, long timestamp)
      throws IOException, BinanceException {
    return binance.orderStatus(
        BinanceAdapters.toSymbol(pair),
        orderId,
        origClientOrderId,
        recvWindow,
        timestamp,
        super.apiKey,
        super.signatureCreator);
  }

  public BinanceCancelledOrder cancelOrder(
      CurrencyPair pair,
      long orderId,
      String origClientOrderId,
      String newClientOrderId,
      Long recvWindow,
      long timestamp)
      throws IOException, BinanceException {
    return binance.cancelOrder(
        BinanceAdapters.toSymbol(pair),
        orderId,
        origClientOrderId,
        newClientOrderId,
        recvWindow,
        timestamp,
        super.apiKey,
        super.signatureCreator);
  }

  public List<BinanceOrder> allOrders(
      CurrencyPair pair, Long orderId, Integer limit, Long recvWindow, long timestamp)
      throws BinanceException, IOException {
    return binance.allOrders(
        BinanceAdapters.toSymbol(pair),
        orderId,
        limit,
        recvWindow,
        timestamp,
        super.apiKey,
        super.signatureCreator);
  }

  public List<BinanceTrade> myTrades(
      CurrencyPair pair, Integer limit, Long fromId, Long recvWindow, long timestamp)
      throws BinanceException, IOException {
    return binance.myTrades(
        BinanceAdapters.toSymbol(pair),
        limit,
        fromId,
        recvWindow,
        timestamp,
        super.apiKey,
        super.signatureCreator);
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
