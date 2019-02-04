package org.knowm.xchange.coinmarketcap.pro.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrencyInfo;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
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
}
