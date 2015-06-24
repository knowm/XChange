package com.xeiam.xchange.ripple.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.ripple.RippleAdapters;
import com.xeiam.xchange.ripple.RippleExchange;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderDetails;
import com.xeiam.xchange.ripple.service.polling.params.RippleTradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;

public class RippleTradeService extends RippleTradeServiceRaw implements PollingTradeService {

  private static final boolean VALIDATE_ALL_REQUESTS = true;

  /**
   * Empty placeholder trade history parameter object.
   */
  private final RippleTradeHistoryParams defaultTradeHistoryParams = createTradeHistoryParams();

  public RippleTradeService(final Exchange exchange) {
    super(exchange);
  }

  /**
   * The additional data map of an order will be populated with {@link RippleExchange.DATA_BASE_COUNTERPARTY} if the base currency is not XRP,
   * similarly if the counter currency is not XRP then {@link RippleExchange.DATA_COUNTER_COUNTERPARTY} will be populated.
   */
  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return RippleAdapters.adaptOpenOrders(getOpenAccountOrders(),
        (Integer) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(RippleExchange.ROUNDING_SCALE));
  }

  @Override
  public String placeMarketOrder(final MarketOrder order) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  /**
   * If the base currency is not XRP then the order's additional data map must contain a value for {@link RippleExchange.DATA_BASE_COUNTERPARTY},
   * similarly if the counter currency is not XRP then {@link RippleExchange.DATA_COUNTER_COUNTERPARTY} must be populated.
   */
  @Override
  public String placeLimitOrder(final LimitOrder order) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {
    return placeOrder(order, VALIDATE_ALL_REQUESTS);
  }

  @Override
  public boolean cancelOrder(final String orderId) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {
    return cancelOrder(orderId, VALIDATE_ALL_REQUESTS);
  }

  /**
   * Search through the last {@link RippleTradeHistoryParams#DEFAULT_PAGE_LENGTH} notifications looking for a maximum of
   * {@link RippleTradeHistoryParams#DEFAULT_TRADE_COUNT_LIMIT} trades. If an account enters many orders and receives few executions then it is likely
   * that this query will return no trades. See {@link #getTradeHistory(TradeHistoryParams)} for details of how to structure the query to fit your use
   * case.
   * 
   * @param arguments these are ignored.
   */

  @Override
  public UserTrades getTradeHistory(final Object... arguments) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {
    return getTradeHistory(defaultTradeHistoryParams);
  }

  /**
   * Ripple trade history is a request intensive process. The REST API does not provide a simple single trade history query. Trades are retrieved by
   * querying account notifications and for those of type order details of the hash are then queried. These order detail queries could be order entry,
   * cancel or execution, it is not possible to tell from the notification. Therefore if an account is entering many orders but executing few of them,
   * this trade history query will result in many API calls without returning any trade history. In order to reduce the time and resources used in
   * these repeated calls In order to reduce the number of API calls a number of different methods can be used:
   * <ul>
   * <li><b>RippleTradeHistoryParams</b> set the {@link RippleTradeHistoryParams#setHashLimit(String)} for the last known trade, this query will then
   * terminate once it has been found.</li>
   * <li><b>RippleTradeHistoryParams</b> set the {@link RippleTradeHistoryParams#setTradeCountLimit(int)} to restrict the number of trades to return,
   * the default is {@link RippleTradeHistoryParams#DEFAULT_TRADE_COUNT_LIMIT}.</li>
   * <li><b>RippleTradeHistoryParams</b> set the {@link RippleTradeHistoryParams#setApiCallCountLimit(int)} to restrict the number of API calls that
   * will be made during a single trade history query, the default is {@link RippleTradeHistoryParams#DEFAULT_API_CALL_COUNT}.</li>
   * <li><b>TradeHistoryParamsTimeSpan</b> set the {@link TradeHistoryParamsTimeSpan#setStartTime(java.util.Date)} to limit the number of trades
   * searched for to those done since the given time.</li> TradeHistoryParamsTimeSpan
   * </ul>
   * 
   * @param params Can optionally implement {@RippleTradeHistoryParams}, {@link TradeHistoryParamPaging},
   *        {@TradeHistoryParamCurrencyPair}, {@link TradeHistoryParamsTimeSpan}. All other TradeHistoryParams types
   *        will be ignored.
   */
  @Override
  public UserTrades getTradeHistory(final TradeHistoryParams params) throws IOException {
    final String account;
    if (params instanceof RippleTradeHistoryParams) {
      final RippleTradeHistoryParams rippleParams = (RippleTradeHistoryParams) params;
      rippleParams.resetApiCallCount();
      rippleParams.resetTradeCount();
      if (rippleParams.getAccount() != null) {
        account = rippleParams.getAccount();
      } else {
        account = exchange.getExchangeSpecification().getApiKey();
      }
    } else {
      account = defaultTradeHistoryParams.getAccount();
    }

    final List<RippleOrderDetails> trades = getTradesForAccount(params, account);
    return RippleAdapters.adaptTrades(trades, params);
  }

  @Override
  public RippleTradeHistoryParams createTradeHistoryParams() {
    final RippleTradeHistoryParams params = new RippleTradeHistoryParams();
    params.setAccount(exchange.getExchangeSpecification().getApiKey());
    return params;
  }
}
