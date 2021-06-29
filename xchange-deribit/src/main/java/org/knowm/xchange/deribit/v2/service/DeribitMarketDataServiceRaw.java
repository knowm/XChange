package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.Kind;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitCurrency;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitInstrument;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitOrderBook;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitSummary;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTicker;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTrades;

/**
 * Implementation of the market data service for Deribit
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class DeribitMarketDataServiceRaw extends DeribitBaseService {

  public DeribitMarketDataServiceRaw(DeribitExchange exchange) {
    super(exchange);
  }

  public List<DeribitInstrument> getDeribitInstruments(String currency, Kind kind, Boolean expired)
      throws IOException {
    return deribit.getInstruments(currency, kind, expired).getResult();
  }

  public List<DeribitCurrency> getDeribitCurrencies() throws IOException {
    return deribit.getCurrencies().getResult();
  }

  public DeribitOrderBook getDeribitOrderBook(String instrumentName, Integer depth)
      throws IOException {
    return deribit.getOrderBook(instrumentName, depth).getResult();
  }

  public List<List<BigDecimal>> getHistoricalVolatility(String currency) throws IOException {
    return deribit.getHistoricalVolatility(currency).getResult();
  }

  public DeribitTrades getLastTradesByInstrument(
      String instrumentName,
      Integer startSeq,
      Integer endSeq,
      Integer count,
      Boolean includeOld,
      String sorting)
      throws IOException {
    return deribit
        .getLastTradesByInstrument(instrumentName, startSeq, endSeq, count, includeOld, sorting)
        .getResult();
  }

  public List<DeribitSummary> getSummaryByInstrument(String instrumentName) throws IOException {
    return deribit.getSummaryByInstrument(instrumentName).getResult();
  }

  public DeribitTicker getDeribitTicker(String instrumentName) throws IOException {
    return deribit.getTicker(instrumentName).getResult();
  }
}
