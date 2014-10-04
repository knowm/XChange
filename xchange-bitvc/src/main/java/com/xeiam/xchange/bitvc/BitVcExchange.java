package com.xeiam.xchange.bitvc;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitvc.service.polling.BitVcAccountService;
import com.xeiam.xchange.bitvc.service.polling.BitVcMarketDataService;
import com.xeiam.xchange.bitvc.service.polling.BitVcTradeService;
import com.xeiam.xchange.currency.CurrencyPair;

public class BitVcExchange extends BaseExchange implements Exchange {

  public static final String SYMBOLS_PARAMETER = "symbols";
  public static final String TRADE_PASSWORD_PARAMETER = "trade_password";

  private static final List<CurrencyPair> SYMBOLS = Arrays.asList(CurrencyPair.BTC_CNY, CurrencyPair.LTC_CNY);

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    pollingMarketDataService = new BitVcMarketDataService(exchangeSpecification);
    if (exchangeSpecification.getApiKey() != null) {
      pollingAccountService = new BitVcAccountService(exchangeSpecification);
      pollingTradeService = new BitVcTradeService(exchangeSpecification);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(getClass());
    spec.setExchangeName("BitVc");
    spec.setExchangeDescription("BitVC");
    spec.setPlainTextUri("http://market.huobi.com/staticmarket");
    spec.setSslUri("https://api.bitvc.com");
    spec.setExchangeSpecificParametersItem(SYMBOLS_PARAMETER, SYMBOLS);
    return spec;
  }
}
