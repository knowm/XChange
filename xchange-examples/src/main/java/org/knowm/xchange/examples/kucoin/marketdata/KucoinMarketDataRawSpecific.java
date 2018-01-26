package org.knowm.xchange.examples.kucoin.marketdata;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssets;
import org.knowm.xchange.kraken.dto.marketdata.KrakenServerTime;
import org.knowm.xchange.kraken.dto.marketdata.KrakenSpreads;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;
import org.knowm.xchange.kucoin.Kucoin;
import org.knowm.xchange.kucoin.KucoinExchange;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTicker;
import org.knowm.xchange.kucoin.service.KucoinMarketDataService;

import java.io.IOException;
import java.util.List;

/**
 * @author Jan Akerman
 */
public class KucoinMarketDataRawSpecific {

    public static void main(String[] args) throws IOException {

        // Use the factory to get Kraken exchange API using default settings
        Exchange kucoinExchange = ExchangeFactory.INSTANCE.createExchange(KucoinExchange.class.getName());

        // Interested in the public market data feed (no authentication)
        KucoinMarketDataService kucoinExchangeMarketDataService = (KucoinMarketDataService) kucoinExchange.getMarketDataService();

        List<Ticker> tickers = kucoinExchangeMarketDataService.getTickers();
        System.out.println("All tickers as ExchangeTickers");
        tickers.forEach(System.out::println);

        System.out.println("All tickers as KucoinTickers");
        List<KucoinTicker> kucoinTickers = kucoinExchangeMarketDataService.getKucoinTickers();
        kucoinTickers.forEach(System.out::println);
    }

}
