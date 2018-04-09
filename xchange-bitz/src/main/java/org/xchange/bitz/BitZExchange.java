package org.xchange.bitz;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import org.xchange.bitz.service.BitZMarketDataService;
import org.xchange.bitz.service.BitZTradeService;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitZExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new BitZMarketDataService(this);
    this.tradeService = new BitZTradeService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.bit-z.com");
    exchangeSpecification.setHost("http://www.bit-z.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bit-Z");
    exchangeSpecification.setExchangeDescription(
        "Bit-Z is a Bitcoin exchange registered in Hong Kong.");

    return exchangeSpecification;
  }

  @Override
  public TradeService getTradeService() {
    throw new NotYetImplementedForExchangeException(
        "Parital implementation due to partial implementation by the exchange");
  }

  @Override
  public AccountService getAccountService() {
    throw new NotAvailableFromExchangeException();
  }
}
