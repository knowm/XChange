package org.knowm.xchange.dsx.service.core;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.DSXAuthenticatedV2;
import org.knowm.xchange.dsx.dto.marketdata.DSXExchangeInfo;
import org.knowm.xchange.dsx.dto.marketdata.DSXOrderbookWrapper;
import org.knowm.xchange.dsx.dto.marketdata.DSXTickerWrapper;
import org.knowm.xchange.dsx.dto.marketdata.DSXTradesWrapper;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author Mikhail Wall */
public class DSXMarketDataServiceRawCore<T extends DSXAuthenticatedV2> extends DSXBaseService<T>
    implements MarketDataService {

  protected static final int FULL_SIZE = 2000;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected DSXMarketDataServiceRawCore(Exchange exchange, Class<T> clazz) {

    super(exchange, clazz);
  }

  /**
   * @param pairs String of currency pairs to retrieve (e.g. "btcusd-btceur")
   * @return DSXTickerWrapper object
   * @throws IOException
   */
  public DSXTickerWrapper getDSXTicker(String pairs, String type) throws IOException {

    return dsx.getTicker(pairs.toLowerCase(), 1, type);
  }

  /**
   * Get market depth from exchange
   *
   * @param pairs String of currency pairs to retrieve (e.g. "btcusd-btceur")
   * @return DSXOrderbookWrapper object
   * @throws IOException
   */
  public DSXOrderbookWrapper getDSXOrderbook(String pairs, String type) throws IOException {

    return dsx.getOrderbook(pairs.toLowerCase(), 1, type);
  }

  /**
   * Get recent trades from exchange
   *
   * @param pairs String of currency pairs to retrieve (e.g. "btcusd-btceur")
   * @param size Integer value from 1 -> get corresponding number of items
   * @return DSXTradesWrapper
   * @throws IOException
   */
  public DSXTradesWrapper getDSXTrades(String pairs, int size, String type) throws IOException {

    if (size < 1) {
      size = 1;
    }

    if (size > FULL_SIZE) {
      size = FULL_SIZE;
    }

    return dsx.getTrades(pairs.toLowerCase(), size, 1, type);
  }

  /**
   * Get DSX exchange info
   *
   * @return DSXExchangeInfo object
   * @throws IOException
   */
  public DSXExchangeInfo getDSXInfo() throws IOException {

    return dsx.getInfo();
  }
}
