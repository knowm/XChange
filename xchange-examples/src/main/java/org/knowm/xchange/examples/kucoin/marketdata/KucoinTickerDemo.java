package org.knowm.xchange.examples.kucoin.marketdata;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.kucoin.KucoinExchange;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTicker;
import org.knowm.xchange.kucoin.service.KucoinMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

/**
 * @author Jan Akerman
 */
public class KucoinTickerDemo {

    public static void main(String[] args) throws IOException {
        Exchange kucoinExchange = ExchangeFactory.INSTANCE.createExchange(KucoinExchange.class.getName());

        MarketDataService marketDataService = kucoinExchange.getMarketDataService();
        Ticker ticker = marketDataService.getTicker(CurrencyPair.ETH_BTC);

        System.out.println("Ticker: " + ticker.toString());
        System.out.println("Currency: " + ticker.getCurrencyPair());
        System.out.println("Last: " + ticker.getLast().toString());
        System.out.println("Volume: " + ticker.getVolume().toString());
        System.out.println("High: " + ticker.getHigh().toString());
        System.out.println("Low: " + ticker.getLow().toString());

        KucoinMarketDataServiceRaw krakenMarketDataService = (KucoinMarketDataServiceRaw) kucoinExchange.getMarketDataService();
        KucoinTicker kucoinTicker = krakenMarketDataService.getKucoinTicker(CurrencyPair.ETH_BTC);

        System.out.println("Ticker: " + kucoinTicker.toString());
        System.out.println("Currency: " + kucoinTicker.getCoinTypePair());
        System.out.println("Last: " + kucoinTicker.getLow());
        System.out.println("Volume: " + kucoinTicker.getVol());
        System.out.println("High: " + kucoinTicker.getHigh().toString());
        System.out.println("Low: " + kucoinTicker.getLow());
    }

}
