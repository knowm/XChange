package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.trade.BinanceCancelledOrder;
import org.knowm.xchange.binance.dto.trade.BinanceListenKey;
import org.knowm.xchange.binance.dto.trade.BinanceNewOrder;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.BinanceTrade;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;

public class BinanceTradeServiceRaw extends BinanceBaseService {

  protected BinanceTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BinanceOrder> openOrders(String symbol, Long recvWindow, long timestamp) throws BinanceException, IOException {
    return binance.openOrders(symbol, recvWindow, timestamp, super.apiKey, super.signatureCreator);
  }

  public BinanceNewOrder newOrder(String symbol, OrderSide side, OrderType type, TimeInForce timeInForce, BigDecimal quantity
      , BigDecimal price, String newClientOrderId, BigDecimal stopPrice, BigDecimal icebergQty
      , Long recvWindow, long timestamp) throws IOException, BinanceException {
    return binance.newOrder(symbol, side, type, timeInForce, quantity, price, newClientOrderId, stopPrice, icebergQty
        , recvWindow, timestamp, super.apiKey, super.signatureCreator);
  }

  public void testNewOrder(String symbol, OrderSide side, OrderType type, TimeInForce timeInForce, BigDecimal quantity
      , BigDecimal price, String newClientOrderId, BigDecimal stopPrice, BigDecimal icebergQty
      , Long recvWindow, long timestamp) throws IOException, BinanceException {
    binance.testNewOrder(symbol, side, type, timeInForce, quantity, price, newClientOrderId, stopPrice, icebergQty
        , recvWindow, timestamp, super.apiKey, super.signatureCreator);
  }

  public BinanceOrder orderStatus(String symbol, long orderId, String origClientOrderId, Long recvWindow, long timestamp)
      throws IOException, BinanceException {
    return binance.orderStatus(symbol, orderId, origClientOrderId, recvWindow, timestamp, super.apiKey, super.signatureCreator);
  }

  public BinanceCancelledOrder cancelOrder(String symbol, long orderId, String origClientOrderId, String newClientOrderId
      , Long recvWindow, long timestamp) throws IOException, BinanceException {
    return binance.cancelOrder(symbol, orderId, origClientOrderId, newClientOrderId, recvWindow, timestamp, super.apiKey, super.signatureCreator);
  }

  public List<BinanceOrder> allOrders(String symbol, Long orderId, Integer limit, Long recvWindow, long timestamp) throws BinanceException, IOException {
    return binance.allOrders(symbol, orderId, limit, recvWindow, timestamp, super.apiKey, super.signatureCreator);
  }

  public List<BinanceTrade> myTrades(String symbol, Integer limit, Long fromId, Long recvWindow, long timestamp) throws BinanceException, IOException {
    return binance.myTrades(symbol, limit, fromId, recvWindow, timestamp, super.apiKey, super.signatureCreator);
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
