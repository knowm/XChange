package org.knowm.xchange.examples.huobi.marketdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.examples.huobi.HuobiDemoUtils;
import org.knowm.xchange.huobi.HuobiExchange;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTicker;
import org.knowm.xchange.huobi.service.HuobiMarketDataService;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HuobiMarketDataDemo {
  public static void main(String[] args) throws IOException {
    Exchange exchange = HuobiDemoUtils.createExchange();
    MarketDataService marketDataService = exchange.getMarketDataService();
    generic(exchange, marketDataService);
    raw((HuobiExchange) exchange, (HuobiMarketDataService) marketDataService);
  }

  public static void generic(Exchange exchange, MarketDataService marketDataService)
      throws IOException {}

  public static void raw(HuobiExchange exchange, HuobiMarketDataService marketDataService)
      throws IOException {

    List<HuobiTicker> tickers = new ArrayList<>();
    for (CurrencyPair cp : exchange.getExchangeMetaData().getCurrencyPairs().keySet()) {
      if (cp.counter == Currency.USDT) {
        tickers.add(marketDataService.getHuobiTicker(cp));
      }
    }

    tickers.sort((t1, t2) -> t2.getTs().compareTo(t1.getTs()));

    tickers.forEach(
        t -> System.out.println(t.getId() + " => " + String.format("%s", t.toString())));
    System.out.println("raw out end");
  }
}
