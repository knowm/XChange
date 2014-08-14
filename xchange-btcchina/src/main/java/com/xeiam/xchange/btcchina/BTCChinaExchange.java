package com.xeiam.xchange.btcchina;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaAccountService;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaMarketDataService;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaTradeService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the BTCChina exchange API</li>
 * </ul>
 */
public class BTCChinaExchange extends BaseExchange implements Exchange {

  public static final String ALL_MARKET = "ALL";
  public static final String DEFAULT_MARKET = "BTCCNY";

  /**
   * 2 decimals for BTC/CNY and LTC/CNY markets.
   */
  public static final int CNY_SCALE = 2;

  /**
   * 4 decimals for LTC/BTC market.
   */
  public static final int BTC_SCALE = 4;

  /**
   * Default constructor for ExchangeFactory
   */
  public BTCChinaExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingTradeService = new BTCChinaTradeService(exchangeSpecification);
    this.pollingAccountService = new BTCChinaAccountService(exchangeSpecification);
    exchangeSpecification.setSslUri("https://data.btcchina.com");
    this.pollingMarketDataService = new BTCChinaMarketDataService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.btcchina.com");
    exchangeSpecification.setHost("api.btcchina.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BTCChina");
    exchangeSpecification.setExchangeDescription("BTCChina is a Bitcoin exchange located in China.");
    return exchangeSpecification;
  }
}
