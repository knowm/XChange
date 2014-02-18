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
  
  public Map<String, BigDecimal> getCurrencyExchangeRates() throws IOException {

    return coinbase.getCurrencyExchangeRates();
  }

  public CoinbasePrice getBuyPrice() throws IOException {

    return getBuyPrice(null, null);
  }

  public CoinbasePrice getBuyPrice(BigDecimal quantity) throws IOException {

    return getBuyPrice(quantity, null);
  }

  public CoinbasePrice getBuyPrice(BigDecimal quantity, String currency) throws IOException {

    return coinbase.getBuyPrice(quantity, currency);
  }

  public CoinbasePrice getSellPrice() throws IOException {

    return getBuyPrice(null, null);
  }

  public CoinbasePrice getSellPrice(BigDecimal quantity) throws IOException {

    return getBuyPrice(quantity, null);
  }

  public CoinbasePrice getSellPrice(BigDecimal quantity, String currency) throws IOException {

    return coinbase.getBuyPrice(quantity, currency);
  }

  public CoinbaseAmount getSpotRate(String currency) throws IOException {

    return coinbase.getSpotRate(currency);
  }

  public CoinbaseSpotPriceHistory getHistoricalSpotRates() throws IOException {

    return getHistoricalSpotRates(null);
  }

  public CoinbaseSpotPriceHistory getHistoricalSpotRates(Integer page) throws IOException {

    return coinbase.getHistoricalSpotRates(page);
  }
}
