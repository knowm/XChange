package com.xeiam.xchange.campbx;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.campbx.service.polling.CampBXAccountService;
import com.xeiam.xchange.campbx.service.polling.CampBXMarketDataService;
import com.xeiam.xchange.campbx.service.polling.CampBXTradeService;

/**
 * Exchange for CampBX.
 * <p>
 * WARNING: Please heed the CampbBX's note:
 * </p>
 * <blockquote>
 * <p>
 * Important: Please note that using API and Website interfaces concurrently may cause login interference issues. Please use different external IP addresses, or pause the bot when you need to use the
 * Web UI.
 * </p>
 * <p>
 * Please do not abuse the API interface with brute-forcing bots, and ensure that there is at least 500 millisecond latency between two calls. We may revoke the API access without notice for accounts
 * violating this requirement.
 * </p>
 * </blockquote>
 * 
 * @author Matija Mazi
 */
public class CampBXExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://campbx.com");
    exchangeSpecification.setHost("campbx.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("CampBX");
    exchangeSpecification.setExchangeDescription("CampBX is a Bitcoin exchange registered in the USA.");
    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new CampBXMarketDataService(exchangeSpecification);
    this.pollingTradeService = new CampBXTradeService(exchangeSpecification);
    this.pollingAccountService = new CampBXAccountService(exchangeSpecification);
  }

}
