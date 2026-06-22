package org.knowm.xchange.gateio.service;

import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.ExchangeHealth;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioErrorAdapter;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.config.Config;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.marketdata.*;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GateioMarketDataService extends GateioMarketDataServiceRaw
    implements MarketDataService {
  @Getter
  private Map<Instrument, GateioFundingInfo> fundingRateInfoMap = new HashMap<>();

  public GateioMarketDataService(GateioExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public ExchangeHealth getExchangeHealth() {
    try {
      Instant serverTime = getGateioServerTime().getTime();
      Instant localTime = Instant.now(Config.getInstance().getClock());

      // timestamps shouldn't diverge by more than 10 minutes
      if (Duration.between(serverTime, localTime).toMinutes() < 10) {
        return ExchangeHealth.ONLINE;
      }
    } catch (GateioException | IOException e) {
      return ExchangeHealth.OFFLINE;
    }

    return ExchangeHealth.OFFLINE;
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    Objects.requireNonNull(instrument);
    try {
      if (exchange.isFuturesEnabled()) {
        List<GateioFuturesTicker> tickers = getGateioFuturesTickers(instrument);
        Validate.validState(tickers.size() == 1);
        return GateioAdapters.toTickerFutures(
            tickers.get(0), exchange.getExchangeMetaData().getInstruments().get(instrument).getContractValue());
      } else {
        List<GateioTicker> tickers = getGateioTickers(instrument);
        Validate.validState(tickers.size() == 1);
        return GateioAdapters.toTickerSpot(tickers.get(0));
      }
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      if (exchange.isFuturesEnabled()) {
        List<GateioFuturesTicker> tickers = getGateioFuturesTickers(null);
        return tickers.stream()
            .map(
                d ->
                    GateioAdapters.toTickerFutures(
                        d,
                        exchange.getExchangeMetaData().getInstruments()
                            .get(GateioAdapters.fromGateioInstrument(d.getContract(), true))
                            .getContractValue()))
            .collect(Collectors.toList());
      } else {
        List<GateioTicker> tickers = getGateioTickers(null);
        return tickers.stream()
            .map(
                GateioAdapters::toTickerSpot)
            .collect(Collectors.toList());
      }
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    try {
      GateioOrderBook gateioOrderBook = getGateioOrderBook(instrument);
      return GateioAdapters.toOrderBook(gateioOrderBook, instrument);
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  public List<Currency> getCurrencies() throws IOException {
    try {
      List<GateioCurrencyInfo> currencyInfos = getGateioCurrencyInfos();
      return currencyInfos.stream()
          .filter(gateioCurrencyInfo -> !gateioCurrencyInfo.getDelisted())
          .map(GateioCurrencyInfo::getCurrency)
          .collect(Collectors.toList());
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  public List<CurrencyPair> getCurrencyPairs() throws IOException {
    try {
      List<GateioCurrencyPairDetails> metadata = getCurrencyPairDetails();

      return metadata.stream()
          .filter(details -> "tradable".equals(details.getTradeStatus()))
          .map(details -> new CurrencyPair(details.getAsset(), details.getQuote()))
          .collect(Collectors.toList());
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  public Map<Instrument, InstrumentMetaData> getMetaDataByInstrument() throws IOException {
    try {
      if (exchange.isFuturesEnabled()) {
        List<GateioInstrumentDetails> metadata = getInstrumentDetails();
        //for get funding stream need this data
        metadata.stream().filter(f -> f.getType().equals("direct") &&
                f.getStatus().equals("trading"))

            .forEach(entry -> fundingRateInfoMap.put(new FuturesContract(
                new CurrencyPair(entry.getName().replace("_", "/")),
                "PERP"), new GateioFundingInfo(entry.getFundingInterval(), entry.getFundingNextApply())));

        return metadata.stream().filter(f -> f.getType().equals("direct") &&
                f.getStatus().equals("trading"))
            .collect(
                Collectors.toMap(
                    gateioInstrumentDetails ->
                        new FuturesContract(
                            new CurrencyPair(gateioInstrumentDetails.getName().replace("_", "/")),
                            "PERP"),
                    GateioAdapters::instrumentToInstrumentMetaData));
      } else {
        List<GateioCurrencyPairDetails> metadata = getCurrencyPairDetails();

        return metadata.stream()
            .collect(
                Collectors.toMap(
                    gateioCurrencyPairDetails ->
                        new CurrencyPair(
                            gateioCurrencyPairDetails.getAsset(),
                            gateioCurrencyPairDetails.getQuote()),
                    GateioAdapters::currencyPairToInstrumentMetaData));
      }
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, CandleStickDataParams params)
      throws IOException {
    return getCandleStickData((Instrument) currencyPair, params);
  }

  /**
   * K-line chart data returns a maximum of 1000 points per request. When specifying from, to, and interval, ensure the number of points is not excessive
   *
   * @param instrument instrument.
   * @param params     Params for query, including start(e.g. march 2022.) and end date, period etc.,
   * @return
   * @throws IOException
   */
  @Override
  public CandleStickData getCandleStickData(Instrument instrument, CandleStickDataParams params)
      throws IOException {
    Long from = null;
    Long to = null;
    Integer limit = null;
    String interval = "1h"; // default
    if (params instanceof DefaultCandleStickParamWithLimit) {
      DefaultCandleStickParamWithLimit p =
          (DefaultCandleStickParamWithLimit) params;
      limit = p.getLimit();
      if (p.getPeriodInSecs() > 0) {
        interval = adaptInterval(p.getPeriodInSecs());
      }
    }
    // limit OR (from, to)
    else if (params instanceof DefaultCandleStickParam) {
      DefaultCandleStickParam p =
          (DefaultCandleStickParam) params;
      if (p.getStartDate() != null) {
        from = p.getStartDate().getTime() / 1000;
      }
      if (p.getEndDate() != null) {
        to = p.getEndDate().getTime() / 1000;
      }
      if (p.getPeriodInSecs() > 0) {
        interval = adaptInterval(p.getPeriodInSecs());
      }
    }
    try {
      if (instrument instanceof FuturesContract) {
        List<GateioFuturesCandlestick> gateiFuturesCandlesticks = getGateioFuturesCandlesticks(instrument, limit, from, to, interval);
        return GateioAdapters.toCandleStickDataFutures(gateiFuturesCandlesticks, instrument, exchange.getExchangeMetaData().getInstruments().get(instrument).getContractValue());
      } else {
        List<GateioSpotCandlestick> gateioSpotCandlesticks = getGateioSpotCandlesticks(instrument, limit, from, to, interval);
        return GateioAdapters.toCandleStickDataSpot(gateioSpotCandlesticks, instrument);
      }
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  public List<GateioFundingRateHistory> getFundingRateHistory(Instrument instrument, Long startTime, Long endTime, Integer limit) throws IOException {
    return getGateioFundingRateHistory(instrument, startTime, endTime, limit);
  }
  private String adaptInterval(long periodInSecs) {
    if (periodInSecs == 10) return "10s";
    if (periodInSecs == 60) return "1m";
    if (periodInSecs == 300) return "5m";
    if (periodInSecs == 900) return "15m";
    if (periodInSecs == 1800) return "30m";
    if (periodInSecs == 3600) return "1h";
    if (periodInSecs == 14400) return "4h";
    if (periodInSecs == 28800) return "8h";
    if (periodInSecs == 86400) return "1d";
    if (periodInSecs == 604800) return "7d";
    if (periodInSecs == 2592000) return "30d";
    return "1h";
  }
}
