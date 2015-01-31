package com.xeiam.xchange.lakebtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.lakebtc.LakeBTCUtil;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCBuyOrderRequest;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCCancelRequest;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCCancelResponse;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCOrderResponse;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCOrdersRequest;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCOrdersResponse;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCSellOrderRequest;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCTradeResponse;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCTradesRequest;

/**
 * Created by cristian.lucaci on 12/19/2014.
 */
public class LakeBTCTradeServiceRaw extends LakeBTCBasePollingService {

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
      throw new ExchangeException("LakeBTC returned an error: " + e.getMessage());
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
            new LakeBTCSellOrderRequest(String.format("\"%s,%s,%s\"", limitOrder.getLimitPrice(), limitOrder.getTradableAmount().toString(), pair)));
        break;
      }
      return newOrder;
    } catch (IOException e) {
      throw new ExchangeException("LakeBTC returned an error: " + e.getMessage());
    }
  }

  public LakeBTCCancelResponse cancelLakeBTCOrder(String orderId) throws IOException {
    try {
      return lakeBTCAuthenticated.cancelOrder(signatureCreator, exchange.getNonceFactory(), new LakeBTCCancelRequest(orderId));
    } catch (Exception e) {
      throw new ExchangeException("LakeBTC returned an error: " + e.getMessage());
    }
  }

  public LakeBTCTradeResponse[] getLakeBTCTradeHistory(long timestamp) throws IOException {

    try {
      return lakeBTCAuthenticated.pastTrades(signatureCreator, exchange.getNonceFactory(), new LakeBTCTradesRequest(String.valueOf(timestamp)));
    } catch (IOException e) {
      throw new ExchangeException("LakeBTC returned an error: " + e.getMessage());
    }
  }

  public LakeBTCOrdersResponse[] getLakeBTCOrders() throws IOException {
    return lakeBTCAuthenticated.getOrders(signatureCreator, exchange.getNonceFactory(), new LakeBTCOrdersRequest());
  }
}
