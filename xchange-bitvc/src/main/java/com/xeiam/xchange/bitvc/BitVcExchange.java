package com.xeiam.xchange.bitvc;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitvc.service.polling.BitVcAccountService;
import com.xeiam.xchange.bitvc.service.polling.BitVcMarketDataService;
import com.xeiam.xchange.bitvc.service.polling.BitVcTradeService;
import com.xeiam.xchange.huobi.service.polling.HuobiAccountService;

public class BitVcExchange extends BaseExchange implements Exchange {

  public static final String TRADE_PASSWORD_PARAMETER = "trade_password";
  public static final String URI_HUOBI = "huobi_uri";
  public static final String URI_HUOBI_MARKETDATA = "huobi_uri_marketdata";
  public static final String USE_HUOBI = "use_huobi";

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    pollingMarketDataService = new BitVcMarketDataService(this);
    if (exchangeSpecification.getApiKey() != null) {
      if ((Boolean) exchangeSpecification.getExchangeSpecificParametersItem(USE_HUOBI)) {
        pollingAccountService = new HuobiAccountService(this);
        pollingTradeService = new BitVcTradeService(this);

      } else {

        pollingAccountService = new BitVcAccountService(this);
        pollingTradeService = new BitVcTradeService(this);
      }
    }
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(getClass());
    spec.setExchangeName("BitVc");
    spec.setExchangeDescription("BitVC and Huobi");

    /* by default we request market data from huobi and execute on bitvc */
    spec.setPlainTextUri("http://market.huobi.com/staticmarket");
    spec.setSslUri("https://api.bitvc.com");

    /* set to true if trade and account service should be from Huobi too */
    spec.setExchangeSpecificParametersItem(USE_HUOBI, false);
    spec.setExchangeSpecificParametersItem(URI_HUOBI, "https://api.huobi.com/apiv3");
    spec.setExchangeSpecificParametersItem(URI_HUOBI_MARKETDATA, "http://market.huobi.com/staticmarket");

    return spec;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // BitVC doesn't require a nonce for it's authenticated API
    return null;
  }
}
