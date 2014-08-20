package com.xeiam.xchange.cexio.service.polling;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cexio.CexIOAuthenticated;
import com.xeiam.xchange.cexio.CexIOUtils;
import com.xeiam.xchange.cexio.dto.trade.CexIOOpenOrders;
import com.xeiam.xchange.cexio.dto.trade.CexIOOrder;
import com.xeiam.xchange.cexio.service.CexIODigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * @author timmolter
 */

public class CexIOTradeServiceRaw extends CexIOBasePollingService {

  private final CexIOAuthenticated cexIOAuthenticated;
  private ParamsDigest signatureCreator;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public CexIOTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    cexIOAuthenticated = RestProxyFactory.createProxy(CexIOAuthenticated.class, exchangeSpecification.getSslUri());
    signatureCreator = CexIODigest.createInstance(exchangeSpecification.getSecretKey(), exchangeSpecification.getUserName(), exchangeSpecification.getApiKey());
  }

  public List<CexIOOrder> getCexIOOpenOrders(CurrencyPair currencyPair) throws IOException {

    List<CexIOOrder> cexIOOrderList = new ArrayList<CexIOOrder>();

    String tradableIdentifier = currencyPair.baseSymbol;
    String transactionCurrency = currencyPair.counterSymbol;

    CexIOOpenOrders openOrders = cexIOAuthenticated.getOpenOrders(tradableIdentifier, transactionCurrency, exchangeSpecification.getApiKey(), signatureCreator, CexIOUtils.nextNonce());

    for (CexIOOrder cexIOOrder : openOrders.getOpenOrders()) {
      cexIOOrder.setTradableIdentifier(tradableIdentifier);
      cexIOOrder.setTransactionCurrency(transactionCurrency);
      cexIOOrderList.add(cexIOOrder);
    }

    return cexIOOrderList;
  }

  public List<CexIOOrder> getCexIOOpenOrders() throws IOException {

    List<CexIOOrder> cexIOOrderList = new ArrayList<CexIOOrder>();

    for (CurrencyPair currencyPair : getExchangeSymbols()) {
      cexIOOrderList.addAll(getCexIOOpenOrders(currencyPair));
    }
    return cexIOOrderList;
  }

  public CexIOOrder placeCexIOLimitOrder(LimitOrder limitOrder) throws IOException {

    CexIOOrder order =
        cexIOAuthenticated.placeOrder(limitOrder.getCurrencyPair().baseSymbol, limitOrder.getCurrencyPair().counterSymbol, exchangeSpecification.getApiKey(), signatureCreator, CexIOUtils.nextNonce(),
            (limitOrder.getType() == BID ? CexIOOrder.Type.buy : CexIOOrder.Type.sell), limitOrder.getLimitPrice(), limitOrder.getTradableAmount());
    if (order.getErrorMessage() != null) {
      throw new ExchangeException(order.getErrorMessage());
    }
    return order;
  }

  public boolean cancelCexIOOrder(String orderId) throws IOException {

    return cexIOAuthenticated.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, CexIOUtils.nextNonce(), Long.parseLong(orderId)).equals(true);
  }

}
