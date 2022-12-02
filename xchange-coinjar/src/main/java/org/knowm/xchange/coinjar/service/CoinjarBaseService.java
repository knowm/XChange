package org.knowm.xchange.coinjar.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinjar.CoinjarData;
import org.knowm.xchange.coinjar.CoinjarTrading;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class CoinjarBaseService extends BaseExchangeService implements BaseService {

  public static final String LIVE_URL = "https://exchange.coinjar.com/";
  public static final String SANDBOX_URL = "https://exchange.coinjar-sandbox.com/";

  protected final CoinjarData coinjarData;
  protected final CoinjarTrading coinjarTrading;

  protected final String authorizationHeader;

  public CoinjarBaseService(Exchange exchange) {
    super(exchange);
    String domain;
    // Take url from either sslUri or host, fall back to live if not set
    String url;
    if (exchange.getExchangeSpecification().getSslUri() != null) {
      url = exchange.getExchangeSpecification().getSslUri();
    } else if (exchange.getExchangeSpecification().getHost() != null) {
      url = exchange.getExchangeSpecification().getHost();
    } else {
      url = LIVE_URL;
    }

    this.authorizationHeader =
        "Token token=\"" + exchange.getExchangeSpecification().getApiKey() + "\"";

    if (!url.equals(LIVE_URL) && !url.equals(SANDBOX_URL)) {
      throw new IllegalArgumentException(
          "Coinbase configuration url should be either "
              + LIVE_URL
              + " or "
              + SANDBOX_URL
              + " - got "
              + url);
    } else if (url.equals(SANDBOX_URL)) {
      domain = "coinjar-sandbox";
    } else {
      domain = "coinjar";
    }
    final String baseUrlData = "https://data.exchange." + domain + ".com/";
    this.coinjarData =
        ExchangeRestProxyBuilder.forInterface(
                CoinjarData.class, exchange.getExchangeSpecification())
            .baseUrl(baseUrlData)
            .build();

    final String baseUrlTrading = "https://api.exchange." + domain + ".com/";
    this.coinjarTrading =
        ExchangeRestProxyBuilder.forInterface(
                CoinjarTrading.class, exchange.getExchangeSpecification())
            .baseUrl(baseUrlTrading)
            .build();
  }
}
