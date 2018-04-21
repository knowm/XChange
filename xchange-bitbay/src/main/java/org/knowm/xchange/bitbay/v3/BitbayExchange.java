package org.knowm.xchange.bitbay.v3;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitbay.v3.service.BitbayAccountService;
import org.knowm.xchange.bitbay.v3.service.BitbayTradeService;
import org.knowm.xchange.utils.nonce.CurrentTime1000NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Initial support for the new BitBay API. Official documentation isn't even out yet so use this at
 * your own risk. Currently however this is the only API that allowes you to download a users full
 * trade history.
 *
 * @author walec51
 */
public class BitbayExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTime1000NonceFactory();

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.bitbay.net");
    exchangeSpecification.setHost("api.bitbay.net");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Bitbay");
    exchangeSpecification.setExchangeDescription(
        "Bitbay is a Bitcoin exchange based in Katowice, Poland.");

    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    this.tradeService = new BitbayTradeService(this);
    this.accountService = new BitbayAccountService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
