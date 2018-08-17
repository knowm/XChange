package org.knowm.xchange.coinsuper.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsuper.CoinsuperAuthenticated;
import org.knowm.xchange.coinsuper.dto.CoinsuperResponse;
import org.knowm.xchange.coinsuper.dto.account.CoinsuperUserAssetInfo;
import org.knowm.xchange.coinsuper.utils.RestApiRequestHandler;
import org.knowm.xchange.coinsuper.utils.RestRequestParam;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinsuperAccountServiceRaw extends CoinsuperBaseService {
  private final CoinsuperAuthenticated coinsuper;

  private String apiKey;
  private String secretKey;
  private SynchronizedValueFactory<Long> nonceFactory;

  public CoinsuperAccountServiceRaw(Exchange exchange) {

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
   * Obtain your own personal asset information.
   *
   * @return Object
   * @throws IOException
   */
  public CoinsuperResponse<CoinsuperUserAssetInfo> getUserAssetInfo() throws IOException {

    Map<String, String> parameters = new HashMap<String, String>();

    RestRequestParam restRequestParam =
        RestApiRequestHandler.generateRequestParam(parameters, this.apiKey, this.secretKey);

    return coinsuper.getUserAssetInfo(restRequestParam);
  }
}
