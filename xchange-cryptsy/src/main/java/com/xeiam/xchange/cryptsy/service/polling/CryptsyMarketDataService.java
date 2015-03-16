package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptsy.CryptsyAdapters;
import com.xeiam.xchange.cryptsy.CryptsyCurrencyUtils;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyGetMarketsReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyMarketTradesReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyOrderBookReturn;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

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
