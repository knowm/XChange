package org.knowm.xchange.coinmarketcap.pro.v1;


import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import java.math.BigDecimal;

public class CoinMarketCapAdapters {

    public static Ticker adaptTicker(CoinMarketCapTicker ticker, CurrencyPair currencyPair) {
        BigDecimal price = ticker.getQuote().get(currencyPair.counter.getCurrencyCode()).getPrice();
        BigDecimal volume24h = ticker.getQuote().get(currencyPair.counter.getCurrencyCode()).getVolume24h();

        return new Ticker.Builder()
                .currencyPair(currencyPair)
                .timestamp(ticker.getLastUpdated())
                .open(price)
                .last(price)
                .bid(price)
                .ask(price)
                .high(price)
                .low(price)
                .vwap(price)
                .volume(volume24h)
                .build();
    }

}
