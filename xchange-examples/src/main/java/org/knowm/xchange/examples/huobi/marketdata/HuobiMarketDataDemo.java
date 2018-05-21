package org.knowm.xchange.examples.huobi.marketdata;


import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.huobi.HuobiExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.examples.huobi.HuobiDemoUtils;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.huobi.service.HuobiMarketDataService;


public class HuobiMarketDataDemo {
    public static void main(String[] args) throws IOException {
      Exchange exchange = HuobiDemoUtils.createExchange();
      MarketDataService marketDataService = exchange.getMarketDataService();
      generic(exchange,marketDataService);
      raw((HuobiExchange)exchange,(HuobiMarketDataService)marketDataService);
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

        Collections.sort(
            tickers,
            new Comparator<HuobiTicker>() {
                @Override
                public int compare(HuobiTicker t1, HuobiTicker t2) {
                return t2.getTs().compareTo(t1.getTs());
                }
            });

        tickers
            .stream()
            .forEach(
                t -> {
                    System.out.println(
                        t.getId()
                            + " => "
                            + String.format("%s", t.toString()));
                });
        System.out.println("raw out end");
    }

}