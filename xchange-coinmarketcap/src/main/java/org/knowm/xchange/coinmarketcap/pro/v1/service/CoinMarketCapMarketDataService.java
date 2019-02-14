package org.knowm.xchange.coinmarketcap.pro.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrencyInfo;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.Map;

import static org.knowm.xchange.coinmarketcap.deprecated.v2.CoinMarketCapAdapters.adaptTicker;

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

    return adaptTicker(ticker, currencyPair);
  }
}
