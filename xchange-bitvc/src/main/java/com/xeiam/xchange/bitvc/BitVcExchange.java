package com.xeiam.xchange.bitvc;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitvc.service.polling.BitVcAccountService;
import com.xeiam.xchange.bitvc.service.polling.BitVcMarketDataService;
import com.xeiam.xchange.bitvc.service.polling.BitVcTradeService;
import com.xeiam.xchange.bitvc.service.polling.BitVcTradeServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.huobi.service.polling.HuobiAccountService;
import com.xeiam.xchange.huobi.service.polling.HuobiTradeServiceRaw;

public class BitVcExchange extends BaseExchange implements Exchange {

  public static final String SYMBOLS_PARAMETER = "symbols";
  public static final String TRADE_PASSWORD_PARAMETER = "trade_password";
  public static final String URI_HUOBI = "huobi_uri";
  public static final String URI_HUOBI_MARKETDATA = "huobi_uri_marketdata";
  public static final String USE_HUOBI = "use_huobi";
 
  private static final List<CurrencyPair> SYMBOLS = Arrays.asList(CurrencyPair.BTC_CNY, CurrencyPair.LTC_CNY);

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    pollingMarketDataService = new BitVcMarketDataService(exchangeSpecification);
    if (exchangeSpecification.getApiKey() != null) {
      if((Boolean) exchangeSpecification.getExchangeSpecificParametersItem(USE_HUOBI)) {
        pollingAccountService = new HuobiAccountService(exchangeSpecification);
        pollingTradeService = new BitVcTradeService(new HuobiTradeServiceRaw(exchangeSpecification));
        
      } else {
        
        pollingAccountService = new BitVcAccountService(exchangeSpecification);
        pollingTradeService = new BitVcTradeService(new BitVcTradeServiceRaw(exchangeSpecification));
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(getClass());
    spec.setExchangeName("BitVc");
    spec.setExchangeDescription("BitVC and Huobi");
    
    /* by default we request market data from huobi and execute on bitvc */
    spec.setPlainTextUri("http://market.huobi.com/staticmarket");
    spec.setSslUri("https://api.bitvc.com");
    spec.setExchangeSpecificParametersItem(SYMBOLS_PARAMETER, SYMBOLS);

    /* set to true if trade and account service should be from Huobi too */
    spec.setExchangeSpecificParametersItem(USE_HUOBI, false);
    spec.setExchangeSpecificParametersItem(URI_HUOBI, "https://api.huobi.com/apiv3");
    spec.setExchangeSpecificParametersItem(URI_HUOBI_MARKETDATA, "http://market.huobi.com/staticmarket");
    
    return spec;
  }
}
