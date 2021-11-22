package org.knowm.xchange.binance.futures.service;

import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.BinanceFuturesAuthenticated;
import org.knowm.xchange.binance.futures.dto.meta.BinanceFuturesExchangeInfo;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.params.Params;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.knowm.xchange.binance.BinanceResilience.REQUEST_WEIGHT_RATE_LIMITER;

public class BinanceFuturesMarketDataService extends BinanceMarketDataService {
    public BinanceFuturesMarketDataService(BinanceExchange exchange, BinanceAuthenticated binance, ResilienceRegistries resilienceRegistries) {
        super(exchange, binance, resilienceRegistries);
    }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) {
    throw new NotAvailableFromExchangeException("getTicker");
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    if (instrument instanceof FuturesContract) {
      FuturesContract futuresContract = (FuturesContract) instrument;
      return BinanceFuturesAdapter.replaceInstrument(
          super.getTicker(futuresContract.getCurrencyPair(), args), futuresContract);
    }
    throw new NotAvailableFromExchangeException("getTicker");
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return super.getTickers(params).stream()
        .map(
            t ->
                BinanceFuturesAdapter.replaceInstrument(
                    t, new FuturesContract(t.getCurrencyPair(), null)))
        .collect(Collectors.toList());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) {
    throw new NotAvailableFromExchangeException("getOrderBook");
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    if (instrument instanceof FuturesContract) {
      FuturesContract futuresContract = (FuturesContract) instrument;
      return BinanceFuturesAdapter.replaceInstrument(
          super.getOrderBook(futuresContract.getCurrencyPair(), args), futuresContract);
    }
    throw new NotAvailableFromExchangeException("getOrderBook");
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) {
    throw new NotAvailableFromExchangeException("getTrades");
  }

  @Override
  public Trades getTrades(Instrument instrument, Object... args) throws IOException {
    if (instrument instanceof FuturesContract) {
      FuturesContract futuresContract = (FuturesContract) instrument;
      return BinanceFuturesAdapter.replaceInstrument(
          super.getTrades(futuresContract.getCurrencyPair(), args), futuresContract);
    }
    throw new NotAvailableFromExchangeException("getTrades");
  }

    public List<BinanceTicker24h> ticker24h() throws IOException {
        return decorateApiCall(() -> binance.ticker24h())
                .withRetry(retry("ticker24h"))
                .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 40)
                .call();
    }

    protected int aggTradesPermits(Integer limit) {
        return 20;
    }

    public BinanceFuturesExchangeInfo getFuturesExchangeInfo() throws IOException {
        return decorateApiCall(((BinanceFuturesAuthenticated)binance)::futuresExchangeInfo)
                .withRetry(retry("futuresExchangeInfo"))
                .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
                .call();
    }
}
