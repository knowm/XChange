package org.knowm.xchange.okex.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexAdapters;
import org.knowm.xchange.okex.OkexExchange;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.marketdata.OkexCandleStick;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexMarketDataService extends OkexMarketDataServiceRaw implements MarketDataService {
  public static final String SPOT = "SPOT";
  public static final String SWAP = "SWAP";
  public static final String FUTURES = "FUTURES";
  public static final String OPTION = "OPTION";

  public OkexMarketDataService(OkexExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    return OkexAdapters.adaptOrderBook(
        getOkexOrderbook(OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument)), instrument);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return this.getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public Trades getTrades(Instrument instrument, Object... args) throws IOException {
    return OkexAdapters.adaptTrades(
        getOkexTrades(OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument), 100).getData(), instrument);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return OkexAdapters.adaptTrades(
        getOkexTrades(OkexAdapters.adaptInstrumentToOkexInstrumentId(currencyPair), 100).getData(), currencyPair);
  }


  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, CandleStickDataParams params)
          throws IOException {

    if (!(params instanceof DefaultCandleStickParam)) {
      throw new NotYetImplementedForExchangeException("Only DefaultCandleStickParam is supported");
    }
    DefaultCandleStickParam defaultCandleStickParam = (DefaultCandleStickParam) params;
    OkexCandleStickPeriodType periodType =
            OkexCandleStickPeriodType.getPeriodTypeFromSecs(defaultCandleStickParam.getPeriodInSecs());
    if (periodType == null) {
      throw new NotYetImplementedForExchangeException("Only discrete period values are supported;" +
              Arrays.toString(OkexCandleStickPeriodType.getSupportedPeriodsInSecs()));
    }

    String limit = null;
    if (params instanceof DefaultCandleStickParamWithLimit) {
      limit = String.valueOf(((DefaultCandleStickParamWithLimit) params).getLimit());
    }

    OkexResponse<List<OkexCandleStick>> historyCandle = getHistoryCandle(
            OkexAdapters.adaptInstrumentToOkexInstrumentId(currencyPair),
            String.valueOf(defaultCandleStickParam.getEndDate().getTime()),
            String.valueOf(defaultCandleStickParam.getStartDate().getTime()),
            periodType.getFieldValue(), limit);
    return OkexAdapters.adaptCandleStickData(historyCandle.getData(), currencyPair);
  }

  public Long getOkexTime() throws IOException {
    return okex.getTime().getData().get(0).getServerTime();
  }

  @Override
  public FundingRate getFundingRate(Instrument instrument) throws IOException {
    return OkexAdapters.adaptFundingRate(getOkexFundingRate(OkexAdapters.adaptInstrument(instrument)).getData());
  }
}
