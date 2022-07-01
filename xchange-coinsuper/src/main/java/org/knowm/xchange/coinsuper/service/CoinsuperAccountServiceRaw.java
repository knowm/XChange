package org.knowm.xchange.coinsuper.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinsuper.CoinsuperAuthenticated;
import org.knowm.xchange.coinsuper.dto.CoinsuperResponse;
import org.knowm.xchange.coinsuper.dto.account.CoinsuperUserAssetInfo;
import org.knowm.xchange.coinsuper.utils.RestApiRequestHandler;
import org.knowm.xchange.coinsuper.utils.RestRequestParam;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinsuperAccountServiceRaw extends CoinsuperBaseService {
  private final CoinsuperAuthenticated coinsuper;

  private final String apiKey;
  private final String secretKey;
  private final SynchronizedValueFactory<Long> nonceFactory;

  public CoinsuperAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.coinsuper =
        ExchangeRestProxyBuilder.forInterface(
                CoinsuperAuthenticated.class, exchange.getExchangeSpecification())
            .build();

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

    Map<String, String> parameters = new HashMap<>();

    RestRequestParam restRequestParam =
        RestApiRequestHandler.generateRequestParam(parameters, this.apiKey, this.secretKey);

    return coinsuper.getUserAssetInfo(restRequestParam);
  }
}
