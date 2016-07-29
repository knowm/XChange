package org.knowm.xchange.examples.mexbt.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.mexbt.MeXBTExchange;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

public class TickerDemo {

  public static void main(String[] args) throws ExchangeException, IOException {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(MeXBTExchange.class.getName());
    PollingMarketDataService md = exchange.getPollingMarketDataService();
    Ticker ticker = md.getTicker(CurrencyPair.BTC_MXN);
    System.out.println(ticker);
  }

}
