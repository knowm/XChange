package org.knowm.xchange.coinmarketcap.pro.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.pro.v1.CoinMarketCapAdapter;
import org.knowm.xchange.coinmarketcap.pro.v1.CoinMarketCapErrorAdapter;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrency;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrencyInfo;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;
import si.mazi.rescu.HttpStatusIOException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CoinMarketCapMarketDataService extends CoinMarketCapMarketDataServiceRaw
    implements MarketDataService {

  public CoinMarketCapMarketDataService(Exchange exchange) {
    super(exchange);
  }

  public CoinMarketCapCurrencyInfo getCurrencyInfo(Currency currency) throws IOException{

      Map<String, CoinMarketCapCurrencyInfo> currencyData = null;

      try {
          currencyData = super.getCoinMarketCapCurrencyInfo(currency).getData();
      } catch(HttpStatusIOException ex) {
          CoinMarketCapErrorAdapter.adapt(ex);
      }

      return currencyData.get(currency.getSymbol());
  }

  public List<CoinMarketCapCurrency> getCurrencyList() throws IOException {

      List<CoinMarketCapCurrency> currencyList = new ArrayList<>();

      try {
          currencyList =  super.getCoinMarketCapCurrencyMap().getData();
      } catch(HttpStatusIOException ex) {
          CoinMarketCapErrorAdapter.adapt(ex);
      }

      return currencyList;
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

      CoinMarketCapTicker ticker = null;
      try {
          ticker = super.getCoinMarketCapLatestQuote(currencyPair)
                  .getData().get(currencyPair.base.getSymbol());
      } catch (HttpStatusIOException ex) {
          CoinMarketCapErrorAdapter.adapt(ex);
      }

      return CoinMarketCapAdapter.adaptTicker(ticker, currencyPair);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
      List<Ticker> tickers = new ArrayList<>();

      List<CoinMarketCapTicker> coinMarketCapTickers = new ArrayList<>();

      try {
          coinMarketCapTickers = super.getCoinMarketCapLatestDataForAllCurrencies().getData();
      } catch (HttpStatusIOException ex) {
          CoinMarketCapErrorAdapter.adapt(ex);
      }

      for(CoinMarketCapTicker t : coinMarketCapTickers) {

          Set<String> counterCurrencySymbolSet = t.getQuote().keySet();
          for (String counterSymbol : counterCurrencySymbolSet) {
              tickers.add(CoinMarketCapAdapter.adaptTicker(t, buildCurrencyPair(t.getSymbol(), counterSymbol)));
          }
      }

    return tickers;
  }

    @Override
    public OrderBook getOrderBook(CurrencyPair currencyPair, Object... objects) {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public Trades getTrades(CurrencyPair currencyPair, Object... objects) {
        throw new NotAvailableFromExchangeException();
    }

  private CurrencyPair buildCurrencyPair(String currencyBaseSymbol, String currencyCounterSymbol){
      Currency currencyBase = Currency.getInstance(currencyBaseSymbol);
      Currency currencyCounter = Currency.getInstance(currencyCounterSymbol);
      return new CurrencyPair(currencyBase, currencyCounter);
  }

}
