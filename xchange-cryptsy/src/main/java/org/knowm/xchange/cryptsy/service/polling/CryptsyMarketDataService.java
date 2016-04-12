package org.knowm.xchange.cryptsy.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptsy.CryptsyAdapters;
import org.knowm.xchange.cryptsy.CryptsyCurrencyUtils;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyGetMarketsReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyMarketTradesReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyOrderBookReturn;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author ObsessiveOrange
 */
public class CryptsyMarketDataService extends CryptsyMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptsyMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException, ExchangeException {

    CryptsyGetMarketsReturn marketsReturnData = super.getCryptsyMarkets();

    return CryptsyAdapters.adaptTicker(marketsReturnData, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException, ExchangeException {

    CryptsyOrderBookReturn orderBookReturnData = super.getCryptsyOrderBook(CryptsyCurrencyUtils.convertToMarketId(currencyPair));

    return CryptsyAdapters.adaptOrderBook(orderBookReturnData, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException, ExchangeException {

    CryptsyMarketTradesReturn tradesReturnData = super.getCryptsyTrades(CryptsyCurrencyUtils.convertToMarketId(currencyPair));

    return CryptsyAdapters.adaptTrades(tradesReturnData, currencyPair);
  }

}
