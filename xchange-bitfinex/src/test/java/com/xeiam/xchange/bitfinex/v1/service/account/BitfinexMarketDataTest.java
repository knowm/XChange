package com.xeiam.xchange.bitfinex.v1.service.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bitfinex.v1.BitfinexExchange;
import com.xeiam.xchange.currency.CurrencyPair;

public class BitfinexMarketDataTest {

  public static void main(String[] args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName());
    System.out.println(exchange.getPollingMarketDataService().getOrderBook(CurrencyPair.BTC_USD));
    
  }
}
