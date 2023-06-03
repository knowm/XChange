package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioErrorAdapter;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.account.GateioOrder;
import org.knowm.xchange.gateio.dto.trade.GateioOpenOrders;
import org.knowm.xchange.gateio.dto.trade.GateioTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class GateioTradeService extends GateioTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioTradeService(GateioExchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    GateioOpenOrders openOrders = super.getGateioOpenOrders();
    Collection<Instrument> currencyPairs = exchange.getExchangeInstruments();

    return GateioAdapters.adaptOpenOrders(openOrders, currencyPairs);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      GateioOrder order = createOrder(GateioAdapters.toGateioOrder(marketOrder));
      return order.getId();
    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  /**
   * Submits a Limit Order to be executed on the Gateio Exchange for the desired market defined by
   * {@code CurrencyPair}. WARNING - Gateio will return true regardless of whether or not an order
   * actually gets created. The reason for this is that orders are simply submitted to a queue in
   * their back-end. One example for why an order might not get created is because there are
   * insufficient funds. The best attempt you can make to confirm that the order was created is to
   * poll {@link #getOpenOrders}. However, if the order is created and executed before it is caught
   * in its open state from calling {@link #getOpenOrders} then the only way to confirm would be
   * confirm the expected difference in funds available for your account.
   *
   * @return String "true"/"false" Used to determine if the order request was submitted
   *     successfully.
   */
  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return String.valueOf(super.placeGateioLimitOrder(limitOrder));
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[] {CancelOrderByIdParams.class, CancelOrderByCurrencyPair.class};
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams
        && orderParams instanceof CancelOrderByCurrencyPair) {
      return cancelOrder(
          ((CancelOrderByIdParams) orderParams).getOrderId(),
          ((CancelOrderByCurrencyPair) orderParams).getCurrencyPair());
    } else {
      return false;
    }
  }

  /** Required parameter: {@link TradeHistoryParamCurrencyPair} */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params)
      throws ExchangeException, IOException {

    CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    List<GateioTrade> userTrades = getGateioTradeHistory(pair).getTrades();

    return GateioAdapters.adaptUserTrades(userTrades);
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    // todo: implement getting of several orders
    Validate.validState(orderQueryParams.length == 1);
    Validate.isInstanceOf(OrderQueryParamInstrument.class, orderQueryParams[0]);

    OrderQueryParamInstrument params = (OrderQueryParamInstrument) orderQueryParams[0];

    try {
      GateioOrder gateioOrder = getOrder(params.getOrderId(), params.getInstrument());
      return List.of(GateioAdapters.toOrder(gateioOrder));
    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParamCurrencyPair createTradeHistoryParams() {

    return new DefaultTradeHistoryParamCurrencyPair();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }
}
