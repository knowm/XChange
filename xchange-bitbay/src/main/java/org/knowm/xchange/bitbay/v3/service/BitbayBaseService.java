package org.knowm.xchange.bitbay.v3.service;

import java.util.Iterator;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.v3.BitbayAuthenticated;
import org.knowm.xchange.bitbay.v3.BitbayDigest;
import org.knowm.xchange.bitbay.v3.dto.BitbayBaseResponse;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BitbayBaseService extends BaseExchangeService implements BaseService {

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

    bitbayAuthenticated =
        RestProxyFactory.createProxy(
            BitbayAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    sign = BitbayDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    apiKey = exchange.getExchangeSpecification().getApiKey();
  }

  void checkError(BitbayBaseResponse response) {
    if (response.getStatus().equalsIgnoreCase("fail")) {
      List<String> errors = response.getErrors();
      if (errors == null || errors.isEmpty()) {
        throw new ExchangeException("Bitbay API unexpected error");
      }
      Iterator<String> errorsIterator = errors.iterator();
      StringBuilder message = new StringBuilder("Bitbay API error: ").append(errorsIterator.next());
      while (errorsIterator.hasNext()) {
        message.append(", ").append(errorsIterator.next());
      }
      throw new ExchangeException(message.toString());
    }
  }
}
