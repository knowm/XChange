package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitget.BitgetAdapters;
import org.knowm.xchange.bitget.BitgetFuturesExchange;
import org.knowm.xchange.bitget.dto.marketdata.*;
import org.knowm.xchange.instrument.Instrument;

public class BitgetFuturesMarketDataServiceRaw extends BitgetFuturesBaseService {

  public BitgetFuturesMarketDataServiceRaw(BitgetFuturesExchange exchange) {
    super(exchange);
  }

  public BitgetServerTime getBitgetServerTime() throws IOException {
    return bitgetFutures.serverTime().getData();
  }

  public List<BitgetContractDto> getBitgetContractDtos(Instrument instrument) throws IOException {
    return bitgetFutures.contracts(BitgetAdapters.toString(instrument), "USDT-FUTURES").getData();
  }

  public List<BitgetFuturesTickerDto> getBitgetFuturesTickerDtos(Instrument instrument)
      throws IOException {
    if (instrument == null) {
      return bitgetFutures.allTickers("USDT-FUTURES").getData();
    } else {
      return bitgetFutures.ticker(BitgetAdapters.toString(instrument), "USDT-FUTURES").getData();
    }
  }
}
