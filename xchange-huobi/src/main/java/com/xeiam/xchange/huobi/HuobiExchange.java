package com.xeiam.xchange.huobi;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.huobi.service.polling.BitVcAccountService;
import com.xeiam.xchange.huobi.service.polling.BitVcTradeServiceRaw;
import com.xeiam.xchange.huobi.service.polling.GenericTradeService;
import com.xeiam.xchange.huobi.service.polling.HuobiAccountService;
import com.xeiam.xchange.huobi.service.polling.HuobiMarketDataService;
import com.xeiam.xchange.huobi.service.polling.HuobiTradeServiceRaw;

/**
 * By default when instantiating this exchange market data and trading goes via Huobi.
 * If the flag HuobiExchange.HUOBI_MARKET_DATA is true then market data will go through Huobi still
 * and trade execution and account information will go through BitVC.
 *
 */
public class HuobiExchange extends BaseExchange implements Exchange {

  public static final String TRADE_PASSWORD_PARAMETER = "trade_password";
  
  /** Potentially different market data endpoints should be settable */
  public static final String HUOBI_MARKET_DATA = "huobi_uri_marketdata";  
  public static final String USE_BITVC = "use_bitvc";

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    if (exchangeSpecification.getExchangeSpecificParametersItem(USE_BITVC).equals(true)) {
      exchangeSpecification.setSslUri("https://api.bitvc.com");
    }

    pollingMarketDataService = new HuobiMarketDataService(this);
    if (exchangeSpecification.getApiKey() != null) {
      if ((Boolean) exchangeSpecification.getExchangeSpecificParametersItem(USE_BITVC)) {
        pollingAccountService = new BitVcAccountService(this);
        pollingTradeService = new GenericTradeService(this, new BitVcTradeServiceRaw(this));
        
      } else {
        pollingAccountService = new HuobiAccountService(this);
        pollingTradeService = new GenericTradeService(this, new HuobiTradeServiceRaw(this));
        
      }
    }
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(getClass());
    spec.setExchangeName("Huobi");
    spec.setExchangeDescription("Huobi-Family Exchange (Huobi, BitVC)");

    /* by default we request market data from huobi and execute on bitvc */
    spec.setPlainTextUri("http://market.huobi.com/staticmarket");
    spec.setSslUri("https://api.huobi.com/apiv3");

    /* set to true if trade and account service should be from BitVc too */
    spec.setExchangeSpecificParametersItem(USE_BITVC, false);
    spec.setExchangeSpecificParametersItem(HUOBI_MARKET_DATA, "http://market.huobi.com/staticmarket");

    return spec;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // BitVC doesn't require a nonce for it's authenticated API
    return null;
  }
}
