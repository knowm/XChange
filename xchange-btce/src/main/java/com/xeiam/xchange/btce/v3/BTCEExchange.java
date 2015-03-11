package com.xeiam.xchange.btce.v3;

import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.dto.BTCEMetaData;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEExchangeInfo;
import com.xeiam.xchange.btce.v3.service.polling.BTCEAccountService;
import com.xeiam.xchange.btce.v3.service.polling.BTCEMarketDataService;
import com.xeiam.xchange.btce.v3.service.polling.BTCETradeService;
import com.xeiam.xchange.utils.nonce.TimestampIncrementingNonceFactory;

public class BTCEExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new TimestampIncrementingNonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new BTCEMarketDataService(this);
    this.pollingAccountService = new BTCEAccountService(this);
    this.pollingTradeService = new BTCETradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://btc-e.com");
    exchangeSpecification.setHost("btc-e.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BTC-e");
    exchangeSpecification.setExchangeDescription("BTC-e is a Bitcoin exchange registered in Russia.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  private BTCEMetaData btceMetaData;

  @Override
  protected void loadMetaData(InputStream is) {
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    try {
      btceMetaData = mapper.readValue(is, BTCEMetaData.class);
      logger.debug(btceMetaData.toString());
      BTCEMarketDataService marketDataService = (BTCEMarketDataService) pollingMarketDataService;
      BTCEExchangeInfo btceInfo = marketDataService.getBTCEInfo();
      metaData = BTCEAdapters.toMetaData(btceInfo, btceMetaData);
    } catch (Exception e) {
      logger.warn("An exception occurred while loading the metadata file from the file. This may lead to unexpected results.", e);
    }

  }

  public BTCEMetaData getBtceMetaData() {
    return btceMetaData;
  }
}
