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

public class BybitMarketDataServiceRaw extends BybitBaseService {

  public BybitMarketDataServiceRaw(BybitExchange exchange) {
    super(exchange);
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
      BybitCategory category,
      String symbol,
      String status,
      String baseCoin,
      Integer limit,
      String cursor
  ) throws IOException {
    BybitResult<BybitInstrumentsInfo<BybitInstrumentInfo>> result =
        bybit.getInstrumentsInfo(
            category.getValue(),
            symbol,
            status,
            baseCoin,
            limit,
            cursor
        );

    if (!result.isSuccess()) {
      throw BybitAdapters.createBybitExceptionFromResult(result);
    }
    return result;
  }

  public BybitResult<String> getAssetsInfo(
      String accountType,
      String coin
  ) throws IOException {
    BybitResult<String> result =
        bybit.getAssetsInfo(
            accountType,
            coin
        );

    if (!result.isSuccess()) {
      throw BybitAdapters.createBybitExceptionFromResult(result);
    }
    return result;
  }
}
