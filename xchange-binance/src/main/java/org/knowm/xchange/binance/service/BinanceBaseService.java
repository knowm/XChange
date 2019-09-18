package org.knowm.xchange.binance.service;

import java.io.IOException;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceBaseService extends BaseExchangeService implements BaseService {

  protected final Logger LOG = LoggerFactory.getLogger(getClass());

  protected final String apiKey;
  protected final BinanceAuthenticated binance;
  protected final ParamsDigest signatureCreator;

  protected BinanceBaseService(BinanceExchange exchange, BinanceAuthenticated binance) {

    super(exchange);
    this.binance = binance;
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BinanceHmacDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public Long getRecvWindow() {
    return (Long)
        exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
  }

  public SynchronizedValueFactory<Long> getTimestampFactory() {
    return ((BinanceExchange) exchange).getTimestampFactory();
  }

  public BinanceExchangeInfo getExchangeInfo() throws IOException {

    return binance.exchangeInfo();
  }
}
