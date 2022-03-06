package org.knowm.xchange.coincheck.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coincheck.CoincheckAdapter;
import org.knowm.xchange.coincheck.CoincheckUtil;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckPagination;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckPair;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoincheckMarketDataService extends CoincheckMarketDataServiceRaw
    implements MarketDataService {
  public CoincheckMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    return CoincheckAdapter.createTicker(
        instrument, getCoincheckTicker(toCoincheckPair(instrument)));
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    return CoincheckAdapter.createOrderBook(
        instrument, getCoincheckOrderBook(toCoincheckPair(instrument)));
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTrades((Instrument) currencyPair, args);
  }

  @Override
  public Trades getTrades(Instrument instrument, Object... args) throws IOException {
    CoincheckPagination pagination =
        CoincheckUtil.getArg(args, CoincheckPagination.class).orElse(null);
    return CoincheckAdapter.createTrades(
        getCoincheckTrades(toCoincheckPair(instrument), pagination));
  }

  private CoincheckPair toCoincheckPair(Instrument instrument) {
    if (!(instrument instanceof CurrencyPair)) {
      throw new UnsupportedOperationException("Coincheck only supports CurrencyPair");
    }
    return new CoincheckPair((CurrencyPair) instrument);
  }
}
