package org.knowm.xchange.bybit.service;

import java.io.IOException;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.BybitErrorAdapter;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategorizedPayload;
import org.knowm.xchange.bybit.dto.marketdata.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.BybitOrderBook;
import org.knowm.xchange.bybit.dto.marketdata.BybitServerTime;
import org.knowm.xchange.bybit.dto.marketdata.BybitTicker;
import org.knowm.xchange.instrument.Instrument;

public class BybitMarketDataServiceRaw extends BybitBaseService {

  public BybitMarketDataServiceRaw(BybitExchange exchange) {
    super(exchange);
  }


  public BybitCategorizedPayload<BybitInstrumentInfo> getInstrumentsInfo() throws IOException {
    try {
      return bybit.getInstrumentsInfo("spot").getResult();
    }
    catch (BybitException e) {
      throw BybitErrorAdapter.adapt(e);
    }
  }


  public BybitCategorizedPayload<BybitTicker> getBybitTickers(Instrument instrument) throws IOException {
    try {
      return bybit.getTickers("spot", BybitAdapters.toSymbol(instrument)).getResult();
    }
    catch (BybitException e) {
      throw BybitErrorAdapter.adapt(e);
    }

  }


  public BybitOrderBook getBybitOrderBook(Instrument instrument, Integer limit) throws IOException {
    try {
      return bybit.getOrderBook("spot", BybitAdapters.toSymbol(instrument), limit).getResult();
    }
    catch (BybitException e) {
      throw BybitErrorAdapter.adapt(e);
    }
  }


  public BybitServerTime getServerTime() throws IOException {
    try {
      return bybit.getServerTime().getResult();
    }
    catch (BybitException e) {
      throw BybitErrorAdapter.adapt(e);
    }
  }
}
