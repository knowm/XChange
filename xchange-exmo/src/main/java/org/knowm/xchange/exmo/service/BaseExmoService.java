package org.knowm.xchange.exmo.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exmo.Exmo;
import org.knowm.xchange.exmo.ExmoDigest;
import org.knowm.xchange.service.BaseExchangeService;
import si.mazi.rescu.RestProxyFactory;

public class BaseExmoService extends BaseExchangeService {
  protected final Exmo exmo;
  protected final String apiKey;
  protected final ExmoDigest signatureCreator;

  protected BaseExmoService(Exchange exchange) {
    super(exchange);

    ExchangeSpecification exchangeSpecification = exchange.getExchangeSpecification();

    this.exmo =
        RestProxyFactory.createProxy(
            Exmo.class, exchangeSpecification.getSslUri(), getClientConfig());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = ExmoDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  public static CurrencyPair adaptMarket(String market) {
    String[] parts = market.split("_");
    return new CurrencyPair(Currency.getInstance(parts[0]), Currency.getInstance(parts[1]));
  }
}
