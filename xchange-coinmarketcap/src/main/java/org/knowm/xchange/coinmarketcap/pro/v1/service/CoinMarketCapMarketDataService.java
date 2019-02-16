package org.knowm.xchange.coinmarketcap.pro.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrency;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrencyInfo;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.knowm.xchange.coinmarketcap.deprecated.v2.CoinMarketCapAdapters.adaptTicker;

public class CoinMarketCapMarketDataService extends CoinMarketCapMarketDataServiceRaw
    implements MarketDataService {

  public CoinMarketCapMarketDataService(Exchange exchange) {
    super(exchange);
  }

  //TODO: Add error handling

  public CoinMarketCapCurrencyInfo getCurrencyInfo(Currency currency) throws IOException {
    Map<String, CoinMarketCapCurrencyInfo> currencyData =
            super.getCoinMarketCapCurrencyInfo(currency).getCurrencyData();
    return currencyData.get(currency.getSymbol());
  }

  public List<CoinMarketCapCurrency> getCurrencyList() throws IOException {
    return super.getCoinMarketCapCurrencyMap().getCurrencyData();
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    CoinMarketCapTicker ticker = super.getCoinMarketCapLatestQuote(currencyPair)
            .getTickerData().get(currencyPair.base.getSymbol());

    return adaptTicker(ticker, currencyPair);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
      List<Ticker> tickers = new ArrayList<>();

    List<CoinMarketCapTicker> coinMarketCapTickers
            = super.getCoinMarketCapLatestDataForAllCurrencies().getTickerData();

      for(CoinMarketCapTicker t : coinMarketCapTickers) {

          Set<String> counterCurrencySymbolSet = t.getQuote().keySet();
          for (String counterSymbol : counterCurrencySymbolSet) {
              tickers.add(adaptTicker(t, buildCurrencyPair(t.getSymbol(), counterSymbol)));
          }
      }

    return tickers;
  }



  private CurrencyPair buildCurrencyPair(String currencyBaseSymbol, String currencyCounterSymbol){
      Currency currencyBase = Currency.getInstance(currencyBaseSymbol);
      Currency currencyCounter = Currency.getInstance(currencyCounterSymbol);
      return new CurrencyPair(currencyBase, currencyCounter);
  }

//    @Override
//    public Trades getTrades(CurrencyPair currencyPair, Object... objects) {
//        throw new NotAvailableFromExchangeException();
//    }
}
