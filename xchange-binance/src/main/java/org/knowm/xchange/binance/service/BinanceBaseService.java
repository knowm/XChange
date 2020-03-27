package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BinanceBaseService extends BaseExchangeService implements BaseService {

  protected final Logger LOG = LoggerFactory.getLogger(getClass());

  protected final String apiKey;
  protected final BinanceAuthenticated binance;
  protected final ParamsDigest signatureCreator;

  protected BinanceBaseService(Exchange exchange) {

    super(exchange);
    this.binance =
        RestProxyFactory.createProxy(
            BinanceAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BinanceHmacDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public long getTimestamp() throws IOException {

    long deltaServerTime = ((BinanceExchange) exchange).deltaServerTime();
    Date systemTime = new Date(System.currentTimeMillis());
    Date serverTime = new Date(systemTime.getTime() + deltaServerTime);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    LOG.trace(
        "getTimestamp: {} + {} => {}",
        df.format(systemTime),
        deltaServerTime,
        df.format(serverTime));
    return serverTime.getTime();
  }

  public Long getRecvWindow() {
    return (Long)
        exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
  }

  /**
   * After period of time, the deltaServerTime may not accurate again. Need to catch the "Timestamp
   * for this request was 1000ms ahead" exception and refresh the deltaServerTime.
   */
  public void refreshTimestamp() {
    ((BinanceExchange) exchange).clearDeltaServerTime();
  }

  public BinanceExchangeInfo getExchangeInfo() throws IOException {

    return binance.exchangeInfo();
  }
}
