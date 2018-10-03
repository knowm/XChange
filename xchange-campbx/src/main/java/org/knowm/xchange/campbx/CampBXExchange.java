package org.knowm.xchange.campbx;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.campbx.service.CampBXAccountService;
import org.knowm.xchange.campbx.service.CampBXMarketDataService;
import org.knowm.xchange.campbx.service.CampBXTradeService;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Exchange for CampBX.
 *
 * <p>WARNING: Please heed the CampbBX's note:
 *
 * <blockquote>
 *
 * <p>Important: Please note that using API and Website interfaces concurrently may cause login
 * interference issues. Please use different external IP addresses, or pause the bot when you need
 * to use the Web UI.
 *
 * <p>Please do not abuse the API interface with brute-forcing bots, and ensure that there is at
 * least 500 millisecond latency between two calls. We may revoke the API access without notice for
 * accounts violating this requirement.
 *
 * </blockquote>
 *
 * @author Matija Mazi
 */
public class CampBXExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://campbx.com");
    exchangeSpecification.setHost("campbx.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("CampBX");
    exchangeSpecification.setExchangeDescription(
        "CampBX is a Bitcoin exchange registered in the USA.");
    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    this.marketDataService = new CampBXMarketDataService(this);
    this.tradeService = new CampBXTradeService(this);
    this.accountService = new CampBXAccountService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // CampBX doesn't use a none on their authenticated API
    return null;
  }
}
