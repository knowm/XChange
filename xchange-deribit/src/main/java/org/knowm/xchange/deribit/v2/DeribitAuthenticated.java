package org.knowm.xchange.deribit.v2;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.deribit.v2.dto.DeribitException;
import org.knowm.xchange.deribit.v2.dto.DeribitResponse;
import org.knowm.xchange.deribit.v2.dto.Kind;
import org.knowm.xchange.deribit.v2.dto.account.AccountSummary;
import org.knowm.xchange.deribit.v2.dto.account.Position;
import org.knowm.xchange.deribit.v2.dto.trade.AdvancedOptions;
import org.knowm.xchange.deribit.v2.dto.trade.Order;
import org.knowm.xchange.deribit.v2.dto.trade.OrderPlacement;
import org.knowm.xchange.deribit.v2.dto.trade.OrderType;
import org.knowm.xchange.deribit.v2.dto.trade.SettlementType;
import org.knowm.xchange.deribit.v2.dto.trade.TimeInForce;
import org.knowm.xchange.deribit.v2.dto.trade.Trigger;
import org.knowm.xchange.deribit.v2.dto.trade.UserSettlements;
import org.knowm.xchange.deribit.v2.dto.trade.UserTrades;
import si.mazi.rescu.ParamsDigest;

@Path("/api/v2/private")
@Produces(MediaType.APPLICATION_JSON)
public interface DeribitAuthenticated {

  /**
   * Retrieves user account summary.
   *
   * @param currency required, The currency symbol, BTC or ETH
   * @param extended Include additional fields
   * @see <a href="https://docs.deribit.com/#private-get_account_summary">docs.deribit.com</a>
   */
  @GET
  @Path("get_account_summary")
  DeribitResponse<AccountSummary> getAccountSummary(
      @QueryParam("currency") String currency,
      @QueryParam("extended") Boolean extended,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Places a buy order for an instrument.
   *
   * @param instrumentName required, Instrument name
   * @param amount required, It represents the requested order size. For perpetual and futures the
   *     amount is in USD units, for options it is amount of corresponding cryptocurrency contracts,
   *     e.g., BTC or ETH
   * @param type optional, The order type, default: "limit"
   * @param label optional, user defined label for the order (maximum 32 characters)
   * @param price optional, The order price in base currency (Only for limit and stop_limit orders)
   *     When adding order with advanced=usd, the field price should be the option price value in
   *     USD. When adding order with advanced=implv, the field price should be a value of implied
   *     volatility in percentages. For example, price=100, means implied volatility of 100%
   * @param timeInForce optional, Specifies how long the order remains in effect. Default
   *     "good_til_cancelled" "good_til_cancelled" - unfilled order remains in order book until
   *     cancelled "fill_or_kill" - execute a transaction immediately and completely or not at all
   *     "immediate_or_cancel" - execute a transaction immediately, and any portion of the order
   *     that cannot be immediately filled is cancelled
   * @param maxShow optional, Maximum amount within an order to be shown to other customers, 0 for
   *     invisible order
   * @param postOnly optional, If true, the order is considered post-only. If the new price would
   *     cause the order to be filled immediately (as taker), the price will be changed to be just
   *     below the bid. Only valid in combination with time_in_force="good_til_cancelled"
   * @param rejectPostOnly optional, If an order is considered post-only and this field is set to
   *     true then the order is put to order book unmodified or request is rejected and order is
   *     canceled. Only valid in combination with "post_only" set to true
   * @param reduceOnly optional, If true, the order is considered reduce-only which is intended to
   *     only reduce a current position
   * @param triggerPrice optional, Trigger price, required for trigger orders only (Stop-loss or
   *     Take-profit orders)
   * @param trigger optional, Defines trigger type, required for "stop_limit", "stop_market",
   *     "take_limit" or "take_market" order types
   * @param advanced optional, Advanced option order type. (Only for options)
   * @param mmp optional, Order MMP flag, only for order_type 'limit'
   * @see <a href="https://docs.deribit.com/#private-buy">docs.deribit.com</a>
   */
  @GET
  @Path("buy")
  DeribitResponse<OrderPlacement> buy(
      @QueryParam("instrument_name") String instrumentName,
      @QueryParam("amount") BigDecimal amount,
      @QueryParam("type") OrderType type,
      @QueryParam("label") String label,
      @QueryParam("price") BigDecimal price,
      @QueryParam("time_in_force") TimeInForce timeInForce,
      @QueryParam("max_show") BigDecimal maxShow,
      @QueryParam("post_only") Boolean postOnly,
      @QueryParam("reject_post_only") Boolean rejectPostOnly,
      @QueryParam("reduce_only") Boolean reduceOnly,
      @QueryParam("trigger_price") BigDecimal triggerPrice,
      @QueryParam("trigger") Trigger trigger,
      @QueryParam("advanced") AdvancedOptions advanced,
      @QueryParam("mmp") Boolean mmp,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Places a sell order for an instrument.
   *
   * @see <a href="https://docs.deribit.com/#private-sell">docs.deribit.com</a>
   */
  @GET
  @Path("sell")
  DeribitResponse<OrderPlacement> sell(
      @QueryParam("instrument_name") String instrumentName,
      @QueryParam("amount") BigDecimal amount,
      @QueryParam("type") OrderType type,
      @QueryParam("label") String label,
      @QueryParam("price") BigDecimal price,
      @QueryParam("time_in_force") TimeInForce timeInForce,
      @QueryParam("max_show") BigDecimal maxShow,
      @QueryParam("post_only") Boolean postOnly,
      @QueryParam("reject_post_only") Boolean rejectPostOnly,
      @QueryParam("reduce_only") Boolean reduceOnly,
      @QueryParam("trigger_price") BigDecimal triggerPrice,
      @QueryParam("trigger") Trigger trigger,
      @QueryParam("advanced") AdvancedOptions advanced,
      @QueryParam("mmp") Boolean mmp,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Changes price, amount and/or other properties of an order.
   *
   * @see <a href="https://docs.deribit.com/#private-edit">docs.deribit.com</a>
   */
  @GET
  @Path("edit")
  DeribitResponse<OrderPlacement> edit(
      @QueryParam("order_id") String orderId,
      @QueryParam("amount") BigDecimal amount,
      @QueryParam("price") BigDecimal price,
      @QueryParam("post_only") Boolean postOnly,
      @QueryParam("reject_post_only") Boolean rejectPostOnly,
      @QueryParam("reduce_only") Boolean reduceOnly,
      @QueryParam("trigger_price") BigDecimal triggerPrice,
      @QueryParam("advanced") AdvancedOptions advanced,
      @QueryParam("mmp") Boolean mmp,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Cancels an order, specified by order id.
   *
   * @param orderId required, The order id
   * @see <a href="https://docs.deribit.com/#private-cancel">docs.deribit.com</a>
   */
  @GET
  @Path("cancel")
  DeribitResponse<Order> cancel(
      @QueryParam("order_id") String orderId, @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Cancels orders by label. All user's orders (trigger orders too), with given label are canceled
   * in all currencies
   *
   * @param label user defined label for the order (maximum 64 characters)
   * @see <a href="https://docs.deribit.com/#private-cancel_by_label">docs.deribit.com</a>
   */
  @GET
  @Path("cancel_by_label")
  DeribitResponse<Integer> cancelByLabel(
      @QueryParam("label") String label, @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Retrieves list of user's open orders.
   *
   * @param currency required, The currency symbol
   * @param kind optional, Instrument kind, if not provided instruments of all kinds are considered.
   *     One of: "future", "option"
   * @param type optional, Order type, one of (all, limit, stop_all, stop_limit, stop_market),
   *     default - all
   * @see <a
   *     href="https://docs.deribit.com/#private-get_open_orders_by_currency">docs.deribit.com</a>
   */
  @GET
  @Path("get_open_orders_by_currency")
  DeribitResponse<List<Order>> getOpenOrdersByCurrency(
      @QueryParam("currency") String currency,
      @QueryParam("kind") Kind kind,
      @QueryParam("type") String type,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Retrieves list of user's open orders within given Instrument.
   *
   * @param instrumentName required, Instrument name
   * @param type optional, Order type, one of (all, limit, stop_all, stop_limit, stop_market),
   *     default - all
   * @see <a
   *     href="https://docs.deribit.com/#private-get_open_orders_by_instrument">docs.deribit.com</a>
   */
  @GET
  @Path("get_open_orders_by_instrument")
  DeribitResponse<List<Order>> getOpenOrdersByInstrument(
      @QueryParam("instrument_name") String instrumentName,
      @QueryParam("type") String type,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Retrieves the latest user trades that have occurred for instruments in a specific currency
   * symbol.
   *
   * @see <a
   *     href="https://docs.deribit.com/#private-get_user_trades_by_currency">docs.deribit.com</a>
   */
  @GET
  @Path("get_user_trades_by_currency")
  DeribitResponse<UserTrades> getUserTradesByCurrency(
      @QueryParam("currency") String currency,
      @QueryParam("kind") Kind kind,
      @QueryParam("start_id") String startId,
      @QueryParam("end_id") String endId,
      @QueryParam("count") Integer count,
      @QueryParam("include_old") Boolean includeOld,
      @QueryParam("sorting") String sorting,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Retrieves the latest user trades that have occurred for instruments in a specific currency
   * symbol and within given time range.
   *
   * @see <a
   *     href="https://docs.deribit.com/#private-get_user_trades_by_currency_and_time">docs.deribit.com</a>
   */
  @GET
  @Path("get_user_trades_by_currency")
  DeribitResponse<UserTrades> getUserTradesByCurrencyAndTime(
      @QueryParam("currency") String currency,
      @QueryParam("kind") Kind kind,
      @QueryParam("start_timestamp") long startTimestamp,
      @QueryParam("end_timestamp") long endTimestamp,
      @QueryParam("count") Integer count,
      @QueryParam("include_old") Boolean includeOld,
      @QueryParam("sorting") String sorting,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Retrieves the latest user trades that have occurred for a specific instrument.
   *
   * @param instrumentName required Instrument name
   * @param startSeq optional The sequence number of the first trade to be returned
   * @param endSeq optional The sequence number of the last trade to be returned
   * @param count optional Number of requested items, default - 10
   * @param includeOld optional Include trades older than 7 days, default - false
   * @param sorting optional ( asc, desc, default) Direction of results sorting (default value means
   *     no sorting, results will be returned in order in which they left the database)
   * @see <a
   *     href="https://docs.deribit.com/#private-get_user_trades_by_instrument">docs.deribit.com</a>
   */
  @GET
  @Path("get_user_trades_by_instrument")
  DeribitResponse<UserTrades> getUserTradesByInstrument(
      @QueryParam("instrument_name") String instrumentName,
      @QueryParam("start_seq") Integer startSeq,
      @QueryParam("end_seq") Integer endSeq,
      @QueryParam("count") Integer count,
      @QueryParam("include_old") Boolean includeOld,
      @QueryParam("sorting") String sorting,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Retrieves the latest user trades that have occurred for a specific instrument and within given
   * time range.
   *
   * @param instrumentName required Instrument name
   * @param startTimestamp required The earliest timestamp to return result for
   * @param endTimestamp required The most recent timestamp to return result for
   * @param count optional Number of requested items, default - 10
   * @param includeOld optional Include trades older than 7 days, default - false
   * @param sorting optional ( asc, desc, default) Direction of results sorting (default value means
   *     no sorting, results will be returned in order in which they left the database)
   * @see <a
   *     href="https://docs.deribit.com/#private-get_user_trades_by_instrument_and_time">docs.deribit.com</a>
   */
  @GET
  @Path("get_user_trades_by_instrument_and_time")
  DeribitResponse<UserTrades> getUserTradesByInstrumentAndTime(
      @QueryParam("instrument_name") String instrumentName,
      @QueryParam("start_timestamp") long startTimestamp,
      @QueryParam("end_timestamp") long endTimestamp,
      @QueryParam("count") Integer count,
      @QueryParam("include_old") Boolean includeOld,
      @QueryParam("sorting") String sorting,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Retrieves user positions.
   *
   * @param currency BTC, ETH
   * @param kind Kind filter on positions
   * @see <a href="https://docs.deribit.com/#private-get_positions">docs.deribit.com</a>
   */
  @GET
  @Path("get_positions")
  DeribitResponse<List<Position>> getPositions(
      @QueryParam("currency") String currency,
      @QueryParam("kind") Kind kind,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Retrieves public settlement, delivery and bankruptcy events filtered by instrument name.
   *
   * @param instrumentName required - Instrument name
   * @param count optional - Number of requested items, default - 20
   * @param type optional - Settlement type
   * @param continuation optional - Continuation string for pagination
   * @see <a
   *     href="https://docs.deribit.com/#private-get_settlement_history_by_instrument">docs.deribit.com</a>
   */
  @GET
  @Path("get_settlement_history_by_instrument")
  DeribitResponse<UserSettlements> getSettlementHistoryByInstrument(
      @QueryParam("instrument_name") String instrumentName,
      @QueryParam("type") SettlementType type,
      @QueryParam("count") Integer count,
      @QueryParam("continuation") String continuation,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Retrieves history of orders that have been partially or fully filled.
   *
   * @see <a
   *     href="https://docs.deribit.com/#private-get_order_history_by_currency">docs.deribit.com</a>
   */
  @GET
  @Path("get_order_history_by_instrument")
  DeribitResponse<List<Order>> getOrderHistoryByCurrency(
      @QueryParam("currency") String currency,
      @QueryParam("kind") Kind kind,
      @QueryParam("count") Integer count,
      @QueryParam("offset") Integer offset,
      @QueryParam("include_old") Boolean includeOld,
      @QueryParam("include_unfilled") Boolean includeUnfilled,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Retrieves history of orders that have been partially or fully filled.
   *
   * @param instrumentName required - Instrument name
   * @param count optional - Number of requested items, default - 20
   * @param offset optional - The offset for pagination, default - 0
   * @param includeOld optional - Include orders older than 2 days, default - false
   * @param includeUnfilled optional - Include fully unfilled closed orders, default - false
   * @see <a
   *     href="https://docs.deribit.com/#private-get_order_history_by_instrument">docs.deribit.com</a>
   */
  @GET
  @Path("get_order_history_by_instrument")
  DeribitResponse<List<Order>> getOrderHistoryByInstrument(
      @QueryParam("instrument_name") String instrumentName,
      @QueryParam("count") Integer count,
      @QueryParam("offset") Integer offset,
      @QueryParam("include_old") Boolean includeOld,
      @QueryParam("include_unfilled") Boolean includeUnfilled,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;

  /**
   * Retrieve the current state of an order
   *
   * @see <a href="https://docs.deribit.com/#private-get_order_state">docs.deribit.com</a>
   */
  @GET
  @Path("get_order_state")
  DeribitResponse<Order> getOrderState(
      @QueryParam("order_id") String orderId, @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;
}
