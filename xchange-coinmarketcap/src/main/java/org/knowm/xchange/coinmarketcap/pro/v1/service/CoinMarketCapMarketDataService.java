package org.knowm.xchange.coinmarketcap.pro.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrencyInfo;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class CoinMarketCapMarketDataService extends CoinMarketCapMarketDataServiceRaw
    implements MarketDataService {

  public CoinMarketCapMarketDataService(Exchange exchange) {
    super(exchange);
  }

  public CoinMarketCapCurrencyInfo getCurrencyInfo(Currency currency) throws IOException {
    Map<String, CoinMarketCapCurrencyInfo> currencyData =
            super.getCoinMarketCapCurrencyInfo(currency).getCurrencyData();
    return currencyData.get(currency.getSymbol());
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    CoinMarketCapTicker ticker = super.getCoinMarketCapLatestQuote(currencyPair)
            .getTickerData().get(currencyPair.base.getSymbol());

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
