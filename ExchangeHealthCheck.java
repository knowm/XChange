package org.knowm.xchange.utils;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class ExchangeHealthCheck {

    public static void run() {
        try {
            Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class);
            MarketDataService marketDataService = exchange.getMarketDataService();
            Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
            System.out.println("✅ Exchange Health Check OK: " + ticker.toString());
        } catch (Exception e) {
            System.err.println("❌ Exchange Health Check Failed");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        run();
    }
}
