package com.xeiam.xchange.cryptotrade;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptotrade.service.polling.CryptoTradeAccountService;
import com.xeiam.xchange.cryptotrade.service.polling.CryptoTradeMarketDataService;
import com.xeiam.xchange.cryptotrade.service.polling.CryptoTradeTradeService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the CryptoTrade exchange API</li>
 * </ul>
 */
public class CryptoTradeExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public CryptoTradeExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new CryptoTradeMarketDataService(exchangeSpecification);
    this.pollingAccountService = new CryptoTradeAccountService(exchangeSpecification);
    this.pollingTradeService = new CryptoTradeTradeService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://crypto-trade.com");
    exchangeSpecification.setHost("crypto-trade.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Crypto-Trade");

    return exchangeSpecification;
  }

}
