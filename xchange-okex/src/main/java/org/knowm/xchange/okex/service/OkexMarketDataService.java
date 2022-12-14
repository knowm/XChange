package org.knowm.xchange.okex.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexAdapters;
import org.knowm.xchange.okex.OkexExchange;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.marketdata.OkexCandleStick;
import org.knowm.xchange.okex.dto.marketdata.OkexTicker;
import org.knowm.xchange.okex.service.params.OkexTickerParams;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;
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
        getOkexTrades(OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument), 100).getData(),
        instrument);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return OkexAdapters.adaptTrades(
        getOkexTrades(OkexAdapters.adaptInstrumentToOkexInstrumentId(currencyPair), 100).getData(),
        currencyPair);
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
      throw new NotYetImplementedForExchangeException(
          "Only discrete period values are supported;"
              + Arrays.toString(OkexCandleStickPeriodType.getSupportedPeriodsInSecs()));
    }

    String limit = null;
    if (params instanceof DefaultCandleStickParamWithLimit) {
      limit = String.valueOf(((DefaultCandleStickParamWithLimit) params).getLimit());
    }

    OkexResponse<List<OkexCandleStick>> historyCandle =
        getHistoryCandle(
            OkexAdapters.adaptInstrumentToOkexInstrumentId(currencyPair),
            String.valueOf(defaultCandleStickParam.getEndDate().getTime()),
            String.valueOf(defaultCandleStickParam.getStartDate().getTime()),
            periodType.getFieldValue(),
            limit);
    return OkexAdapters.adaptCandleStickData(historyCandle.getData(), currencyPair);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    OkexResponse<List<OkexTicker>> ticker =
        getTicker(OkexAdapters.adaptInstrumentToOkexInstrumentId(currencyPair));
    return OkexAdapters.adaptTicker(ticker.getData().get(0));
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    OkexResponse<List<OkexTicker>> ticker =
        getTicker(OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument));
    return OkexAdapters.adaptTicker(ticker.getData().get(0));
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    OkexTickerParams tickerParams = (OkexTickerParams) params;
    OkexResponse<List<OkexTicker>> ticker =
        getTickers(tickerParams.getInstType(), tickerParams.getUly(), tickerParams.getInstFamily());
    return OkexAdapters.adaptTickers(ticker.getData());
  }
}
