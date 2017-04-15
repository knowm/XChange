package org.knowm.xchange.lakebtc.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.lakebtc.LakeBTCUtil;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCBuyOrderRequest;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCCancelRequest;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCCancelResponse;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCOrderResponse;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCOrdersRequest;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCOrdersResponse;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCSellOrderRequest;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCTradeResponse;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCTradesRequest;

/**
 * Created by cristian.lucaci on 12/19/2014.
 */
public class LakeBTCTradeServiceRaw extends LakeBTCBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public LakeBTCTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public LakeBTCOrderResponse placeLakeBTCMarketOrder(MarketOrder marketOrder) throws IOException {
    String pair = LakeBTCUtil.toPairString(marketOrder.getCurrencyPair());
    try {
      LakeBTCOrderResponse newOrder = null;
      switch (marketOrder.getType()) {
        case BID:
          newOrder = lakeBTCAuthenticated.placeBuyOrder(signatureCreator, exchange.getNonceFactory(),
              //unit price, amount, currency concatenated by commas
              new LakeBTCBuyOrderRequest(String.format("\"%s,%s,%s\"", "0", marketOrder.getTradableAmount().toString(), pair)));
          break;
        case ASK:
          newOrder = lakeBTCAuthenticated.placeSellOrder(signatureCreator, exchange.getNonceFactory(),
              //unit price, amount, currency concatenated by commas
              new LakeBTCSellOrderRequest(String.format("\"%s,%s,%s\"", "0", marketOrder.getTradableAmount().toString(), pair)));
          break;
      }
      return newOrder;
    } catch (IOException e) {
      throw new ExchangeException("LakeBTC returned an error", e);
    }
  }

  public LakeBTCOrderResponse placeLakeBTCLimitOrder(LimitOrder limitOrder) throws IOException {
    String pair = LakeBTCUtil.toPairString(limitOrder.getCurrencyPair());
    try {
      LakeBTCOrderResponse newOrder = null;
      switch (limitOrder.getType()) {
        case BID:
          newOrder = lakeBTCAuthenticated.placeBuyOrder(signatureCreator, exchange.getNonceFactory(),
              //unit price, amount, currency concatenated by commas
              new LakeBTCBuyOrderRequest(String.format("\"%s,%s,%s\"", limitOrder.getLimitPrice(), limitOrder.getTradableAmount().toString(), pair)));
          break;
        case ASK:
          newOrder = lakeBTCAuthenticated.placeSellOrder(signatureCreator, exchange.getNonceFactory(),
              //unit price, amount, currency concatenated by commas
              new LakeBTCSellOrderRequest(
                  String.format("\"%s,%s,%s\"", limitOrder.getLimitPrice(), limitOrder.getTradableAmount().toString(), pair)));
          break;
      }
      return newOrder;
    } catch (IOException e) {
      throw new ExchangeException("LakeBTC returned an error", e);
    }
  }

  public LakeBTCCancelResponse cancelLakeBTCOrder(String orderId) throws IOException {
    try {
      return lakeBTCAuthenticated.cancelOrder(signatureCreator, exchange.getNonceFactory(), new LakeBTCCancelRequest(orderId));
    } catch (Exception e) {
      throw new ExchangeException("LakeBTC returned an error", e);
    }
  }

  public LakeBTCTradeResponse[] getLakeBTCTradeHistory(long timestamp) throws IOException {

    try {
      return lakeBTCAuthenticated.pastTrades(signatureCreator, exchange.getNonceFactory(), new LakeBTCTradesRequest(String.valueOf(timestamp)));
    } catch (IOException e) {
      throw new ExchangeException("LakeBTC returned an error", e);
    }
  }

  public LakeBTCOrdersResponse[] getLakeBTCOrders() throws IOException {
    return lakeBTCAuthenticated.getOrders(signatureCreator, exchange.getNonceFactory(), new LakeBTCOrdersRequest());
  }
}
