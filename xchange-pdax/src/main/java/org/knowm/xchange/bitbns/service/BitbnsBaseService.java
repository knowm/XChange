package org.knowm.xchange.bitbns.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbns.Pdax;
import org.knowm.xchange.bitbns.dto.BitbnsException;
import org.knowm.xchange.bitbns.dto.BitbnsHmacPostBodyDigest;
import org.knowm.xchange.bitbns.dto.BitbnsPayloadDigest;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BitbnsBaseService extends BaseExchangeService implements BaseService {

  protected final Pdax pdax;
  protected final String apiKey;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest payloadCreator;

  protected BitbnsBaseService(Exchange exchange) {
    super(exchange);
    pdax =
        RestProxyFactory.createProxy(
            Pdax.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BitbnsHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.payloadCreator = new BitbnsPayloadDigest();
  }

  protected ExchangeException handleException(BitbnsException e) {
    if (e.getMessage().contains("due to insufficient funds")
        || e.getMessage().contains("you do not have enough available"))
      return new FundsExceededException(e);

    return new ExchangeException(e);
  }
}
