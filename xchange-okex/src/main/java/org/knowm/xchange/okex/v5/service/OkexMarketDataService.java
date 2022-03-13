package org.knowm.xchange.okex.v5.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.v5.OkexAdapters;
import org.knowm.xchange.okex.v5.OkexExchange;
import org.knowm.xchange.okex.v5.dto.OkexResponse;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexCandleStick;
import org.knowm.xchange.service.marketdata.MarketDataService;

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
        getOkexOrderbook(OkexAdapters.adaptCurrencyPairId(instrument)), instrument);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return this.getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public Trades getTrades(Instrument instrument, Object... args) throws IOException {
    return OkexAdapters.adaptTrades(
        getOkexTrades(OkexAdapters.adaptCurrencyPairId(instrument), 100).getData(), instrument);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return OkexAdapters.adaptTrades(
        getOkexTrades(OkexAdapters.adaptCurrencyPairId(currencyPair), 100).getData(), currencyPair);
  }

  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, String startDate, String endDate, String period, Object... args)
          throws IOException {

    String limit = null;
    if (args != null && args.length == 1) {
      if (args[0] != null && args[0] instanceof String) {
        limit = String.valueOf(args[0]);
      }
    }

    OkexResponse<List<OkexCandleStick>> historyCandle = getHistoryCandle(
            OkexAdapters.adaptCurrencyPairId(currencyPair), endDate, startDate, period, limit);

    return OkexAdapters.adaptCandleStickData(historyCandle.getData(), currencyPair);
  }

}
