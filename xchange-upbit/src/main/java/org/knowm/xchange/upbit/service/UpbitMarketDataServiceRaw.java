package org.knowm.xchange.upbit.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.upbit.UpbitUtils;
import org.knowm.xchange.upbit.dto.marketdata.UpbitMarket;
import org.knowm.xchange.upbit.dto.marketdata.UpbitOrderBooks;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTickers;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTrades;

/**
 * Implementation of the market data service for Korbit
 *
 * <p>
 *
 * <p>
 *
 * <p>
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class UpbitMarketDataServiceRaw extends UpbitBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public UpbitMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public UpbitTickers getTickers(List<CurrencyPair> currencyPair) throws IOException {
    return upbit.getTicker(
        currencyPair.stream().map(UpbitUtils::toPairString).collect(Collectors.joining(",")));
  }

  public List<UpbitMarket> getMarketAll() throws IOException {
    return upbit.getMarketAll();
  }

  public UpbitTickers getTicker(CurrencyPair currencyPair) throws IOException {
    return upbit.getTicker(UpbitUtils.toPairString(currencyPair));
  }

  public UpbitOrderBooks getUpbitOrderBook(CurrencyPair currencyPair) throws IOException {
    return upbit.getOrderBook(UpbitUtils.toPairString(currencyPair));
  }

  public UpbitTrades getTrades(CurrencyPair currencyPair) throws IOException {
    return upbit.getTrades(UpbitUtils.toPairString(currencyPair), 100);
  }
}
