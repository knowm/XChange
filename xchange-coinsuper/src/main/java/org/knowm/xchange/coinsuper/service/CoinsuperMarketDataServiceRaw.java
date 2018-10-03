package org.knowm.xchange.coinsuper.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsuper.CoinsuperAuthenticated;
import org.knowm.xchange.coinsuper.dto.CoinsuperResponse;
import org.knowm.xchange.coinsuper.dto.marketdata.CoinsuperOrderbook;
import org.knowm.xchange.coinsuper.dto.marketdata.CoinsuperPair;
import org.knowm.xchange.coinsuper.dto.marketdata.CoinsuperTicker;
import org.knowm.xchange.coinsuper.utils.RestApiRequestHandler;
import org.knowm.xchange.coinsuper.utils.RestRequestParam;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinsuperMarketDataServiceRaw extends CoinsuperBaseService {
  private final CoinsuperAuthenticated coinsuper;

  private String apiKey;
  private String secretKey;
  private SynchronizedValueFactory<Long> nonceFactory;

  public CoinsuperMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    this.coinsuper =
        RestProxyFactory.createProxy(
            CoinsuperAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    this.apiKey = super.apiKey;
    this.secretKey = super.secretKey;
    this.nonceFactory = exchange.getNonceFactory();
  }

  /**
   * @return Object
   * @throws IOException
   */
  public CoinsuperResponse<List<CoinsuperPair>> getSymbolList(Map<String, String> data)
      throws IOException {

    RestRequestParam parameters =
        RestApiRequestHandler.generateRequestParam(data, this.apiKey, this.secretKey);

    return coinsuper.getSymbolList(parameters);
  }

  /**
   * @return Object
   * @throws IOException
   */
  public CoinsuperResponse<CoinsuperOrderbook> getCoinsuperOrderBooks(
      Map<String, String> parameters) throws IOException {

    RestRequestParam restRequestParam =
        RestApiRequestHandler.generateRequestParam(parameters, this.apiKey, this.secretKey);

    return coinsuper.getOrderBooks(restRequestParam);
  }

  /**
   * marketDepth
   *
   * @return Object
   * @throws IOException
   */
  public CoinsuperResponse<CoinsuperOrderbook> marketDepth(Map<String, String> parameters)
      throws IOException {

    RestRequestParam restRequestParam =
        RestApiRequestHandler.generateRequestParam(parameters, this.apiKey, this.secretKey);

    return coinsuper.marketDepth(restRequestParam);
  }

  /**
   * getCoinsuperTicker
   *
   * @return Object
   * @throws IOException
   */
  public CoinsuperResponse<List<CoinsuperTicker>> getCoinsuperTicker(CurrencyPair currencyPair)
      throws IOException {

    Map<String, String> data = new HashMap<String, String>();
    data.put("symbol", currencyPair.toString());

    RestRequestParam parameters =
        RestApiRequestHandler.generateRequestParam(data, this.apiKey, this.secretKey);

    return coinsuper.getTickers(parameters);
  }

  /**
   * getCoinsuperTicker
   *
   * @return Object
   * @throws IOException
   */
  public Object getKlines(CurrencyPair currencyPair) throws IOException {

    Map<String, String> data = new HashMap<String, String>();
    data.put("symbol", currencyPair.toString());

    RestRequestParam parameters =
        RestApiRequestHandler.generateRequestParam(data, this.apiKey, this.secretKey);

    return coinsuper.getKlines(parameters);
  }
}
