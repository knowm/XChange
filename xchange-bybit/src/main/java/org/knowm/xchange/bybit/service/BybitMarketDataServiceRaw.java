package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.marketdata.BybitTicker;

public class BybitMarketDataServiceRaw extends BybitBaseService {

  public BybitMarketDataServiceRaw(BybitExchange exchange) {
    super(exchange);
  }

  public BybitResult<List<BybitTicker>> getTicker24h(String symbol)
      throws IOException {
    BybitResult<List<BybitTicker>> result = bybit.getTicker24h(symbol);
    if (!result.isSuccess()) {
      throw createBybitExceptionFromResult(result);
    }
    return result;
  }

}
