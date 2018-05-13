package org.knowm.xchange.bitso.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.BitsoAdapters;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.bitso.dto.trade.BitsoOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/** @author Piotr Ładyżyński */
public class BitsoTradeService extends BitsoTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitsoTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, BitsoException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    BitsoOrder[] openOrders = getBitsoOpenOrders();

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (BitsoOrder bitsoOrder : openOrders) {
      OrderType orderType = bitsoOrder.getType() == 0 ? OrderType.BID : OrderType.ASK;
      String id = bitsoOrder.getId();
      BigDecimal price = bitsoOrder.getPrice();
      limitOrders.add(
          new LimitOrder(
              orderType,
              bitsoOrder.getAmount(),
              new CurrencyPair(Currency.BTC, Currency.MXN),
              id,
              bitsoOrder.getTime(),
              price));
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException, BitsoException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, BitsoException {

    BitsoOrder bitsoOrder;
    if (limitOrder.getType() == BID) {
      bitsoOrder = buyBitoOrder(limitOrder.getOriginalAmount(), limitOrder.getLimitPrice());
    } else {
      bitsoOrder = sellBitsoOrder(limitOrder.getOriginalAmount(), limitOrder.getLimitPrice());
    }
    if (bitsoOrder.getErrorMessage() != null) {
      throw new ExchangeException(bitsoOrder.getErrorMessage());
    }

    return bitsoOrder.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, BitsoException {

    return cancelBitsoOrder(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  /**
   * Required parameter types: {@link TradeHistoryParamPaging#getPageLength()}
   *
   * <p>Warning: using a limit here can be misleading. The underlying call retrieves trades,
   * withdrawals, and deposits. So the example here will limit the result to 17 of those types and
   * from those 17 only trades are returned. It is recommended to use the raw service demonstrated
   * below if you want to use this feature.
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    return BitsoAdapters.adaptTradeHistory(
        getBitsoUserTransactions(Long.valueOf(((TradeHistoryParamPaging) params).getPageLength())));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new DefaultTradeHistoryParamPaging(1000);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }
}
