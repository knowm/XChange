package com.xeiam.xchange.examples.mexbt.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.mexbt.MeXBTExchange;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class TradesDemo {

  public static void main(String[] args) throws ExchangeException, IOException {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(MeXBTExchange.class.getName());
    PollingMarketDataService md = exchange.getPollingMarketDataService();
    Trades trades = md.getTrades(CurrencyPair.BTC_MXN, 0);
    System.out.println(trades);
  }

}
