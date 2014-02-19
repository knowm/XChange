package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.Coinbase;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;


public class CoinbaseMarketDataServiceRaw extends CoinbaseBaseService<Coinbase> {

  public CoinbaseMarketDataServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(Coinbase.class, exchangeSpecification);
  }
  
  public Map<String, BigDecimal> getCoinbaseCurrencyExchangeRates() throws IOException {

    return coinbase.getCurrencyExchangeRates();
  }

  public CoinbasePrice getCoinbaseBuyPrice() throws IOException {

    return getCoinbaseBuyPrice(null, null);
  }

  public CoinbasePrice getCoinbaseBuyPrice(BigDecimal quantity) throws IOException {

    return getCoinbaseBuyPrice(quantity, null);
  }

  public CoinbasePrice getCoinbaseBuyPrice(BigDecimal quantity, String currency) throws IOException {

    return coinbase.getBuyPrice(quantity, currency);
  }

  public CoinbasePrice getCoinbaseSellPrice() throws IOException {

    return getCoinbaseSellPrice(null, null);
  }

  public CoinbasePrice getCoinbaseSellPrice(BigDecimal quantity) throws IOException {

    return getCoinbaseSellPrice(quantity, null);
  }

  public CoinbasePrice getCoinbaseSellPrice(BigDecimal quantity, String currency) throws IOException {

    return coinbase.getSellPrice(quantity, currency);
  }

  public CoinbaseAmount getCoinbaseSpotRate(String currency) throws IOException {

    return coinbase.getSpotRate(currency);
  }

  public CoinbaseSpotPriceHistory getCoinbaseHistoricalSpotRates() throws IOException {

    return getCoinbaseHistoricalSpotRates(null);
  }

  public CoinbaseSpotPriceHistory getCoinbaseHistoricalSpotRates(Integer page) throws IOException {

    return coinbase.getHistoricalSpotRates(page);
  }
}
