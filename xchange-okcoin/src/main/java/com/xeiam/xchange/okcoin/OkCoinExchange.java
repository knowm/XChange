package com.xeiam.xchange.okcoin;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.okcoin.service.polling.OkCoinAccountService;
import com.xeiam.xchange.okcoin.service.polling.OkCoinMarketDataService;
import com.xeiam.xchange.okcoin.service.polling.OkCoinTradeService;

public class OkCoinExchange extends BaseExchange {

  /**
   * The parameter name of the symbols that will focus on.
   */
  public static final String SYMBOLS_PARAMETER = "symbols";
  public static final String INTL_SYMBOLS_PARAMETER = "intl_symbols";

  private static final List<CurrencyPair> SYMBOLS = Arrays.asList(CurrencyPair.BTC_CNY, CurrencyPair.LTC_CNY);
  private static final List<CurrencyPair> INTL_SYMBOLS = Arrays.asList(CurrencyPair.BTC_USD, CurrencyPair.LTC_USD);

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new OkCoinMarketDataService(exchangeSpecification);
    if (exchangeSpecification.getApiKey() != null) {
      this.pollingAccountService = new OkCoinAccountService(exchangeSpecification);
      this.pollingTradeService = new OkCoinTradeService(exchangeSpecification);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.okcoin.cn/api");
    exchangeSpecification.setHost("www.okcoin.cn");
    exchangeSpecification.setExchangeName("OKCoin");
    exchangeSpecification.setExchangeDescription("OKCoin is a globally oriented crypto-currency trading platform.");
    exchangeSpecification.setExchangeSpecificParametersItem(SYMBOLS_PARAMETER, SYMBOLS);
    exchangeSpecification.setExchangeSpecificParametersItem(INTL_SYMBOLS_PARAMETER, INTL_SYMBOLS);

    exchangeSpecification.setExchangeSpecificParametersItem("Intl_SslUri", "https://www.okcoin.com/api");
    exchangeSpecification.setExchangeSpecificParametersItem("Intl_Host", "www.okcoin.com");

    // set to true to automatically use the Intl_ parameters for ssluri and host
    exchangeSpecification.setExchangeSpecificParametersItem("Use_Intl", false);
    return exchangeSpecification;
  }

}
