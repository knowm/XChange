package org.knowm.xchange.binance;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ResilienceRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceFutureTimestampFactory implements SynchronizedValueFactory<Long> {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceFutureTimestampFactory.class);

  private final BinanceFutures binance;
  private final ExchangeSpecification.ResilienceSpecification resilienceSpecification;
  private final ResilienceRegistries resilienceRegistries;

  private Long deltaServerTimeExpire;
  private Long deltaServerTime;

  // TODO if possible, make this extend BinanceTimestampFactory

  public BinanceFutureTimestampFactory(BinanceFutures binance, ExchangeSpecification.ResilienceSpecification resilienceSpecification,
                                       ResilienceRegistries resilienceRegistries) {
    this.binance = binance;
    this.resilienceSpecification = resilienceSpecification;
    this.resilienceRegistries = resilienceRegistries;
  }

  @Override
  public Long createValue() {

    return System.currentTimeMillis();
  }

  public void clearDeltaServerTime() {
    deltaServerTime = null;
  }

}
