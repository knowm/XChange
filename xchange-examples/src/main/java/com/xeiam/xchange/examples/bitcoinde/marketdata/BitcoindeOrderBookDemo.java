package com.xeiam.xchange.examples.bitcoinde.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinde.BitcoindeExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class BitcoindeOrderBookDemo {
	public void main(String[] args) throws IOException {
        /* configure the exchange to use our api key */
		final String API_KEY = "";
        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoindeExchange.class.getName());
        exchangeSpecification.setApiKey(API_KEY);

        /* create the exchange object */
        Exchange bitcoindeExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

        /* create a data service from the exchange */
        PollingMarketDataService marketDataService = bitcoindeExchange.getPollingMarketDataService();

        /* get OrderBook data */
        OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_EUR);
        System.out.println(orderBook.toString());
	}
}