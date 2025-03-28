package org.knowm.xchange.bybit.service;

import java.io.IOException;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentsInfo;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTickers;
import org.knowm.xchange.client.ResilienceRegistries;

public class BybitMarketDataServiceRaw extends BybitBaseService {

  public BybitMarketDataServiceRaw(
      BybitExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public BybitResult<BybitTickers<BybitTicker>> getTicker24h(BybitCategory category, String symbol)
      throws IOException {
    BybitResult<BybitTickers<BybitTicker>> result = bybit.getTicker24h(category.getValue(), symbol);

    if (!result.isSuccess()) {
      throw BybitAdapters.createBybitExceptionFromResult(result);
    }
    return result;
  }

  public BybitResult<BybitInstrumentsInfo<BybitInstrumentInfo>> getInstrumentsInfo(
      BybitCategory category) throws IOException {
    BybitResult<BybitInstrumentsInfo<BybitInstrumentInfo>> result =
        bybit.getInstrumentsInfo(category.getValue(), "1000");

    if (!result.isSuccess()) {
      throw BybitAdapters.createBybitExceptionFromResult(result);
    }
    return result;
  }

  public BybitResult<BybitTickers<BybitTicker>> getTickers(BybitCategory category)
      throws IOException {
    BybitResult<BybitTickers<BybitTicker>> result = bybit.getTickers(category.getValue());

    if (!result.isSuccess()) {
      throw BybitAdapters.createBybitExceptionFromResult(result);
    }
    return result;
  }
}
