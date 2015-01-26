package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcchina.BTCChina;
import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.btcchina.BTCChinaExchange;
import com.xeiam.xchange.btcchina.BTCChinaExchangeException;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.service.BTCChinaDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author timmolter
 */
public class BTCChinaBasePollingService<T extends BTCChina> extends BaseExchangeService implements BasePollingService {

  protected final T btcChina;
  protected final ParamsDigest signatureCreator;
  protected final SynchronizedValueFactory<Long> tonce;

  /**
   * Constructor
   *
   * @param type
   * @param exchange
   * @param tonceFactory
   */
  public BTCChinaBasePollingService(Class<T> type, Exchange exchange, SynchronizedValueFactory<Long> tonceFactory) {

    super(exchange);

    Assert.notNull(exchange.getExchangeSpecification().getSslUri(), "Exchange specification URI cannot be null");

    this.btcChina = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = BTCChinaDigest.createInstance(exchange.getExchangeSpecification().getApiKey(), exchange.getExchangeSpecification().getSecretKey());
    this.tonce = tonceFactory;
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    BTCChinaTicker btcChinaTicker = btcChina.getTicker(BTCChinaExchange.ALL_MARKET);
    Map<CurrencyPair, Ticker> tickers = BTCChinaAdapters.adaptTickers(btcChinaTicker);
    currencyPairs.addAll(tickers.keySet());

    return currencyPairs;
  }

  @SuppressWarnings("rawtypes")
  public static <T extends BTCChinaResponse> T checkResult(T returnObject) {

    if (returnObject.getError() != null) {
      throw new BTCChinaExchangeException(returnObject.getError());
    } else if (returnObject.getResult() == null) {
      throw new ExchangeException("Null data returned");
    }
    return returnObject;
  }
}
