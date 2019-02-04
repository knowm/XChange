package org.knowm.xchange.coinmarketcap.pro.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrencyInfo;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.wrapper.CoinMarketCapCurrencyData;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

public class CoinMarketCapMarketDataService extends CoinMarketCapMarketDataServiceRaw
    implements MarketDataService {

  public CoinMarketCapMarketDataService(Exchange exchange) {
    super(exchange);
  }

  public CoinMarketCapCurrencyInfo getCurrencyInfo(Currency currency) throws IOException {
    CoinMarketCapCurrencyData currencyData =
        super.getCoinMarketCapCurrencyInfo(currency).getCurrencyData();
    return currencyData.getCurrencyMap().get(currency.getSymbol());
  }
}
