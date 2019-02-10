package org.knowm.xchange.dvchain.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dvchain.DVChain;
import org.knowm.xchange.dvchain.dto.DVChainException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class DVChainBaseService extends BaseExchangeService implements BaseService {
  protected final DVChain dvChain;
  protected final String authToken;

  protected DVChainBaseService(Exchange exchange) {

    super(exchange);
    dvChain =
        RestProxyFactory.createProxy(
            DVChain.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    authToken = exchange.getExchangeSpecification().getSecretKey();
  }

  protected ExchangeException handleException(DVChainException exception) {
    if (exception.getMessage().contains("Internal server error")) {
      return new InternalServerException(exception);
    } else if (exception.getMessage().contains("An unknown error")) {
      return new InternalServerException(exception);
    } else {
      return new ExchangeException(exception);
    }
  }
}
