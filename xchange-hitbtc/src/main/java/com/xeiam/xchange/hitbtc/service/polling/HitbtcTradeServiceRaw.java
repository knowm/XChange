package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.hitbtc.HitbtcAuthenticated;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrder;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrdersResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcTradeResponse;

public class HitbtcTradeServiceRaw extends HitbtcBasePollingService<HitbtcAuthenticated> {

  private static final BigDecimal LOT_MULTIPLIER = new BigDecimal("100");

  public HitbtcTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(HitbtcAuthenticated.class, exchangeSpecification);
  }

  public HitbtcOrdersResponse getOpenOrdersRawBaseResponse() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcOrdersResponse hitbtcActiveOrders = hitbtc.getHitbtcActiveOrders(signatureCreator, valueFactory, apiKey);
    return hitbtcActiveOrders;
  }

  public HitbtcOrder[] getOpenOrdersRaw() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcOrdersResponse hitbtcActiveOrders = getOpenOrdersRawBaseResponse();
    return hitbtcActiveOrders.getOrders();
  }

  public HitbtcOrdersResponse getRecentOrdersRawBaseResponse(int max_results) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcOrdersResponse hitbtcActiveOrders = hitbtc.getHitbtcRecentOrders(signatureCreator, valueFactory, apiKey, max_results);
    return hitbtcActiveOrders;
  }

  public HitbtcOrder[] getRecentOrdersRaw(int max_results) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcOrdersResponse hitbtcActiveOrders = getRecentOrdersRawBaseResponse(max_results);
    return hitbtcActiveOrders.getOrders();
  }


  public HitbtcExecutionReportResponse placeMarketOrderRawBaseResponse(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    String symbol = marketOrder.getCurrencyPair().baseSymbol + marketOrder.getCurrencyPair().counterSymbol;

    long nonce = valueFactory.createValue();
    String side = getSide(marketOrder.getType());
    String orderId = createId(marketOrder, nonce);

    HitbtcExecutionReportResponse response =
        hitbtc.postHitbtcNewOrder(signatureCreator, valueFactory, apiKey, orderId, symbol, side, null, marketOrder.getTradableAmount().multiply(LOT_MULTIPLIER), "market", "GTC");

    return response;
  }

  public HitbtcExecutionReport placeMarketOrderRaw(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcExecutionReportResponse response = placeMarketOrderRawBaseResponse(marketOrder);
    return response.getExecutionReport();
  }

  public HitbtcExecutionReport placeLimitOrderRaw(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    String symbol = createSymbol(limitOrder.getCurrencyPair());
    long nonce = valueFactory.createValue();
    String side = getSide(limitOrder.getType());
    String orderId = createId(limitOrder, nonce);

    HitbtcExecutionReportResponse postHitbtcNewOrder =
        hitbtc.postHitbtcNewOrder(signatureCreator, valueFactory, apiKey, orderId, symbol, side, limitOrder.getLimitPrice(), limitOrder.getTradableAmount().multiply(LOT_MULTIPLIER), "limit", "GTC");

    return postHitbtcNewOrder.getExecutionReport();
  }

  public HitbtcExecutionReportResponse cancelOrderRaw(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    String originalSide = getSide(readOrderType(orderId));
    String symbol = readSymbol(orderId);

    return hitbtc.postHitbtcCancelOrder(signatureCreator, valueFactory, apiKey, orderId, orderId, symbol, originalSide); // extract symbol and side from original order id: buy/sell
  }

  public HitbtcTradeResponse getTradeHistoryRawBaseResponse(int startIndex, int maxResults, String symbols) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException,
      IOException {

    HitbtcTradeResponse hitbtcTrades = hitbtc.getHitbtcTrades(signatureCreator, valueFactory, apiKey, "ts", startIndex, maxResults, symbols, "desc", null, null);
    return hitbtcTrades;
  }

  public HitbtcOwnTrade[] getTradeHistoryRaw(int startIndex, int maxResults, String symbols) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException,
    IOException {

    HitbtcTradeResponse hitbtcTrades = getTradeHistoryRawBaseResponse(startIndex, maxResults, symbols);
    return hitbtcTrades.getTrades();
  }

  private static String getSide(OrderType type) {

    return type == OrderType.BID ? "buy" : "sell";
  }

  private static String createSymbol(CurrencyPair pair) {

    return pair.baseSymbol + pair.counterSymbol;
  }

  private String createId(Order order, long nonce) {

    // encoding side in client order id
    return order.getId() == null ? order.getType() + createSymbol(order.getCurrencyPair()) + nonce : order.getId();
  }

  private OrderType readOrderType(String orderId) {

    return orderId.charAt(0) == 'A' ? OrderType.ASK : OrderType.BID;
  }

  public static String readSymbol(String orderId) {

    return orderId.substring(3, 9);
  }

}
