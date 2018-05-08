package org.knowm.xchange.bitmarket.service;

import java.io.IOException;
import java.util.ArrayList;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.BitMarketUtils;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketCancelResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryOperation;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryOperations;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryOperationsResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryTradesResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketTradeResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import si.mazi.rescu.IRestProxyFactory;

/** Created by krzysztoffonal on 25/05/15. */
public class BitMarketTradeServiceRaw extends BitMarketBaseService {
  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitMarketTradeServiceRaw(Exchange exchange, IRestProxyFactory restProxyFactory) {
    super(exchange, restProxyFactory);
  }

  public BitMarketOrdersResponse getBitMarketOpenOrders() throws IOException, ExchangeException {

    BitMarketOrdersResponse response =
        bitMarketAuthenticated.orders(apiKey, sign, exchange.getNonceFactory());

    if (!response.getSuccess()) {
      throw new ExchangeException(
          String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    return response;
  }

  public BitMarketTradeResponse placeBitMarketOrder(LimitOrder order)
      throws IOException, ExchangeException {

    String market = order.getCurrencyPair().toString().replace("/", "");
    String type = order.getType() == Order.OrderType.ASK ? "sell" : "buy";

    BitMarketTradeResponse response =
        bitMarketAuthenticated.trade(
            apiKey,
            sign,
            exchange.getNonceFactory(),
            market,
            type,
            order.getOriginalAmount(),
            order.getLimitPrice());

    if (!response.getSuccess()) {
      throw new ExchangeException(
          String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    return response;
  }

  public BitMarketCancelResponse cancelBitMarketOrder(String id)
      throws IOException, ExchangeException {

    BitMarketCancelResponse response =
        bitMarketAuthenticated.cancel(apiKey, sign, exchange.getNonceFactory(), Long.parseLong(id));

    if (!response.getSuccess()) {
      throw new ExchangeException(
          String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    return response;
  }

  public BitMarketHistoryTradesResponse getBitMarketTradeHistory(TradeHistoryParams params)
      throws IOException, ExchangeException {

    // default values
    String currencyPair = "BTCPLN";
    int count = 1000;
    long offset = 0;

    if (params instanceof TradeHistoryParamCurrencyPair) {
      currencyPair =
          BitMarketUtils.currencyPairToBitMarketCurrencyPair(
              ((TradeHistoryParamCurrencyPair) params).getCurrencyPair());
    }

    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }

    if (params instanceof BitMarketHistoryParams) {
      count = ((BitMarketHistoryParams) params).getCount();
    }

    BitMarketHistoryTradesResponse response =
        bitMarketAuthenticated.trades(
            apiKey, sign, exchange.getNonceFactory(), currencyPair, count, offset);

    if (!response.getSuccess()) {
      throw new ExchangeException(
          String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    return response;
  }

  public BitMarketHistoryOperationsResponse getBitMarketOperationHistory(TradeHistoryParams params)
      throws IOException, ExchangeException {

    // default values
    CurrencyPair currencyPair = CurrencyPair.BTC_PLN;
    int count = 1000;
    long offset = 0;

    if (params instanceof TradeHistoryParamCurrencyPair) {
      currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    }

    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }

    if (params instanceof BitMarketHistoryParams) {
      count = ((BitMarketHistoryParams) params).getCount();
    }

    BitMarketHistoryOperationsResponse response =
        bitMarketAuthenticated.history(
            apiKey,
            sign,
            exchange.getNonceFactory(),
            currencyPair.base.getCurrencyCode(),
            count,
            offset);

    BitMarketHistoryOperationsResponse response2 =
        bitMarketAuthenticated.history(
            apiKey,
            sign,
            exchange.getNonceFactory(),
            currencyPair.counter.getCurrencyCode(),
            count,
            offset);

    if (!response.getSuccess() || !response2.getSuccess()) {
      throw new ExchangeException(
          String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    // combine results from both historic operations - for base and coiunter currency
    int combinedTotal = response.getData().getTotal() + response2.getData().getTotal();
    ArrayList<BitMarketHistoryOperation> combinedOperations = new ArrayList<>(combinedTotal);
    combinedOperations.addAll(response.getData().getOperations());
    combinedOperations.addAll(response2.getData().getOperations());

    BitMarketHistoryOperationsResponse combinedResponse =
        new BitMarketHistoryOperationsResponse(
            true,
            new BitMarketHistoryOperations(
                combinedTotal,
                response.getData().getStart(),
                response.getData().getCount() * 2,
                combinedOperations),
            response2.getLimit(),
            0,
            null);

    return combinedResponse;
  }
}
