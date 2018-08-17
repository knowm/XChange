package org.knowm.xchange.coinsuper.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsuper.CoinsuperAdapters;
import org.knowm.xchange.coinsuper.dto.CoinsuperResponse;
import org.knowm.xchange.coinsuper.dto.marketdata.CoinsuperOrderbook;
import org.knowm.xchange.coinsuper.dto.marketdata.CoinsuperTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoinsuperMarketDataService extends CoinsuperMarketDataServiceRaw
    implements MarketDataService {
  public CoinsuperMarketDataService(Exchange exchange) {
    super(exchange);
  }

  /** */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    Map<String, String> data = new HashMap<String, String>();
    CoinsuperResponse<CoinsuperOrderbook> CoinsuperOrderbooks = null;
    if (args != null && args.length > 0 && Integer.parseInt(args[0].toString()) > 0) {
      data.put("symbol", currencyPair.toString());
      data.put("num", args[0].toString());
      CoinsuperOrderbooks = getCoinsuperOrderBooks(data);
    } else {
      data.put("symbol", currencyPair.toString());
      CoinsuperOrderbooks = marketDepth(data);
    }

    return CoinsuperOrderbooks != null
        ? CoinsuperAdapters.adaptOrderBook(CoinsuperOrderbooks, currencyPair)
        : null;
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    CoinsuperResponse<List<CoinsuperTicker>> coinsuperTickers = getCoinsuperTicker(currencyPair);

    if (coinsuperTickers != null && coinsuperTickers.getData().getResult().get(0) != null) {
      return CoinsuperAdapters.convertTicker(coinsuperTickers.getData().getResult().get(0));
    } else {
      return null;
    }
  }

  /**
   * @param args
   * @return
   */
  private int getLimit(Object... args) {
    int limitDepth = 0;
    if (args != null && args.length == 1) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Integer)) {
        throw new ExchangeException("Argument 0 must be an Integer!");
      } else {
        limitDepth = (Integer) arg0;
      }
    }
    return limitDepth;
  }
}
