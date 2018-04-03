package org.knowm.xchange.itbit.v1.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.itbit.v1.ItBitAdapters;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitDepth;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class ItBitMarketDataService extends ItBitMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public ItBitMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    ItBitTicker itBitTicker = getItBitTicker(currencyPair);

    return ItBitAdapters.adaptTicker(currencyPair, itBitTicker);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    ItBitDepth depth = getItBitDepth(currencyPair, args);

    List<LimitOrder> asks = ItBitAdapters.adaptOrders(depth.getAsks(), currencyPair, OrderType.ASK);
    List<LimitOrder> bids = ItBitAdapters.adaptOrders(depth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return ItBitAdapters.adaptTrades(getItBitTrades(currencyPair, args), currencyPair);
  }
}
