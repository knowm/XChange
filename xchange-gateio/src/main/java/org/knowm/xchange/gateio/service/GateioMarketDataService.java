package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.dto.marketdata.*;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class GateioMarketDataService extends GateioMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    GateioTicker ticker =
        super.getBTERTicker(
            currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return GateioAdapters.adaptTicker(currencyPair, ticker);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    final List<CurrencyPair> currencyPairs = new ArrayList<>();
    if (params instanceof CurrencyPairsParam) {
      currencyPairs.addAll(((CurrencyPairsParam) params).getCurrencyPairs());
    }
    return getGateioTickers().values().stream()
        .filter(
            ticker -> currencyPairs.size() == 0 || currencyPairs.contains(ticker.getCurrencyPair()))
        .collect(Collectors.toList());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    GateioDepth gateioDepth =
        super.getBTEROrderBook(
            currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return GateioAdapters.adaptOrderBook(gateioDepth, currencyPair);
  }

  public Map<CurrencyPair, OrderBook> getOrderBooks() throws IOException {

    Map<CurrencyPair, GateioDepth> gateioDepths = super.getGateioDepths();
    Map<CurrencyPair, OrderBook> orderBooks = new HashMap<>(gateioDepths.size());

    gateioDepths.forEach(
        (currencyPair, gateioDepth) -> {
          OrderBook orderBook = GateioAdapters.adaptOrderBook(gateioDepth, currencyPair);
          orderBooks.put(currencyPair, orderBook);
        });

    return orderBooks;
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    GateioTradeHistory tradeHistory =
        (args != null && args.length > 0 && args[0] != null && args[0] instanceof String)
            ? super.getBTERTradeHistorySince(
                currencyPair.base.getCurrencyCode(),
                currencyPair.counter.getCurrencyCode(),
                (String) args[0])
            : super.getBTERTradeHistory(
                currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return GateioAdapters.adaptTrades(tradeHistory, currencyPair);
  }

  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, CandleStickDataParams params)
      throws IOException {

    if (!(params instanceof DefaultCandleStickParam)) {
      throw new NotYetImplementedForExchangeException("Only DefaultCandleStickParam is supported");
    }

    DefaultCandleStickParam defaultCandleStickParam = (DefaultCandleStickParam) params;

    long periodInSecs = defaultCandleStickParam.getPeriodInSecs();
    GateioKlineInterval interval = GateioKlineInterval.m30;
    for (GateioKlineInterval gateioKlineInterval : GateioKlineInterval.values()) {
      if (gateioKlineInterval.getSeconds() == periodInSecs) {
        interval = gateioKlineInterval;
      }
    }

    Date startDate = defaultCandleStickParam.getStartDate();
    Date endDate = defaultCandleStickParam.getEndDate();
    long hours = MILLISECONDS.toHours(Math.abs(startDate.getTime() - endDate.getTime()));

    List<GateioKline> klines = getKlines(currencyPair, interval, Math.toIntExact(hours));
    return GateioAdapters.adaptCandleStickData(klines, currencyPair);
  }
}
