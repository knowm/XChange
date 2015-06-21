package com.xeiam.xchange.examples.mexbt.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.mexbt.MeXBTExchange;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class TickerDemo {

  public static void main(String[] args) throws ExchangeException, IOException {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(MeXBTExchange.class.getName());
    PollingMarketDataService md = exchange.getPollingMarketDataService();
    Ticker ticker = md.getTicker(CurrencyPair.BTC_MXN);
    System.out.println(ticker);
  }

}
