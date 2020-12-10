package org.knowm.xchange.bitbay;

import java.util.concurrent.TimeUnit;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitbay.service.BitbayAccountService;
import org.knowm.xchange.bitbay.service.BitbayMarketDataService;
import org.knowm.xchange.bitbay.service.BitbayTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author kpysniak */
public class BitbayExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory =
      new CurrentTimeIncrementalNonceFactory(TimeUnit.SECONDS);

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://bitbay.net/API/");
    exchangeSpecification.setHost("bitbay.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitbay");
    exchangeSpecification.setExchangeDescription(
        "Bitbay is a Bitcoin exchange based in Katowice, Poland.");

    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    this.marketDataService = new BitbayMarketDataService(this);
    this.tradeService = new BitbayTradeService(this);
    this.accountService = new BitbayAccountService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
