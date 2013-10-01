package org.xchange.kraken.service.polling;

import java.util.Arrays;

import org.xchange.kraken.KrakenAdapters;
import org.xchange.kraken.KrakenAuthenticated;
import org.xchange.kraken.KrakenUtils;
import org.xchange.kraken.dto.account.KrakenBalanceResult;
import org.xchange.kraken.dto.trade.KrakenOpenOrdersResult;
import org.xchange.kraken.service.KrakenDigest;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.utils.Assert;

public class KrakenPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

  private KrakenAuthenticated krakenAuthenticated;
  private ParamsDigest signatureCreator;

  public KrakenPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    krakenAuthenticated = RestProxyFactory.createProxy(KrakenAuthenticated.class, exchangeSpecification.getSslUri());
    signatureCreator = KrakenDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
    KrakenOpenOrdersResult result = krakenAuthenticated.listOrders(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), null,null);
    if (result.getError().length > 0) {
      throw new ExchangeException(Arrays.toString(result.getError()));
    }
    return KrakenAdapters.adaptOpenOrders(result.getResult().getOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {

    KrakenBalanceResult result =
        krakenAuthenticated.addOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), KrakenUtils.createKrakenCurrencyPair(marketOrder.getTradableIdentifier(), marketOrder
            .getTransactionCurrency()), KrakenUtils.getKrakenOrderType(marketOrder.getType()), "market", null, marketOrder.getTradableAmount().toString());
    if (result.getError().length > 0) {
      throw new ExchangeException(Arrays.toString(result.getError()));
    }
    return null;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {

    throw new NotYetImplementedForExchangeException();

  }

  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {

    krakenAuthenticated.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), orderId);
    return false;
  }

  @Override
  public Trades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {

    throw new NotYetImplementedForExchangeException();
  }

}
