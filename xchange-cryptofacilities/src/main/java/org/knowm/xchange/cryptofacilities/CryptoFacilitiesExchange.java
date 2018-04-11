package org.knowm.xchange.cryptofacilities;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptofacilities.service.CryptoFacilitiesAccountService;
import org.knowm.xchange.cryptofacilities.service.CryptoFacilitiesMarketDataService;
import org.knowm.xchange.cryptofacilities.service.CryptoFacilitiesTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Jean-Christophe Laruelle */
public class CryptoFacilitiesExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new CryptoFacilitiesMarketDataService(this);
    this.accountService = new CryptoFacilitiesAccountService(this);
    this.tradeService = new CryptoFacilitiesTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.cryptofacilities.com/derivatives");
    exchangeSpecification.setHost("www.cryptofacilities.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("CryptoFacilities");
    exchangeSpecification.setExchangeDescription(
        "CryptoFacilities is a bitcoin derivatives exchange operated by Crypto Facilities Ltd.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
