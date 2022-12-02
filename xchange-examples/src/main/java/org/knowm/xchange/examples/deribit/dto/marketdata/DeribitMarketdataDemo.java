package org.knowm.xchange.examples.deribit.dto.marketdata;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.v2.dto.Kind;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitCurrency;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitInstrument;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitOrderBook;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitSummary;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTicker;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTrades;
import org.knowm.xchange.deribit.v2.service.DeribitMarketDataService;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.deribit.DeribitDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class DeribitMarketdataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = DeribitDemoUtils.createExchange();
    generic(exchange);
    raw(exchange);
  }

  private static void generic(Exchange exchange) throws IOException {
    MarketDataService genericService = exchange.getMarketDataService();

    CurrencyPair pair = new CurrencyPair("BTC", "PERPETUAL");

    Ticker ticker = genericService.getTicker(pair);
    System.out.println(ticker);

    OrderBook orderBook = genericService.getOrderBook(pair);
    System.out.println(orderBook);

    Trades trades = genericService.getTrades(pair);
    System.out.println(trades);
  }

  private static void raw(Exchange exchange) throws IOException {
    DeribitMarketDataService service = (DeribitMarketDataService) exchange.getMarketDataService();

    String instrumentName = "BTC-PERPETUAL";
    String currency = "BTC";

    DeribitTicker ticker = service.getDeribitTicker(instrumentName);
    System.out.println(ticker);

    DeribitOrderBook orderBook = service.getDeribitOrderBook(instrumentName, null);
    System.out.println(orderBook);

    DeribitTrades trades =
        service.getLastTradesByInstrument(instrumentName, null, null, null, null, null);
    System.out.println(trades);

    List<DeribitCurrency> currencies = service.getDeribitCurrencies();
    System.out.println(currencies);

    List<DeribitInstrument> instruments =
        service.getDeribitInstruments(currency, Kind.future, false);
    System.out.println(instruments);

    List<DeribitSummary> summaries = service.getSummaryByInstrument(instrumentName);
    System.out.println(summaries);
  }
}
