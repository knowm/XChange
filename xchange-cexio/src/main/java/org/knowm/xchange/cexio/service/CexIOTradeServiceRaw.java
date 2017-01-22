package org.knowm.xchange.cexio.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.CexIOAuthenticated;
import org.knowm.xchange.cexio.dto.trade.CexIOOpenOrders;
import org.knowm.xchange.cexio.dto.trade.CexIOOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author timmolter
 */

public class CexIOTradeServiceRaw extends CexIOBaseService {

  private final CexIOAuthenticated cexIOAuthenticated;
  private ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOTradeServiceRaw(Exchange exchange) {

    super(exchange);
    cexIOAuthenticated = RestProxyFactory.createProxy(CexIOAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    signatureCreator = CexIODigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public List<CexIOOrder> getCexIOOpenOrders(CurrencyPair currencyPair) throws IOException {

    List<CexIOOrder> cexIOOrderList = new ArrayList<CexIOOrder>();

    String tradableIdentifier = currencyPair.base.getCurrencyCode();
    String transactionCurrency = currencyPair.counter.getCurrencyCode();

    CexIOOpenOrders openOrders = cexIOAuthenticated.getOpenOrders(tradableIdentifier, transactionCurrency,
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());

    for (CexIOOrder cexIOOrder : openOrders.getOpenOrders()) {
      cexIOOrder.setTradableIdentifier(tradableIdentifier);
      cexIOOrder.setTransactionCurrency(transactionCurrency);
      cexIOOrderList.add(cexIOOrder);
    }

    return cexIOOrderList;
  }

  public List<CexIOOrder> getCexIOOpenOrders() throws IOException {

    List<CexIOOrder> cexIOOrderList = new ArrayList<CexIOOrder>();

    for (CurrencyPair currencyPair : exchange.getExchangeMetaData().getCurrencyPairs().keySet()) {
      cexIOOrderList.addAll(getCexIOOpenOrders(currencyPair));
    }
    return cexIOOrderList;
  }

  public CexIOOrder placeCexIOLimitOrder(LimitOrder limitOrder) throws IOException {

    CexIOOrder order = cexIOAuthenticated.placeOrder(limitOrder.getCurrencyPair().base.getCurrencyCode(),
        limitOrder.getCurrencyPair().counter.getCurrencyCode(), exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory(), (limitOrder.getType() == BID ? CexIOOrder.Type.buy : CexIOOrder.Type.sell), limitOrder.getLimitPrice(),
        limitOrder.getTradableAmount());
    if (order.getErrorMessage() != null) {
      throw new ExchangeException(order.getErrorMessage());
    }
    return order;
  }

  public boolean cancelCexIOOrder(String orderId) throws IOException {

    return cexIOAuthenticated
        .cancelOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), Long.parseLong(orderId))
        .equals(true);
  }

}
