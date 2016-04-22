package org.knowm.xchange.cryptofacilities;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptofacilities.service.polling.CryptoFacilitiesAccountService;
import org.knowm.xchange.cryptofacilities.service.polling.CryptoFacilitiesMarketDataService;
import org.knowm.xchange.cryptofacilities.service.polling.CryptoFacilitiesTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new CryptoFacilitiesMarketDataService(this);
    this.pollingAccountService = new CryptoFacilitiesAccountService(this);
    this.pollingTradeService = new CryptoFacilitiesTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.cryptofacilities.com/derivatives");
    exchangeSpecification.setHost("www.cryptofacilities.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("CryptoFacilities");
    exchangeSpecification.setExchangeDescription("CryptoFacilities is a bitcoin derivatives exchange operated by Crypto Facilities Ltd.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
