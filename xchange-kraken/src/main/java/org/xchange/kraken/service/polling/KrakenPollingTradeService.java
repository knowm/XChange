package org.xchange.kraken.service.polling;

import java.util.Arrays;

import org.xchange.kraken.KrakenAdapters;
import org.xchange.kraken.KrakenAuthenticated;
import org.xchange.kraken.KrakenUtils;
import org.xchange.kraken.dto.trade.KrakenCancelOrderResult;
import org.xchange.kraken.dto.trade.KrakenOpenOrdersResult;
import org.xchange.kraken.dto.trade.KrakenOrderResult;
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

    KrakenOpenOrdersResult result = krakenAuthenticated.listOrders(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), null, null);
    if (!result.isSuccess()) {
      throw new ExchangeException(Arrays.toString(result.getError()));
    }
    return KrakenAdapters.adaptOpenOrders(result.getResult().getOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {

    KrakenOrderResult result =
        krakenAuthenticated.addOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), KrakenUtils.createKrakenCurrencyPair(marketOrder.getTradableIdentifier(), marketOrder
            .getTransactionCurrency()), KrakenUtils.getKrakenOrderType(marketOrder.getType()), "market", null, marketOrder.getTradableAmount().toString());
    if (!result.isSuccess()) {
      throw new ExchangeException(Arrays.toString(result.getError()));
    }
    return result.getResult().getTxid();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {

    KrakenOrderResult result =
        krakenAuthenticated.addOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), KrakenUtils.createKrakenCurrencyPair(limitOrder.getTradableIdentifier(), limitOrder
            .getTransactionCurrency()), KrakenUtils.getKrakenOrderType(limitOrder.getType()), "limit", limitOrder.getLimitPrice().getAmount().toString(), limitOrder.getTradableAmount().toString());
    if (!result.isSuccess()) {
      throw new ExchangeException(Arrays.toString(result.getError()));
    }
    return result.getResult().getTxid();
  }

  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {

    KrakenCancelOrderResult result = krakenAuthenticated.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), orderId);
    if (!result.isSuccess()) {
      return false;
    }
    else {
      return result.getResult().getCount() > 0;
    }
  }

  @Override
  public Trades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {

    throw new NotYetImplementedForExchangeException();
  }

}
