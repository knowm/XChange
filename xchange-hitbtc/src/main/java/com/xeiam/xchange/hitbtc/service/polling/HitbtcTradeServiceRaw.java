package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrder;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrdersResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcTradeResponse;

public class HitbtcTradeServiceRaw extends HitbtcBasePollingService {
  private static final BigDecimal LOT_MULTIPLIER = new BigDecimal("100");

  public HitbtcTradeServiceRaw(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }

  public HitbtcOrder[] getOpenOrdersRaw() throws ExchangeException,
  NotAvailableFromExchangeException,
  NotYetImplementedForExchangeException, IOException {

    HitbtcOrdersResponse hitbtcActiveOrders = hitbtc.getHitbtcActiveOrders(signatureCreator, nextNonce(), apiKey);
    return hitbtcActiveOrders.getOrders();
  }

  public HitbtcExecutionReport placeMarketOrderRaw(MarketOrder marketOrder)
      throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    String symbol = marketOrder.getCurrencyPair().baseSymbol + marketOrder.getCurrencyPair().counterSymbol;
    String side = marketOrder.getType().equals(OrderType.BID) ? "buy" : "sell";

    long nonce = nextNonce();

    HitbtcExecutionReportResponse response = hitbtc.postHitbtcNewOrder(signatureCreator, nextNonce(), apiKey, 
        side + String.valueOf(nonce), // encoding side in client order id 
        symbol, 
        side, 
        null, 
        marketOrder.getTradableAmount().multiply(LOT_MULTIPLIER),
        "market",
        "GTC");

    return response.getExecutionReport();
  }

  public HitbtcExecutionReport placeLimitOrderRaw(LimitOrder limitOrder) throws ExchangeException,
  NotAvailableFromExchangeException,
  NotYetImplementedForExchangeException, IOException {

    String symbol = limitOrder.getCurrencyPair().baseSymbol + limitOrder.getCurrencyPair().counterSymbol;
    String side = limitOrder.getType().equals(OrderType.BID) ? "buy" : "sell";

    long nonce = nextNonce();

    HitbtcExecutionReportResponse postHitbtcNewOrder = hitbtc.postHitbtcNewOrder(
        signatureCreator, 
        nonce, 
        apiKey, 
        side + String.valueOf(nonce), // encoding side in client order id
        symbol, 
        side, 
        limitOrder.getLimitPrice(), 
        limitOrder.getTradableAmount().multiply(LOT_MULTIPLIER),
        "limit",
        "GTC");

    return postHitbtcNewOrder.getExecutionReport();
  }

  public HitbtcExecutionReportResponse cancelOrderRaw(String orderId) throws ExchangeException,
  NotAvailableFromExchangeException,
  NotYetImplementedForExchangeException, IOException {
    String originalSide = (orderId.charAt(0) == 'b') ? orderId.substring(0, 3) : orderId.substring(0, 4);

    return hitbtc.postHitbtcCancelOrder(
        signatureCreator, 
        nextNonce(), 
        apiKey, 
        orderId, 
        orderId, 
        "BTCUSD", 
        originalSide); // extract side from original order id: buy/sell
  }

  public HitbtcOwnTrade[] getTradeHistoryRaw(int startIndex, int maxResults, String symbols) throws ExchangeException,
  NotAvailableFromExchangeException,
  NotYetImplementedForExchangeException, IOException {
    
    HitbtcTradeResponse hitbtcTrades = hitbtc.getHitbtcTrades(
        signatureCreator, 
        nextNonce(), 
        apiKey, 
        "ts", 
        startIndex,
        maxResults, 
        symbols); // TODO

    return hitbtcTrades.getTrades();
  }
}
