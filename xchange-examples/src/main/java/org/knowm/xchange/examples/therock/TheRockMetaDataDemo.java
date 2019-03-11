package org.knowm.xchange.examples.therock;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.therock.TheRockExchange;

public class TheRockMetaDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get The Rock exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(TheRockExchange.class.getName());
    ExchangeMetaData exchangeMetaData = exchange.getExchangeMetaData();
    System.out.println(exchangeMetaData.toString());

    for (CurrencyPair currencyPair : exchangeMetaData.getCurrencyPairs().keySet()) {
      System.out.println(currencyPair.toString());
    }
  }
}
