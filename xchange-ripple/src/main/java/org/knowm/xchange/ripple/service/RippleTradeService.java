package org.knowm.xchange.ripple.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.ripple.RippleAdapters;
import org.knowm.xchange.ripple.RippleExchange;
import org.knowm.xchange.ripple.dto.trade.IRippleTradeTransaction;
import org.knowm.xchange.ripple.dto.trade.RippleLimitOrder;
import org.knowm.xchange.ripple.service.params.RippleTradeHistoryAccount;
import org.knowm.xchange.ripple.service.params.RippleTradeHistoryCount;
import org.knowm.xchange.ripple.service.params.RippleTradeHistoryParams;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class RippleTradeService extends RippleTradeServiceRaw implements TradeService {

  private final RippleExchange ripple;

  /** Empty placeholder trade history parameter object. */
  private final RippleTradeHistoryParams defaultTradeHistoryParams = createTradeHistoryParams();

  public RippleTradeService(final RippleExchange exchange) {
    super(exchange);
    ripple = exchange;
  }

  /**
   * The additional data map of an order will be populated with {@link
   * RippleExchange.DATA_BASE_COUNTERPARTY} if the base currency is not XRP, similarly if the
   * counter currency is not XRP then {@link RippleExchange.DATA_COUNTER_COUNTERPARTY} will be
   * populated.
   */
  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return RippleAdapters.adaptOpenOrders(getOpenAccountOrders(), ripple.getRoundingScale());
  }

  /**
   * @param order this should be a RippleLimitOrder object with the base and counter counterparties
   *     populated for any currency other than XRP.
   */
  @Override
  public String placeLimitOrder(final LimitOrder order) throws IOException {
    if (order instanceof RippleLimitOrder) {
      return placeOrder((RippleLimitOrder) order, ripple.validateOrderRequests());
    } else {
      throw new IllegalArgumentException(
          "order must be of type: " + RippleLimitOrder.class.getName());
    }
  }

  @Override
  public boolean cancelOrder(final String orderId) throws IOException {
    return cancelOrder(orderId, ripple.validateOrderRequests());
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
   * Ripple trade history is a request intensive process. The REST API does not provide a simple
   * single trade history query. Trades are retrieved by querying account notifications and for
   * those of type order details of the hash are then queried. These order detail queries could be
   * order entry, cancel or execution, it is not possible to tell from the notification. Therefore
   * if an account is entering many orders but executing few of them, this trade history query will
   * result in many API calls without returning any trade history. In order to reduce the time and
   * resources used in these repeated calls In order to reduce the number of API calls a number of
   * different methods can be used:
   *
   * <ul>
   *   <li><b>RippleTradeHistoryHashLimit</b> set the to the last known trade, this query will then
   *       terminate once it has been found.
   *   <li><b>RippleTradeHistoryCount</b> set the to restrict the number of trades to return, the
   *       default is {@link RippleTradeHistoryCount#DEFAULT_TRADE_COUNT_LIMIT}.
   *   <li><b>RippleTradeHistoryCount</b> set the to restrict the number of API calls that will be
   *       made during a single trade history query, the default is {@link
   *       RippleTradeHistoryCount#DEFAULT_API_CALL_COUNT}.
   *   <li><b>TradeHistoryParamsTimeSpan</b> set the {@link
   *       TradeHistoryParamsTimeSpan#setStartTime(java.util.Date)} to limit the number of trades
   *       searched for to those done since the given start time. TradeHistoryParamsTimeSpan
   * </ul>
   *
   * @param params Can optionally implement {@RippleTradeHistoryAccount},
   *     {@RippleTradeHistoryCount}, {@RippleTradeHistoryHashLimit},
   *     {@RippleTradeHistoryPreferredCurrencies}, {@link TradeHistoryParamPaging},
   *     {@TradeHistoryParamCurrencyPair}, {@link TradeHistoryParamsTimeSpan}. All other
   *     TradeHistoryParams types will be ignored.
   */
  @Override
  public UserTrades getTradeHistory(final TradeHistoryParams params) throws IOException {
    if (params instanceof RippleTradeHistoryCount) {
      final RippleTradeHistoryCount rippleParams = (RippleTradeHistoryCount) params;
      rippleParams.resetApiCallCount();
      rippleParams.resetTradeCount();
    }

    final String account;
    if (params instanceof RippleTradeHistoryAccount) {
      final RippleTradeHistoryAccount rippleAccount = (RippleTradeHistoryAccount) params;
      if (rippleAccount.getAccount() != null) {
        account = rippleAccount.getAccount();
      } else {
        account = exchange.getExchangeSpecification().getApiKey();
      }
    } else {
      account = defaultTradeHistoryParams.getAccount();
    }

    final List<IRippleTradeTransaction> trades = getTradesForAccount(params, account);
    return RippleAdapters.adaptTrades(
        trades,
        params,
        (RippleAccountService) exchange.getAccountService(),
        ripple.getRoundingScale());
  }

  @Override
  public RippleTradeHistoryParams createTradeHistoryParams() {
    final RippleTradeHistoryParams params = new RippleTradeHistoryParams();
    params.setAccount(exchange.getExchangeSpecification().getApiKey());
    return params;
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }
}
