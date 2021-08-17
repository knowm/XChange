package org.knowm.xchange.ftx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.ftx.FtxAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class FtxBaseService extends BaseExchangeService implements BaseService {

  protected final FtxAuthenticated ftx;
  protected final ParamsDigest signatureCreator;

  public FtxBaseService(Exchange exchange) {
    super(exchange);

    ftx =
        ExchangeRestProxyBuilder.forInterface(
                FtxAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    signatureCreator = FtxDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
