package com.xeiam.xchange.itbit.v1.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.itbit.v1.ItBitAdapters;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitDepth;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitTicker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class ItBitMarketDataService extends ItBitMarketDataServiceRaw implements PollingMarketDataService {

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
