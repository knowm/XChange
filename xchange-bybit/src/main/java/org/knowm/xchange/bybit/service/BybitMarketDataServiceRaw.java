package org.knowm.xchange.bybit.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.marketdata.BybitSymbol;
import org.knowm.xchange.bybit.dto.marketdata.BybitTicker;

public class BybitMarketDataServiceRaw extends BybitBaseService {

  public BybitMarketDataServiceRaw(BybitExchange exchange) {
    super(exchange);
  }

  public BybitResult<List<BybitTicker>> getTicker24h(String symbol) throws IOException {
    BybitResult<List<BybitTicker>> result = bybit.getTicker24h(symbol);

    if (!result.isSuccess()) {
      throw BybitAdapters.createBybitExceptionFromResult(result);
    }
    return result;
  }


  public BybitResult<List<BybitSymbol>> getSymbols() throws IOException {
    BybitResult<List<BybitSymbol>> result = bybit.getSymbols();

    if (!result.isSuccess()) {
      throw BybitAdapters.createBybitExceptionFromResult(result);
    }
    return result;
  }

}
