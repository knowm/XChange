package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptotrade.CryptoTradeAuthenticated;
import com.xeiam.xchange.cryptotrade.CryptoTradeUtils;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeException;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeTransactions;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeCancelOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeHistoryQueryParams;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrderInfoReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradePlaceOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class CryptoTradeTradeServiceRaw extends CryptoTradeBasePollingService<CryptoTradeAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CryptoTradeTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(CryptoTradeAuthenticated.class, exchangeSpecification);
  }

  public CryptoTradePlaceOrderReturn placeCryptoTradeLimitOrder(LimitOrder limitOrder) throws CryptoTradeException, IOException {

    String pair = CryptoTradeUtils.getCryptoTradeCurrencyPair(limitOrder.getCurrencyPair());
    CryptoTradeOrderType type = limitOrder.getType() == Order.OrderType.BID ? CryptoTradeOrderType.Buy : CryptoTradeOrderType.Sell;
    CryptoTradePlaceOrderReturn cryptoTradePlaceOrderReturn = cryptoTradeProxy.trade(pair, type, limitOrder.getLimitPrice(), limitOrder.getTradableAmount(), apiKey, signatureCreator, nextNonce());

    return handleResponse(cryptoTradePlaceOrderReturn);
  }

  public CryptoTradeCancelOrderReturn cancelCryptoTradeOrder(long orderId) throws CryptoTradeException, IOException {

    CryptoTradeCancelOrderReturn cryptoTradeCancelOrderReturn = cryptoTradeProxy.cancelOrder(orderId, apiKey, signatureCreator, nextNonce());

    return handleResponse(cryptoTradeCancelOrderReturn);
  }

  private static final CryptoTradeHistoryQueryParams NO_QUERY_PARAMS = CryptoTradeHistoryQueryParams.getQueryParamsBuilder().build();

  public CryptoTradeTrades getCryptoTradeTradeHistory() throws CryptoTradeException, IOException {

    return getCryptoTradeTradeHistory(NO_QUERY_PARAMS);
  }

  public CryptoTradeTrades getCryptoTradeTradeHistory(CryptoTradeHistoryQueryParams queryParams) throws CryptoTradeException, IOException {

    CryptoTradeTrades trades =
        cryptoTradeProxy.getTradeHistory(queryParams.getStartId(), queryParams.getEndId(), queryParams.getStartDate(), queryParams.getEndDate(), queryParams.getCount(), queryParams.getOrdering(),
            queryParams.getCurrencyPair(), apiKey, signatureCreator, nextNonce());

    return handleResponse(trades);
  }

  public CryptoTradeOrderInfoReturn getCryptoTradeOrderInfo(long orderId) throws CryptoTradeException, IOException {

    CryptoTradeOrderInfoReturn orderInfo = cryptoTradeProxy.getOrderInfo(orderId, apiKey, signatureCreator, nextNonce());

    return handleResponse(orderInfo);
  }

  public CryptoTradeOrders getCryptoTradeOrderHistory() throws CryptoTradeException, IOException {

    return getCryptoTradeOrderHistory(NO_QUERY_PARAMS);
  }

  public CryptoTradeOrders getCryptoTradeOrderHistory(CryptoTradeHistoryQueryParams queryParams) throws CryptoTradeException, IOException {

    CryptoTradeOrders orders =
        cryptoTradeProxy.getOrderHistory(queryParams.getStartId(), queryParams.getEndId(), queryParams.getStartDate(), queryParams.getEndDate(), queryParams.getCount(), queryParams.getOrdering(),
            queryParams.getCurrencyPair(), apiKey, signatureCreator, nextNonce());

    return handleResponse(orders);
  }

  public CryptoTradeTransactions getCryptoTradeTransactionHistory() throws CryptoTradeException, IOException {

    return getCryptoTradeTransactionHistory(NO_QUERY_PARAMS);
  }

  public CryptoTradeTransactions getCryptoTradeTransactionHistory(CryptoTradeHistoryQueryParams queryParams) throws CryptoTradeException, IOException {

    CryptoTradeTransactions transactions =
        cryptoTradeProxy.getTransactionHistory(queryParams.getStartId(), queryParams.getEndId(), queryParams.getStartDate(), queryParams.getEndDate(), queryParams.getCount(), queryParams
            .getOrdering(), apiKey, signatureCreator, nextNonce());

    return handleResponse(transactions);
  }
}
