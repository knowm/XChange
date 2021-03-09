package org.knowm.xchange.cryptofacilities;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptofacilities.service.CryptoFacilitiesAccountService;
import org.knowm.xchange.cryptofacilities.service.CryptoFacilitiesMarketDataService;
import org.knowm.xchange.cryptofacilities.service.CryptoFacilitiesTradeService;

/** @author Jean-Christophe Laruelle */
public class CryptoFacilitiesExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new CryptoFacilitiesMarketDataService(this);
    this.accountService = new CryptoFacilitiesAccountService(this);
    this.tradeService = new CryptoFacilitiesTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://www.cryptofacilities.com/derivatives");
    exchangeSpecification.setHost("www.cryptofacilities.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("CryptoFacilities");
    exchangeSpecification.setExchangeDescription(
        "CryptoFacilities is a bitcoin derivatives exchange operated by Crypto Facilities Ltd.");
    return exchangeSpecification;
  }
}
