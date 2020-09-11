package org.knowm.xchange.bittrex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.BittrexAuthenticated;
import org.knowm.xchange.bittrex.BittrexAuthenticatedV3;
import org.knowm.xchange.bittrex.BittrexV2;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class BittrexBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final BittrexAuthenticated bittrexAuthenticated;
  protected final BittrexAuthenticatedV3 bittrexAuthenticatedV3;
  protected final BittrexV2 bittrexV2;
  protected final ParamsDigest contentCreator;
  protected final ParamsDigest signatureCreator;
  protected final BittrexDigestV3 signatureCreatorV3;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexBaseService(Exchange exchange) {
    super(exchange);

    this.bittrexAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BittrexAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    final String bittrexV3BaseUrl =
        (String) exchange.getExchangeSpecification().getParameter("rest.v3.url");
    this.bittrexAuthenticatedV3 =
        ExchangeRestProxyBuilder.forInterface(
                BittrexAuthenticatedV3.class, exchange.getExchangeSpecification())
            .baseUrl(bittrexV3BaseUrl)
            .build();
    this.bittrexV2 =
        ExchangeRestProxyBuilder.forInterface(BittrexV2.class, exchange.getExchangeSpecification())
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BittrexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.contentCreator =
        BittrexContentDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.signatureCreatorV3 =
        BittrexDigestV3.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
