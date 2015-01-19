package com.xeiam.xchange.hitbtc;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTradeServiceHelper;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcAccountService;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcMarketDataService;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcTradeService;
import com.xeiam.xchange.utils.nonce.LongTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class HitbtcExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new LongTimeNonceFactory();

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.hitbtc.com");
    exchangeSpecification.setHost("hitbtc.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Hitbtc");
    exchangeSpecification.setExchangeDescription("Hitbtc is a Bitcoin exchange.");
    exchangeSpecification.setExchangeSpecificParametersItem("demo-api", "http://demo-api.hitbtc.com");

    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new HitbtcMarketDataService(exchangeSpecification, nonceFactory);
    HitbtcTradeService hitbtcTradeService = new HitbtcTradeService(exchangeSpecification, nonceFactory);
    HitbtcAccountService hitbtcAccountService = new HitbtcAccountService(exchangeSpecification, nonceFactory);
    this.pollingTradeService = hitbtcTradeService;
    this.pollingAccountService = hitbtcAccountService;
  }

  @Override
  public void init() throws IOException, ExchangeException {
    super.init();

    Map<CurrencyPair, HitbtcTradeServiceHelper> map = ((HitbtcTradeService) pollingTradeService).getTradeServiceHelperMap();
    ((HitbtcAccountService)pollingAccountService).setTradingFeeFromTradeHelpers(map);
  }
}
