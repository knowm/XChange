package com.xeiam.xchange.bitcoinde.dto.marketdata;

import java.io.IOException;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinde.BitcoindeExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class BitcoindeTickerIntegration {
	
	@Test
	public void bitcoindeTickerTest() throws IOException {
		/* configure the exchange to use our api key */
		ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoindeExchange.class.getName());
		exchangeSpecification.setApiKey("8b32fbcbe9b285b9d14df89d89d245fc");

		/* create the exchange object */
		Exchange bitcoindeExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

		/* create a data service from the exchange */
		PollingMarketDataService marketDataService = bitcoindeExchange.getPollingMarketDataService();

		/* display our ticker data */
        Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_EUR);
        System.out.println(ticker.toString());
	}

}
