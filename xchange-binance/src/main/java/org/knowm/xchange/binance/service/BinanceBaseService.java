package org.knowm.xchange.binance.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.Binance;
import org.knowm.xchange.binance.BinanceDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class BinanceBaseService<T extends Binance> extends BaseExchangeService implements BaseService {

  protected final T service;
  protected final BinanceDigest signatureCreator;
  protected final String apiKey;

  public BinanceBaseService(Class<T> type, Exchange exchange) {
    super(exchange);

    this.service = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BinanceDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), apiKey);
  }
}
