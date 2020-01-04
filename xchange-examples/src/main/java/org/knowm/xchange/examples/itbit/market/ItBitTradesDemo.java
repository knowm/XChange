package org.knowm.xchange.examples.itbit.market;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.itbit.ItBitExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Created by joseph on 6/15/17. */
public class ItBitTradesDemo {
  public static void main(String[] args) throws IOException {
    Exchange xchange = ExchangeFactory.INSTANCE.createExchange(ItBitExchange.class.getName());

    MarketDataService marketDataService = xchange.getMarketDataService();

    generic(marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD, 22233);
    System.out.println(trades.toString());

    Trades tradesAsXBT =
        marketDataService.getTrades(new CurrencyPair(Currency.XBT, Currency.USD), 22233);
    System.out.println(tradesAsXBT.toString());
  }
}
