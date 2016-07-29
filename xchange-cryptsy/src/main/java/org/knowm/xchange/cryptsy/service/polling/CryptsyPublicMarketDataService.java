package org.knowm.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptsy.CryptsyAdapters;
import org.knowm.xchange.cryptsy.CryptsyCurrencyUtils;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketData;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyPublicOrderbook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

public class CryptsyPublicMarketDataService extends CryptsyPublicMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptsyPublicMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, IOException {

    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = super.getCryptsyMarketData(CryptsyCurrencyUtils.convertToMarketId(currencyPair));

    return CryptsyAdapters.adaptPublicTickers(cryptsyMarketData).get(0);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, IOException {

    Map<Integer, CryptsyPublicOrderbook> cryptsyOrderBook = super.getCryptsyOrderBook(CryptsyCurrencyUtils.convertToMarketId(currencyPair));
    return CryptsyAdapters.adaptPublicOrderBooks(cryptsyOrderBook).get(0);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, IOException {

    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = super.getCryptsyMarketData(CryptsyCurrencyUtils.convertToMarketId(currencyPair));

    return CryptsyAdapters.adaptPublicTrades(cryptsyMarketData).get(currencyPair);
  }

}
