package com.xeiam.xchange.empoex.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.empoex.EmpoEx;
import com.xeiam.xchange.empoex.EmpoExUtils;
import com.xeiam.xchange.empoex.dto.marketdata.EmpoExTicker;
import com.xeiam.xchange.empoex.service.EmpoExHmacPostBodyDigest;
import com.xeiam.xchange.empoex.service.EmpoExPayloadDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class EmpoExBasePollingService<T extends EmpoEx> extends BaseExchangeService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L;
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final String apiKey;
  protected final T empoex;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest payloadCreator;

  /**
   * Constructor
   *
   * @param type
   * @param exchange
   */
  public EmpoExBasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);

    this.empoex = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = EmpoExHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.payloadCreator = new EmpoExPayloadDigest();
  }

  protected int nextNonce() {

    return lastNonce.incrementAndGet();
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();

    List<EmpoExTicker> tickers = empoex.getEmpoExTickers();

    for (EmpoExTicker ticker : tickers) {
      currencyPairs.add(EmpoExUtils.toCurrencyPair(ticker.getPairname()));
    }
    return currencyPairs;
  }
}
