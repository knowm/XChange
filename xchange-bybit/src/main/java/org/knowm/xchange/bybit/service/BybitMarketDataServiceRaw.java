package org.knowm.xchange.bybit.service;

import java.io.IOException;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfos;
import org.knowm.xchange.bybit.dto.marketdata.ticker.BybitTicker;
import org.knowm.xchange.bybit.dto.marketdata.ticker.BybitTickers;

public class BybitMarketDataServiceRaw extends BybitBaseService {

  public BybitMarketDataServiceRaw(BybitExchange exchange) {
    super(exchange);
  }

  public BybitResult<BybitTickers<BybitTicker>> getTicker24h(BybitCategory category, String symbol)
      throws IOException {
    BybitResult<BybitTickers<BybitTicker>> result = bybit.getTicker24h(category, symbol);

    if (!result.isSuccess()) {
      throw BybitAdapters.createBybitExceptionFromResult(result);
    }
    return result;
  }

  public BybitResult<BybitInstrumentInfos<BybitInstrumentInfo>> getInstrumentsInfo(
      BybitCategory category) throws IOException {
    BybitResult<BybitInstrumentInfos<BybitInstrumentInfo>> result =
        bybit.getInstrumentsInfo(category);

    if (!result.isSuccess()) {
      throw BybitAdapters.createBybitExceptionFromResult(result);
    }
    return result;
  }
}
