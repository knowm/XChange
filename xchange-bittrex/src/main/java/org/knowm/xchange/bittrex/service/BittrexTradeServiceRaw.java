package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.account.BittrexOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexOpenOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexUserTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
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
      return bittrexAuthenticated
          .buymarket(
              apiKey,
              signatureCreator,
              exchange.getNonceFactory(),
              pair,
              marketOrder.getOriginalAmount().toPlainString())
          .getResult()
          .getUuid();
    } else {
      return bittrexAuthenticated
          .sellmarket(
              apiKey,
              signatureCreator,
              exchange.getNonceFactory(),
              pair,
              marketOrder.getOriginalAmount().toPlainString())
          .getResult()
          .getUuid();
    }
  }

  public String placeBittrexLimitOrder(LimitOrder limitOrder) throws IOException {

    String pair = BittrexUtils.toPairString(limitOrder.getCurrencyPair());

    if (limitOrder.getType() == OrderType.BID) {
      return bittrexAuthenticated
          .buylimit(
              apiKey,
              signatureCreator,
              exchange.getNonceFactory(),
              pair,
              limitOrder.getOriginalAmount().toPlainString(),
              limitOrder.getLimitPrice().toPlainString())
          .getResult()
          .getUuid();
    } else {
      return bittrexAuthenticated
          .selllimit(
              apiKey,
              signatureCreator,
              exchange.getNonceFactory(),
              pair,
              limitOrder.getOriginalAmount().toPlainString(),
              limitOrder.getLimitPrice().toPlainString())
          .getResult()
          .getUuid();
    }
  }

  public boolean cancelBittrexLimitOrder(String uuid) throws IOException {

    bittrexAuthenticated.cancel(apiKey, signatureCreator, exchange.getNonceFactory(), uuid);
    return true;
  }

  public List<BittrexOpenOrder> getBittrexOpenOrders(OpenOrdersParams params) throws IOException {
    String ccyPair = null;

    if (params != null && params instanceof OpenOrdersParamCurrencyPair) {
      CurrencyPair currencyPair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
      if (currencyPair != null) {
        ccyPair = BittrexUtils.toPairString(currencyPair);
      }
    }

    return bittrexAuthenticated
        .openorders(apiKey, signatureCreator, exchange.getNonceFactory(), ccyPair)
        .getResult();
  }

  public List<BittrexUserTrade> getBittrexTradeHistory(CurrencyPair currencyPair)
      throws IOException {
    String ccyPair = null;
    if (currencyPair != null) ccyPair = BittrexUtils.toPairString(currencyPair);

    return bittrexAuthenticated
        .getorderhistory(apiKey, signatureCreator, exchange.getNonceFactory(), ccyPair)
        .getResult();
  }

  public BittrexOrder getBittrexOrder(String uuid) throws IOException {
    return bittrexAuthenticated
        .getOrder(apiKey, signatureCreator, exchange.getNonceFactory(), uuid)
        .getResult();
  }
}
