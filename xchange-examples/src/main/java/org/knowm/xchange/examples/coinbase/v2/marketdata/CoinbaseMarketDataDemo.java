package org.knowm.xchange.examples.coinbase.v2.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbase.v2.CoinbaseExchange;
import org.knowm.xchange.coinbase.v2.dto.CoinbasePrice;
import org.knowm.xchange.coinbase.v2.dto.marketdata.CoinbaseCurrencyData.CoinbaseCurrency;
import org.knowm.xchange.coinbase.v2.service.CoinbaseMarketDataService;
import org.knowm.xchange.currency.Currency;

public class CoinbaseMarketDataDemo {

  public static void main(String[] args) throws IOException, ParseException {

    Exchange coinbaseExchange =
        ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName());
    CoinbaseMarketDataService marketDataService =
        (CoinbaseMarketDataService) coinbaseExchange.getMarketDataService();

    List<CoinbaseCurrency> currencies = marketDataService.getCoinbaseCurrencies();
    System.out.println("Currencies: " + currencies);

    Map<String, BigDecimal> exchangeRates = marketDataService.getCoinbaseExchangeRates();
    System.out.println("Exchange Rates: " + exchangeRates);

    CoinbasePrice buyPrice = marketDataService.getCoinbaseBuyPrice(Currency.BTC, Currency.USD);
    System.out.println("Buy Price for one BTC: " + buyPrice);

    CoinbasePrice sellPrice = marketDataService.getCoinbaseSellPrice(Currency.BTC, Currency.USD);
    System.out.println("Sell Price: " + sellPrice);

    CoinbasePrice spotRate = marketDataService.getCoinbaseSpotRate(Currency.BTC, Currency.USD);
    System.out.println("Spot Rate: " + spotRate);

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    CoinbasePrice historicalSpotRate =
        marketDataService.getCoinbaseHistoricalSpotRate(
            Currency.BTC, Currency.USD, format.parse("2016-12-01"));
    System.out.println("Historical Spot Rate 2016-12-01: " + historicalSpotRate);
  }
}
