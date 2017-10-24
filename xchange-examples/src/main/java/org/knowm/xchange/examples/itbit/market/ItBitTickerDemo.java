package org.knowm.xchange.examples.itbit.market;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.itbit.v1.ItBitExchange;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitTicker;
import org.knowm.xchange.itbit.v1.service.ItBitMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Created by joseph on 6/15/17.
 */
public class ItBitTickerDemo {
  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(ItBitExchange.class.getName());

    MarketDataService marketDataService = exchange.getMarketDataService();

    generic(marketDataService);
    raw((ItBitMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println(ticker.toString());
  }

  private static void raw(ItBitMarketDataServiceRaw marketDataService) throws IOException {

    ItBitTicker ticker = marketDataService.getItBitTicker(CurrencyPair.BTC_USD);

    System.out.println(ticker.toString());
  }
}
