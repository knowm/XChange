package com.xeiam.xchange.itbit.v1.service.polling;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.itbit.v1.ItBitAdapters;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitDepth;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitTicker;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class ItBitMarketDataService extends ItBitMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public ItBitMarketDataService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);
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

    return new OrderBook(new Date(), asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return ItBitAdapters.adaptTrades(getItBitTrades(currencyPair, args), currencyPair);
  }

}
