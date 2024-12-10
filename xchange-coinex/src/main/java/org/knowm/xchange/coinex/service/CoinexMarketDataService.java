package org.knowm.xchange.coinex.service;

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.coinex.CoinexAdapters;
import org.knowm.xchange.coinex.CoinexErrorAdapter;
import org.knowm.xchange.coinex.CoinexExchange;
import org.knowm.xchange.coinex.config.Config;
import org.knowm.xchange.coinex.dto.CoinexException;
import org.knowm.xchange.coinex.dto.marketdata.CoinexAllMarketStatisticsV1;
import org.knowm.xchange.coinex.dto.marketdata.CoinexMaintainInfo;
import org.knowm.xchange.coinex.dto.marketdata.CoinexSingleMarketStatisticsV1;
import org.knowm.xchange.coinex.service.params.CoinexOrderBookParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.ExchangeHealth;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.InstrumentsParams;
import org.knowm.xchange.service.marketdata.params.Params;

public class CoinexMarketDataService extends CoinexMarketDataServiceRaw
    implements MarketDataService {

  public CoinexMarketDataService(CoinexExchange exchange) {
    super(exchange);
  }

  @Override
  public ExchangeHealth getExchangeHealth() {
    try {
      List<CoinexMaintainInfo> coinexMaintainInfos = getCoinexMaintainInfo();

      for (CoinexMaintainInfo coinexMaintainInfo: coinexMaintainInfos) {
        Instant now = Instant.now(Config.getInstance().getClock());
        if (ObjectUtils.allNotNull(coinexMaintainInfo.getStartTime(),
            coinexMaintainInfo.getEndTime())) {
          if (now.isAfter(coinexMaintainInfo.getStartTime()) && now.isBefore(
              coinexMaintainInfo.getEndTime())) {
            return ExchangeHealth.OFFLINE;
          }
        }

        if (ObjectUtils.allNotNull(coinexMaintainInfo.getProtectStart(),
            coinexMaintainInfo.getProtectEnd())) {
          if (now.isAfter(coinexMaintainInfo.getProtectStart()) && now.isBefore(
              coinexMaintainInfo.getProtectEnd())) {
            return ExchangeHealth.OFFLINE;
          }
        }
      }

    } catch (IOException e) {
      return ExchangeHealth.OFFLINE;
    }

    return ExchangeHealth.ONLINE;
  }


  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    try {
      CoinexSingleMarketStatisticsV1 singleMarketStatisticsV1 =
          getCoinexSingleMarketStatisticsV1(instrument);
      return CoinexAdapters.toTicker(
          instrument,
          singleMarketStatisticsV1.getTicker(),
          singleMarketStatisticsV1.getTimestamp());

    } catch (CoinexException e) {
      throw CoinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    // parse parameters
    Collection<Instrument> instruments = Collections.emptyList();
    if (params instanceof InstrumentsParams) {
      instruments = ((InstrumentsParams) params).getInstruments();
    } else if (params instanceof CurrencyPairsParam) {
      instruments =
          ((CurrencyPairsParam) params)
              .getCurrencyPairs().stream().map(Instrument.class::cast).collect(Collectors.toList());
    }

    try {
      // get all
      if (instruments.isEmpty()) {
        CoinexAllMarketStatisticsV1 coinexAllMarketStatisticsV1 = getCoinexAllMarketStatisticsV1();
        return coinexAllMarketStatisticsV1.getTickers().entrySet().stream()
            .map(
                entry ->
                    CoinexAdapters.toTicker(
                        entry.getKey(),
                        entry.getValue(),
                        coinexAllMarketStatisticsV1.getTimestamp()))
            .filter(ticker -> ticker.getInstrument() != null)
            .collect(Collectors.toList());
      } else {
        // get the first one
        Instrument instrument = instruments.iterator().next();
        return Collections.singletonList(getTicker(instrument));
      }

    } catch (CoinexException e) {
      throw CoinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    Integer limit = (Integer) ArrayUtils.get(args, 0);
    Integer interval = (Integer) ArrayUtils.get(args, 1);

    return getOrderBook(
        CoinexOrderBookParams.builder()
            .instrument(instrument)
            .limit(limit)
            .interval(interval)
            .build());
  }

  @Override
  public OrderBook getOrderBook(Params params) throws IOException {
    Validate.isInstanceOf(CoinexOrderBookParams.class, params);
    CoinexOrderBookParams coinexOrderBookParams = (CoinexOrderBookParams) params;

    try {
      return CoinexAdapters.toOrderBook(getCoinexOrderBook(coinexOrderBookParams));
    } catch (CoinexException e) {
      throw CoinexErrorAdapter.adapt(e);
    }
  }
}
