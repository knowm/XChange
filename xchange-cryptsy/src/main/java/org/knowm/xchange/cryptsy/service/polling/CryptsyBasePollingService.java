package org.knowm.xchange.cryptsy.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptsy.Cryptsy;
import org.knowm.xchange.cryptsy.CryptsyAuthenticated;
import org.knowm.xchange.cryptsy.CryptsyExchange;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;
import org.knowm.xchange.cryptsy.service.CryptsyHmacPostBodyDigest;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author ObsessiveOrange
 */
public class CryptsyBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final String apiKey;
  protected final CryptsyAuthenticated cryptsyAuthenticated;
  protected final ParamsDigest signatureCreator;

  protected final Cryptsy cryptsy;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptsyBasePollingService(Exchange exchange) {

    super(exchange);

    // for private API data (trade and account)
    this.cryptsyAuthenticated = RestProxyFactory.createProxy(CryptsyAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = CryptsyHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());

    // for public API (market data)
    this.cryptsy = RestProxyFactory.createProxy(Cryptsy.class,
        (String) exchange.getExchangeSpecification().getParameter(CryptsyExchange.KEY_PUBLIC_API_URL));

  }

  @SuppressWarnings("rawtypes")
  public static <T extends CryptsyGenericReturn> T checkResult(T info) {

    if (info == null) {
      throw new ExchangeException("Cryptsy returned nothing");
    } else if (!info.isSuccess()) {
      throw new ExchangeException(info.getError());
    } else if (info.getError() != null) {
      throw new ExchangeException(info.getError());
    } else if (info.getReturnValue() == null) {
      throw new ExchangeException("Null data returned");
    }
    return info;
  }

}
