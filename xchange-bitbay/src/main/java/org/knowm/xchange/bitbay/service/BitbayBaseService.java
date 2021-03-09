package org.knowm.xchange.bitbay.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.Bitbay;
import org.knowm.xchange.bitbay.BitbayAuthenticated;
import org.knowm.xchange.bitbay.BitbayDigest;
import org.knowm.xchange.bitbay.dto.BitbayBaseResponse;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class BitbayBaseService extends BaseExchangeService implements BaseService {
  protected final Bitbay bitbay;
  final BitbayAuthenticated bitbayAuthenticated;
  final ParamsDigest sign;
  final String apiKey;

  /**
   * Constructor
   *
   * @param exchange
   */
  BitbayBaseService(Exchange exchange) {
    super(exchange);

    bitbay =
        ExchangeRestProxyBuilder.forInterface(Bitbay.class, exchange.getExchangeSpecification())
            .build();
    bitbayAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BitbayAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    sign = BitbayDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    apiKey = exchange.getExchangeSpecification().getApiKey();
  }

  void checkError(BitbayBaseResponse response) {
    if (!response.isSuccess()) {
      throw new ExchangeException(
          String.format("%d: %s", response.getCode(), response.getMessage()));
    }
  }
}
