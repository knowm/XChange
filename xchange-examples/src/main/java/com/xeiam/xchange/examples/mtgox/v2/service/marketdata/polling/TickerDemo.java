/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.examples.mtgox.v2.service.marketdata.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.mtgox.v2.MtGoxExchange;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v2.service.polling.MtGoxMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Test requesting polling Ticker at MtGox
 */
public class TickerDemo {

    public static void main(String[] args) throws IOException {

        // Use the factory to get the version 2 MtGox exchange API using default settings
        Exchange mtGoxExchange = ExchangeFactory.INSTANCE.createExchange(MtGoxExchange.class.getName());

        // Interested in the public polling market data feed (no authentication)
        PollingMarketDataService marketDataService = mtGoxExchange.getPollingMarketDataService();
        generic(marketDataService);
        raw((MtGoxMarketDataServiceRaw) marketDataService);


    }

    private static void generic(PollingMarketDataService marketDataService) throws IOException {
        // Get the latest ticker data showing BTC to USD
        Ticker ticker = marketDataService.getTicker(Currencies.BTC, Currencies.USD);
        System.out.println(ticker.toString());

        // Get the latest ticker data showing BTC to EUR
        ticker = marketDataService.getTicker(Currencies.BTC, Currencies.EUR);
        System.out.println(ticker.toString());

        // Get the latest ticker data showing BTC to GBP
        ticker = marketDataService.getTicker(Currencies.BTC, Currencies.GBP);
        System.out.println(ticker.toString());

        // Get the latest ticker data showing BTC to JPY
        ticker = marketDataService.getTicker(Currencies.BTC, Currencies.JPY);
        System.out.println(ticker.toString());

        // Get the latest ticker data showing BTC to SEK
        ticker = marketDataService.getTicker(Currencies.BTC, Currencies.SEK);
        System.out.println(ticker.toString());
    }

    private static void raw(MtGoxMarketDataServiceRaw marketDataService) throws IOException {
        // Get the latest ticker data showing BTC to USD
        MtGoxTicker ticker = marketDataService.getMtGoxTicker(Currencies.BTC, Currencies.USD);
        System.out.println(ticker.toString());

        // Get the latest ticker data showing BTC to EUR
        ticker = marketDataService.getMtGoxTicker(Currencies.BTC, Currencies.EUR);
        System.out.println(ticker.toString());

        // Get the latest ticker data showing BTC to GBP
        ticker = marketDataService.getMtGoxTicker(Currencies.BTC, Currencies.GBP);
        System.out.println(ticker.toString());

        // Get the latest ticker data showing BTC to JPY
        ticker = marketDataService.getMtGoxTicker(Currencies.BTC, Currencies.JPY);
        System.out.println(ticker.toString());

        // Get the latest ticker data showing BTC to SEK
        ticker = marketDataService.getMtGoxTicker(Currencies.BTC, Currencies.SEK);
        System.out.println(ticker.toString());
    }

}
