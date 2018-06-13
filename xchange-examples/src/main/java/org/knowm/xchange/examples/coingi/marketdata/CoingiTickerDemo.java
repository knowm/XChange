package org.knowm.xchange.examples.coingi.marketdata;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.examples.coingi.CoingiDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

public class CoingiTickerDemo {
    public static void main(String[] args) throws IOException {
        Exchange coingi = CoingiDemoUtils.createExchange();
        
        MarketDataService marketDataService = coingi.getMarketDataService();
        Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_EUR);
        System.out.printf("Received the latest ticker: %s.\n", ticker);
    }
}
