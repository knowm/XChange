package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfinex.v1.BitfinexAuthenticated;
import com.xeiam.xchange.bitfinex.v1.BitfinexOrderType;
import com.xeiam.xchange.bitfinex.v1.BitfinexUtils;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCancelOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNewOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNonceOnlyRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexPastTradesRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;

public class BitfinexTradeServiceRaw extends BitfinexBasePollingService<BitfinexAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitfinexTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BitfinexAuthenticated.class, exchangeSpecification);
  }

  public BitfinexOrderStatusResponse[] getBitfinexOpenOrders() throws IOException {

    BitfinexOrderStatusResponse[] activeOrders = bitfinex.activeOrders(apiKey, payloadCreator, signatureCreator, new BitfinexNonceOnlyRequest("/v1/orders", String.valueOf(nextNonce())));

    return activeOrders;
  }

  public BitfinexOrderStatusResponse placeBitfinexMarketOrder(MarketOrder marketOrder) throws IOException {

    String pair = BitfinexUtils.toPairString(marketOrder.getCurrencyPair());
    String type = marketOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = BitfinexOrderType.MARKET.getValue();

    BitfinexOrderStatusResponse newOrder =
        bitfinex.newOrder(apiKey, payloadCreator, signatureCreator, new BitfinexNewOrderRequest(String.valueOf(nextNonce()), pair, marketOrder.getTradableAmount(), BigDecimal.ONE, "bitfinex", type,
            orderType, false));

    return newOrder;
  }

  public BitfinexOrderStatusResponse placeBitfinexLimitOrder(LimitOrder limitOrder, BitfinexOrderType bitfinexOrderType) throws IOException {

    String pair = BitfinexUtils.toPairString(limitOrder.getCurrencyPair());
    String type = limitOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = bitfinexOrderType.toString();

    BitfinexOrderStatusResponse newOrder =
        bitfinex.newOrder(apiKey, payloadCreator, signatureCreator, new BitfinexNewOrderRequest(String.valueOf(nextNonce()), pair, limitOrder.getTradableAmount(), limitOrder.getLimitPrice(),
            "bitfinex", type, orderType, false));

    return newOrder;
  }

  public BitfinexOrderStatusResponse cancelBitfinexOrder(String orderId) throws IOException {

    BitfinexOrderStatusResponse cancelResponse = bitfinex.cancelOrders(apiKey, payloadCreator, signatureCreator, new BitfinexCancelOrderRequest(String.valueOf(nextNonce()), Integer.valueOf(orderId)));

    return cancelResponse;
  }

  public BitfinexOrderStatusResponse getBitfinexOrderStatus(String orderId) throws IOException {

    BitfinexOrderStatusResponse orderStatus = bitfinex.orderStatus(apiKey, payloadCreator, signatureCreator, new BitfinexOrderStatusRequest(String.valueOf(nextNonce()), Integer.valueOf(orderId)));

    return orderStatus;

  }

  public BitfinexTradeResponse[] getBitfinexTradeHistory(String symbol, long timestamp, int limit) throws IOException {

    BitfinexTradeResponse[] trades = bitfinex.pastTrades(apiKey, payloadCreator, signatureCreator, new BitfinexPastTradesRequest(String.valueOf(nextNonce()), symbol, timestamp, limit));

    return trades;
  }
}
