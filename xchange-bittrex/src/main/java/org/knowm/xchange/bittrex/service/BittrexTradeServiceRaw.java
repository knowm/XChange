package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.trade.BittrexCancelOrderResponse;
import org.knowm.xchange.bittrex.dto.trade.BittrexOpenOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexOpenOrdersResponse;
import org.knowm.xchange.bittrex.dto.trade.BittrexTradeHistoryResponse;
import org.knowm.xchange.bittrex.dto.trade.BittrexTradeResponse;
import org.knowm.xchange.bittrex.dto.trade.BittrexUserTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class BittrexTradeServiceRaw extends BittrexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public String placeBittrexMarketOrder(MarketOrder marketOrder) throws IOException {

    String pair = BittrexUtils.toPairString(marketOrder.getCurrencyPair());

    if (marketOrder.getType() == OrderType.BID) {

      BittrexTradeResponse response = bittrexAuthenticated.buymarket(apiKey, signatureCreator, exchange.getNonceFactory(), pair,
          marketOrder.getTradableAmount().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      } else {
        throw new ExchangeException(response.getMessage());
      }

    } else {

      BittrexTradeResponse response = bittrexAuthenticated.sellmarket(apiKey, signatureCreator, exchange.getNonceFactory(), pair,
          marketOrder.getTradableAmount().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      } else {
        throw new ExchangeException(response.getMessage());
      }

    }
  }

  public String placeBittrexLimitOrder(LimitOrder limitOrder) throws IOException {

    String pair = BittrexUtils.toPairString(limitOrder.getCurrencyPair());

    if (limitOrder.getType() == OrderType.BID) {
      BittrexTradeResponse response = bittrexAuthenticated.buylimit(apiKey, signatureCreator, exchange.getNonceFactory(), pair,
          limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      } else {
        throw new ExchangeException(response.getMessage());
      }

    } else {
      BittrexTradeResponse response = bittrexAuthenticated.selllimit(apiKey, signatureCreator, exchange.getNonceFactory(), pair,
          limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      } else {
        throw new ExchangeException(response.getMessage());
      }
    }
  }

  public boolean cancelBittrexLimitOrder(String uuid) throws IOException {

    BittrexCancelOrderResponse response = bittrexAuthenticated.cancel(apiKey, signatureCreator, exchange.getNonceFactory(), uuid);

    if (response.getSuccess()) {
      return true;
    } else {
      throw new ExchangeException(response.getMessage());
    }

  }

  public List<BittrexOpenOrder> getBittrexOpenOrders(OpenOrdersParams params) throws IOException {
    String ccyPair = null;

    if (params != null && params instanceof OpenOrdersParamCurrencyPair) {
      CurrencyPair currencyPair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
      ccyPair = BittrexUtils.toPairString(currencyPair);
    }

    BittrexOpenOrdersResponse response = bittrexAuthenticated.openorders(apiKey, signatureCreator, exchange.getNonceFactory(), ccyPair);

    if (response.getSuccess()) {
      return response.getBittrexOpenOrders();
    } else {
      throw new ExchangeException(response.getMessage());
    }

  }

  public List<BittrexUserTrade> getBittrexTradeHistory(CurrencyPair currencyPair) throws IOException {
    String ccyPair = null;
    if (currencyPair != null)
      ccyPair = BittrexUtils.toPairString(currencyPair);

    BittrexTradeHistoryResponse response = bittrexAuthenticated.getorderhistory(apiKey, signatureCreator, exchange.getNonceFactory(), ccyPair);

    if (response.getSuccess()) {
      return response.getResult();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

}