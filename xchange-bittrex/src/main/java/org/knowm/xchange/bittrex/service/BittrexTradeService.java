package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.bittrex.BittrexErrorAdapter;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.bittrex.dto.account.BittrexOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexUserTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class BittrexTradeService extends BittrexTradeServiceRaw implements TradeService {


  private final MarketDataService marketDataService;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexTradeService(Exchange exchange, MarketDataService marketDataService) {

    super(exchange);
    this.marketDataService = marketDataService;

  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    Ticker ticker = marketDataService.getTicker(marketOrder.getCurrencyPair());

    BigDecimal price;
    if(marketOrder.getType().equals(Order.OrderType.BID)) {
      price = ticker.getLast().multiply(new BigDecimal(10.0));

    } else {
      price = ticker.getLast().divide(new BigDecimal(10.0));
    }


    return placeLimitOrder(
            new LimitOrder(
                    marketOrder.getType(),
                    marketOrder.getOriginalAmount(),
                    marketOrder.getCurrencyPair(),
                    marketOrder.getId(),
                    marketOrder.getTimestamp(),
                    price
            )
    );

  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      return placeBittrexLimitOrder(limitOrder);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    try {
      return getOpenOrders(createOpenOrdersParams());
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    try {
      return new OpenOrders(BittrexAdapters.adaptOpenOrders(getBittrexOpenOrders(params)));
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    try {
      return cancelBittrexLimitOrder(orderId);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    try {
      if (orderParams instanceof CancelOrderByIdParams) {
        return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
      } else {
        return false;
      }
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    try {
      CurrencyPair currencyPair = null;
      if (params instanceof TradeHistoryParamCurrencyPair) {
        TradeHistoryParamCurrencyPair tradeHistoryParamCurrencyPair =
            (TradeHistoryParamCurrencyPair) params;
        currencyPair = tradeHistoryParamCurrencyPair.getCurrencyPair();
      }

      List<BittrexUserTrade> bittrexTradeHistory = getBittrexTradeHistory(currencyPair);
      return new UserTrades(
          BittrexAdapters.adaptUserTrades(bittrexTradeHistory), TradeSortType.SortByTimestamp);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamCurrencyPair();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    try {
      List<Order> orders = new ArrayList<>();

      for (String orderId : orderIds) {

        BittrexOrder order = getBittrexOrder(orderId);
        if (order != null) {
          LimitOrder limitOrder = BittrexAdapters.adaptOrder(order);
          orders.add(limitOrder);
        }
      }
      return orders;
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }
}
