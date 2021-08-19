package com.knowm.xchange.serum;

import com.knowm.xchange.serum.SerumConfigs.Solana;
import com.knowm.xchange.serum.service.SerumMarketDataService;
import com.knowm.xchange.serum.service.SerumMarketDataServiceRaw;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerumExchange extends BaseExchange implements Exchange {

  protected final Logger logger = LoggerFactory.getLogger(SerumExchange.class);

  @Override
  protected void initServices() {
    this.marketDataService = new SerumMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri(Solana.MAINNET.restUrl());
    exchangeSpecification.setHost("projectserum.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Serum");
    exchangeSpecification.setExchangeDescription(
        "Serum is a decentralized cryptocurrency exchange built on Solana.");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() {
    try {
      SerumAdapters.loadMarkets((SerumMarketDataServiceRaw) this.marketDataService);
    } catch (Exception e) {
      logger.error("Unable to load markets", e);
    }
  }
}
