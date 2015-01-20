package com.xeiam.xchange.btce.v3;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEExchangeInfo;
import com.xeiam.xchange.btce.v3.service.polling.BTCETradeServiceRaw;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.service.polling.BTCEAccountService;
import com.xeiam.xchange.btce.v3.service.polling.BTCEMarketDataService;
import com.xeiam.xchange.btce.v3.service.polling.BTCETradeService;
import com.xeiam.xchange.utils.nonce.IntTimeNonceFactory;

import java.io.IOException;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the BTCE exchange API</li>
 * </ul>
 */
public class BTCEExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Integer> nonceFactory = new IntTimeNonceFactory();

  /**
   * Default constructor for ExchangeFactory
   */
  public BTCEExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new BTCEMarketDataService(exchangeSpecification);
    this.pollingAccountService = new BTCEAccountService(exchangeSpecification, nonceFactory);
    this.pollingTradeService = new BTCETradeService(exchangeSpecification, nonceFactory);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://btc-e.com");
    exchangeSpecification.setHost("btc-e.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BTC-e");
    exchangeSpecification.setExchangeDescription("BTC-e is a Bitcoin exchange registered in Russia.");

    return exchangeSpecification;
  }

  @Override
  public void init() throws IOException, ExchangeException {
    super.init();

    BTCEExchangeInfo info = ((BTCETradeServiceRaw) pollingTradeService).getExchangeInfo();
    ((BTCEAccountService) pollingAccountService).setTradingFeeFromExchangeInfo(info);
  }
}
