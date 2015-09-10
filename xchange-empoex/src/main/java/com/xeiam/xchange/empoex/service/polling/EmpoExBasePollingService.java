package com.xeiam.xchange.empoex.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.empoex.EmpoEx;
import com.xeiam.xchange.empoex.EmpoExAuthenticated;
import com.xeiam.xchange.empoex.EmpoExUtils;
import com.xeiam.xchange.empoex.dto.marketdata.EmpoExTicker;
import com.xeiam.xchange.empoex.service.EmpoExHmacPostBodyDigest;
import com.xeiam.xchange.empoex.service.EmpoExPayloadDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class EmpoExBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final String apiKey;
  protected final EmpoExAuthenticated empoExAuthenticated;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest payloadCreator;

  protected final EmpoEx empoEx;

  /**
   * Constructor
   *
   * @param exchange
   */
  public EmpoExBasePollingService(Exchange exchange) {

    super(exchange);

    this.empoExAuthenticated = RestProxyFactory.createProxy(EmpoExAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = EmpoExHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.payloadCreator = new EmpoExPayloadDigest();

    this.empoEx = RestProxyFactory.createProxy(EmpoEx.class, exchange.getExchangeSpecification().getSslUri());

  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();

    List<EmpoExTicker> tickers = empoExAuthenticated.getEmpoExTickers();

    for (EmpoExTicker ticker : tickers) {
      currencyPairs.add(EmpoExUtils.toCurrencyPair(ticker.getPairname()));
    }
    return currencyPairs;
  }
}
