package org.knowm.xchange.coingi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coingi.dto.CoingiAuthenticatedRequest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class CoingiBaseService extends BaseExchangeService implements BaseService {
  protected CoingiDigest signatureCreator;

  protected CoingiBaseService(Exchange exchange) {
    super(exchange);
  }

  protected void handleAuthentication(Object obj) {
    if (obj instanceof CoingiAuthenticatedRequest) {
      CoingiAuthenticatedRequest request = (CoingiAuthenticatedRequest) obj;
      Long nonce = exchange.getNonceFactory().createValue();
      request.setToken(exchange.getExchangeSpecification().getApiKey());
      request.setNonce(nonce);
      request.setSignature(signatureCreator.sign(nonce));
    }
  }
}
