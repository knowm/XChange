package com.xeiam.xchange.bitmarket;

import java.io.InputStream;

import com.xeiam.xchange.bitmarket.service.polling.BitMarketAccountService;
import com.xeiam.xchange.bitmarket.service.polling.BitMarketTradeService;
import com.xeiam.xchange.utils.nonce.CurrentTime1000NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitmarket.service.polling.BitMarketDataService;

/**
 * @author kpysniak, kfonal
 */
public class BitMarketExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTime1000NonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new BitMarketDataService(this);
    this.pollingTradeService = new BitMarketTradeService(this);
    this.pollingAccountService = new BitMarketAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.bitmarket.pl/");
    exchangeSpecification.setHost("www.bitmarket.pl");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitmarket");
    exchangeSpecification.setExchangeDescription("Bitmarket is a Bitcoin exchange based in Poland.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  protected void loadMetaData(InputStream is) {
    loadExchangeMetaData(is);
  }
}
