package org.knowm.xchange.binance.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BinanceBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final BinanceAuthenticated binance;
  protected final ParamsDigest signatureCreator;

  protected BinanceBaseService(Exchange exchange) {

    super(exchange);
    this.binance = RestProxyFactory.createProxy(BinanceAuthenticated.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BinanceHmacDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public long getTimestamp() throws IOException {

    return System.currentTimeMillis() + ((BinanceExchange) exchange).deltaServerTime();
  }

  public BinanceExchangeInfo getExchangeInfo() throws IOException {

    return binance.exchangeInfo();
  }

}
