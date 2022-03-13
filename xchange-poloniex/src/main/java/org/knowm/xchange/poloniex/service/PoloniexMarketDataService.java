package org.knowm.xchange.poloniex.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.poloniex.PoloniexAdapters;
import org.knowm.xchange.poloniex.PoloniexErrorAdapter;
import org.knowm.xchange.poloniex.PoloniexUtils;
import org.knowm.xchange.poloniex.dto.PoloniexException;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexDepth;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

/** @author Zach Holmes */
public class PoloniexMarketDataService extends PoloniexMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public PoloniexMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    try {
      PoloniexTicker poloniexTicker = getPoloniexTicker(currencyPair);
      if (poloniexTicker == null) {
        throw new CurrencyPairNotValidException(currencyPair);
      }
      return PoloniexAdapters.adaptPoloniexTicker(poloniexTicker, currencyPair);
    } catch (PoloniexException e) {
      throw PoloniexErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      return getAllPoloniexTickers().entrySet().stream()
          .map(
              entry ->
                  PoloniexAdapters.adaptPoloniexTicker(
                      entry.getValue(), PoloniexUtils.toCurrencyPair(entry.getKey())))
          .collect(Collectors.toList());
    } catch (PoloniexException e) {
      throw PoloniexErrorAdapter.adapt(e);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, IOException {

    try {
      PoloniexDepth depth = null;

      int depthLimit = 999999; // ~full order book
      if (args != null && args.length > 0) {
        if (args[0] instanceof Integer) {

          depthLimit = (Integer) args[0];
        } else {
          throw new ExchangeException("Orderbook size argument must be an Integer!");
        }
      }
      depth = getPoloniexDepth(currencyPair, depthLimit);
      if (depth == null) {
        depth = getPoloniexDepth(currencyPair);
      }
      return PoloniexAdapters.adaptPoloniexDepth(depth, currencyPair);
    } catch (PoloniexException e) {
      throw PoloniexErrorAdapter.adapt(e);
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, IOException {

    try {
      Long startTime = null;
      Long endTime = null;

      if (args != null) {
        switch (args.length) {
          case 2:
            if (args[1] != null && args[1] instanceof Long) {
              endTime = (Long) args[1];
            }
          case 1:
            if (args[0] != null && args[0] instanceof Long) {
              startTime = (Long) args[0];
            }
        }
      }
      PoloniexPublicTrade[] poloniexPublicTrades = null;
      if (startTime == null && endTime == null) {
        poloniexPublicTrades = getPoloniexPublicTrades(currencyPair);
      } else {
        poloniexPublicTrades = getPoloniexPublicTrades(currencyPair, startTime, endTime);
      }
      return PoloniexAdapters.adaptPoloniexPublicTrades(poloniexPublicTrades, currencyPair);
    } catch (PoloniexException e) {
      throw PoloniexErrorAdapter.adapt(e);
    }
  }

  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, String startDate, String endDate, String period, Object... args)
          throws IOException {

    try {
      PoloniexChartDataPeriodType periodType = period != null ? PoloniexChartDataPeriodType.valueOf(period) : PoloniexChartDataPeriodType.PERIOD_300;
      PoloniexChartData[] poloniexChartData = getPoloniexChartData(
              currencyPair, TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(startDate)), TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(endDate)), periodType);
      return PoloniexAdapters.adaptPoloniexCandleStickData(poloniexChartData, currencyPair);
    } catch (PoloniexException e) {
      throw PoloniexErrorAdapter.adapt(e);
    }
  }
}
