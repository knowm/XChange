package org.knowm.xchange.bitmex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Nullable;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitmex.dto.account.BitmexAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexMarginAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexMarginAccountList;
import org.knowm.xchange.bitmex.dto.account.BitmexWallet;
import org.knowm.xchange.bitmex.dto.account.BitmexWalletTransaction;
import org.knowm.xchange.bitmex.dto.account.BitmexWalletTransactionList;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrderList;
import org.knowm.xchange.bitmex.dto.trade.BitmexCancelAll;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import org.knowm.xchange.bitmex.dto.trade.BitmexPositionList;
import org.knowm.xchange.bitmex.dto.trade.BitmexPrivateExecution;
import org.knowm.xchange.bitmex.dto.trade.PlaceOrderCommand;
import org.knowm.xchange.bitmex.dto.trade.ReplaceOrderCommand;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface BitmexAuthenticated extends Bitmex {

  /**
   * Get all raw executions for your account. This returns all raw transactions, which includes
   * order opening and cancelation, and order status changes. It can be quite noisy. More focused
   * information is available at /execution/tradeHistory.
   */
  @GET
  @Path("execution")
  HttpResponseAwareList<BitmexPrivateExecution> getExecutions(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("symbol") String symbol,
      @Nullable @QueryParam("filter") String filter,
      @Nullable @QueryParam("columns") String columns,
      @Nullable @QueryParam("count") Integer count,
      @Nullable @QueryParam("start") Long start,
      @Nullable @QueryParam("reverse") Boolean reverse,
      @Nullable @QueryParam("startTime") Date startTime,
      @Nullable @QueryParam("endTime") Date endTime)
      throws IOException, BitmexException;

  /**
   * Get all balance-affecting executions. This includes each trade, insurance charge, and
   * settlement.
   */
  @GET
  @Path("execution/tradeHistory")
  HttpResponseAwareList<BitmexPrivateExecution> getTradeHistory(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("symbol") String symbol,
      @Nullable @QueryParam("filter") String filter,
      @Nullable @QueryParam("columns") String columns,
      @Nullable @QueryParam("count") Integer count,
      @Nullable @QueryParam("start") Long start,
      @Nullable @QueryParam("reverse") Boolean reverse,
      @Nullable @QueryParam("startTime") Date startTime,
      @Nullable @QueryParam("endTime") Date endTime)
      throws IOException, BitmexException;

  /**
   * @param apiKey
   * @param nonce
   * @param paramsDigest
   * @param symbol Instrument symbol. Send a bare series (e.g. XBU) to get data for the nearest
   *     expiring contract in that series. You can also send a timeframe, e.g. XBU:monthly.
   *     Timeframes are daily, weekly, monthly, quarterly, and biquarterly.
   * @param filter Generic table filter. Send JSON key/value pairs, such as {"key": "value"}. You
   *     can key on individual fields, and do more advanced querying on timestamps. See the
   *     Timestamp Docs for more details.
   * @param columns Generic table filter. Send JSON key/value pairs, such as {"key": "value"}. You
   *     can key on individual fields, and do more advanced querying on timestamps. See the
   *     Timestamp Docs for more details.
   * @param count Number of results to fetch.
   * @param start Starting point for results.
   * @param reverse If true, will sort results newest first.
   * @param startTime Starting date filter for results.
   * @param endTime Ending date filter for results.
   * @return {@link BitmexPrivateOrderList} containing the requested order(s).
   * @throws IOException
   * @throws BitmexException
   */
  @GET
  @Path("order")
  BitmexPrivateOrderList getOrders(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("symbol") String symbol,
      @Nullable @QueryParam("filter") String filter,
      @Nullable @QueryParam("columns") String columns,
      @Nullable @QueryParam("count") Integer count,
      @Nullable @QueryParam("start") Long start,
      @Nullable @QueryParam("reverse") Boolean reverse,
      @Nullable @QueryParam("startTime") Date startTime,
      @Nullable @QueryParam("endTime") Date endTime)
      throws IOException, BitmexException;

  /**
   * @param apiKey
   * @param nonce
   * @param paramsDigest
   * @param symbol Instrument symbol. e.g. {@code XBTUSD}.
   * @param side Optional Order side. Valid options: {@code Buy}, {@code Sell}. Defaults to {@code
   *     Buy} unless {@code orderQty} or {@code simpleOrderQty} is negative.
   * @param orderQuantity Optional Order quantity in units of the instrument (i.e. contracts).
   * @param simpleOrderQuantity Optional Order quantity in units of the underlying instrument (i.e.
   *     Bitcoin).
   * @param displayQuantity Optional quantity to display in the book. Use {@code 0} for a fully
   *     hidden order.
   * @param price Optional limit price for {@code Limit}, {@code StopLimit}, and {@code
   *     LimitIfTouched} orders.
   * @param stopPrice Optional trigger price for {@code Stop}, {@code StopLimit}, {@code
   *     MarketIfTouched}, and {@code LimitIfTouched} orders. Use a price below the current price
   *     for stop-sell orders and buy-if-touched orders. Use {@code execInst} of {@code MarkPrice}
   *     or {@code LastPrice} to define the current price used for triggering.
   * @param orderType Optional Order type. Valid options: {@code Market}, {@code Limit}, {@code
   *     Stop}, {@code StopLimit}, {@code MarketIfTouched}, {@code LimitIfTouched}, {@code
   *     MarketWithLeftOverAsLimit}, {@code Pegged}. Defaults to {@code Limit} when {@code price} is
   *     specified. Defaults to {@code Stop} when {@code stopPx} is specified. Defaults to {@code
   *     StopLimit} when {@code price} and {@code stopPx} are specified.
   * @param clOrdID Optional Client Order ID. This {@code clOrdID} will come back on the order and
   *     any related executions.
   * @param executionInstructions Optional execution instructions. Valid options: {@code
   *     ParticipateDoNotInitiate}, {@code AllOrNone}, {@code MarkPrice}, {@code IndexPrice}, {@code
   *     LastPrice}, {@code Close}, {@code ReduceOnly}, {@code Fixed}. {@code AllOrNone} instruction
   *     requires {@code displayQty} to be {@code 0}. {@code MarkPrice}, {@code IndexPrice} or
   *     {@code LastPrice} instruction valid for {@code Stop}, {@code StopLimit}, {@code
   *     MarketIfTouched}, and {@code LimitIfTouched} orders.
   * @param clOrdLinkID Optional Client Order Link ID for contingent orders.
   * @param contingencyType Optional contingency type for use with clOrdLinkID. Valid options:
   *     {@code OneCancelsTheOther}, {@code OneTriggersTheOther}, {@code
   *     OneUpdatesTheOtherAbsolute}, {@code OneUpdatesTheOtherProportional}.
   * @param pegPriceType Optional peg price type. Valid options: {@code LastPeg}, {@code
   *     MidPricePeg}, {@code MarketPeg}, {@code PrimaryPeg}, {@code TrailingStopPeg}.
   * @param pegOffsetValue Optional trailing offset from the current price for {@code Stop}, {@code
   *     StopLimit}, {@code MarketIfTouched}, and {@code LimitIfTouched} orders; use a negative
   *     offset for stop-sell orders and buy-if-touched orders. Optional offset from the peg price
   *     for {@code Pegged} orders.
   * @param timeInForce Optional Time in force. Valid options: {@code Day}, {@code GoodTillCancel},
   *     {@code ImmediateOrCancel}, {@code FillOrKill}. Defaults to {@code GoodTillCancel} for
   *     {@code Limit}, {@code StopLimit}, {@code LimitIfTouched}, and {@code
   *     MarketWithLeftOverAsLimit} orders.
   * @param text Optional order annotation. e.g. {@code Take profit}.
   * @return {@link BitmexPrivateOrder} contains the result of the call.
   * @throws IOException
   * @throws BitmexException
   */
  @POST
  @Path("order")
  BitmexPrivateOrder placeOrder(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @FormParam("symbol") String symbol,
      @Nullable @FormParam("side") String side,
      @Nullable @FormParam("orderQty") BigDecimal orderQuantity,
      @Nullable @FormParam("simpleOrderQty") BigDecimal simpleOrderQuantity,
      @Nullable @FormParam("displayQty") BigDecimal displayQuantity,
      @Nullable @FormParam("price") BigDecimal price,
      @Nullable @FormParam("stopPx") BigDecimal stopPrice,
      @Nullable @FormParam("ordType") String orderType,
      @Nullable @FormParam("clOrdID") String clOrdID,
      @Nullable @FormParam("execInst") String executionInstructions,
      @Nullable @FormParam("clOrdLinkID") String clOrdLinkID,
      @Nullable @FormParam("contingencyType") String contingencyType,
      @Nullable @FormParam("pegOffsetValue") BigDecimal pegOffsetValue,
      @Nullable @FormParam("pegPriceType") String pegPriceType,
      @Nullable @FormParam("timeInForce") String timeInForce,
      @Nullable @FormParam("text") String text)
      throws IOException, BitmexException;

  /**
   * @param apiKey
   * @param nonce
   * @param paramsDigest
   * @param orderId Order ID
   * @param origClOrdID Client Order ID. See {@link Bitmex#placeOrder}.
   * @param clOrdID Optional new Client Order ID, requires {@code origClOrdID}.
   * @param simpleOrderQty Optional order quantity in units of the underlying instrument (i.e.
   *     Bitcoin).
   * @param orderQuantity Optional order quantity in units of the instrument (i.e. contracts).
   * @param simpleLeavesQty Optional leaves quantity in units of the underlying instrument (i.e.
   *     Bitcoin). Useful for amending partially filled orders.
   * @param leavesQty Optional leaves quantity in units of the instrument (i.e. contracts). Useful
   *     for amending partially filled orders.
   * @param price Optional limit price for {@code Limit}, {@code StopLimit}, and {@code
   *     LimitIfTouched} orders.
   * @param stopPrice Optional trigger price for {@code Stop}, {@code StopLimit}, {@code
   *     MarketIfTouched}, and {@code LimitIfTouched} orders. Use a price below the current price
   *     for stop-sell orders and buy-if-touched orders.
   * @param pegOffsetValue Optional trailing offset from the current price for {@code Stop}, {@code
   *     StopLimit}, {@code MarketIfTouched}, and {@code LimitIfTouched} orders; use a negative
   *     offset for stop-sell orders and buy-if-touched orders. Optional offset from the peg price
   *     for {@code Pegged} orders.
   * @param text Optional amend annotation. e.g. {@code Adjust skew}.
   * @return {@link BitmexPrivateOrder} contains the result of the call.
   * @throws IOException
   * @throws BitmexException
   */
  @PUT
  @Path("order")
  // for some reason underlying library doesn't add contenty type for PUT requests automatically
  @Consumes("application/x-www-form-urlencoded")
  BitmexPrivateOrder replaceOrder(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @FormParam("orderID") String orderId,
      @Nullable @FormParam("origClOrdID") String origClOrdID,
      @Nullable @FormParam("clOrdID") String clOrdID,
      @Nullable @FormParam("simpleOrderQty") BigDecimal simpleOrderQty,
      @Nullable @FormParam("orderQty") BigDecimal orderQuantity,
      @Nullable @FormParam("simpleLeavesQty") BigDecimal simpleLeavesQty,
      @Nullable @FormParam("leavesQty") BigDecimal leavesQty,
      @Nullable @FormParam("price") BigDecimal price,
      @Nullable @FormParam("stopPx") BigDecimal stopPrice,
      @Nullable @FormParam("pegOffsetValue") BigDecimal pegOffsetValue,
      @Nullable @FormParam("text") String text)
      throws IOException, BitmexException;

  /**
   * @param apiKey
   * @param nonce
   * @param paramsDigest
   * @param orderCommands JSON Array of order(s). Use {@link PlaceOrderCommand} to generate JSON.
   * @return {@link BitmexPrivateOrderList} contains the results of the call.
   * @throws IOException
   * @throws BitmexException
   */
  @POST
  @Path("order/bulk")
  BitmexPrivateOrderList placeOrderBulk(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @FormParam("orders") String orderCommands)
      throws IOException, BitmexException;

  /**
   * @param apiKey
   * @param nonce
   * @param paramsDigest
   * @param orderCommands JSON Array of order(s). Use {@link ReplaceOrderCommand} to generate JSON.
   * @return {@link BitmexPrivateOrderList} contains the results of the call.
   * @throws IOException
   * @throws BitmexException
   */
  @PUT
  @Path("order/bulk")
  // for some reason underlying library doesn't add contenty type for PUT requests automatically
  @Consumes("application/x-www-form-urlencoded")
  BitmexPrivateOrderList replaceOrderBulk(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @FormParam("orders") String orderCommands)
      throws IOException, BitmexException;

  /**
   * Either an orderID or a clOrdID must be provided.
   *
   * @param apiKey
   * @param nonce
   * @param paramsDigest
   * @param orderID Order ID(s).
   * @param clOrdID Client Order ID(s). See {@link Bitmex#placeOrder}.
   * @return {@link BitmexPrivateOrderList} contains the results of the call.
   * @throws IOException
   * @throws BitmexException
   */
  @DELETE
  @Path("order")
  BitmexPrivateOrderList cancelOrder(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @FormParam("orderID") String orderID,
      @Nullable @FormParam("clOrdID") String clOrdID)
      throws IOException, BitmexException;

  /**
   * @param apiKey
   * @param nonce
   * @param paramsDigest
   * @param symbol Optional symbol. If provided, only cancels orders for that symbol.
   * @param filter Optional filter for cancellation. Use to only cancel some orders, e.g. {"side":
   *     "Buy"}.
   * @param text Optional cancellation annotation. e.g. 'Spread Exceeded'
   * @return {@link BitmexPrivateOrderList} contains the results of the call.
   * @throws IOException
   * @throws BitmexException
   */
  @DELETE
  @Path("order/all")
  BitmexPrivateOrderList cancelAllOrders(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @FormParam("symbol") String symbol,
      @Nullable @FormParam("filter") String filter,
      @Nullable @FormParam("text") String text)
      throws IOException, BitmexException;

  /**
   * Useful as a dead-man's switch to ensure your orders are canceled in case of an outage. If
   * called repeatedly, the existing offset will be canceled and a new one will be inserted in its
   * place. Example usage: call this route at 15s intervals with an offset of 60000 (60s). If this
   * route is not called within 60 seconds, all your orders will be automatically canceled.
   *
   * @param timeout Timeout in ms. Set to 0 to cancel this timer.
   * @return {@link BitmexPrivateOrderList} contains the results of the call.
   * @throws IOException
   * @throws BitmexException
   */
  @POST
  @Path("order/cancelAllAfter")
  BitmexCancelAll cancelAllAfter(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @FormParam("timeout") long timeout)
      throws IOException, BitmexException;

  @POST
  @Path("position/leverage")
  BitmexPosition updateLeveragePosition(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @FormParam("symbol") String symbol,
      @FormParam("leverage") BigDecimal leverage)
      throws IOException, BitmexException;

  @GET
  @Path("position")
  BitmexPositionList getPositions(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest)
      throws IOException, BitmexException;

  @GET
  @Path("position")
  BitmexPositionList getPositions(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("filter") String filter)
      throws IOException, BitmexException;

  @GET
  @Path("user")
  BitmexAccount getAccount(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest)
      throws IOException, BitmexException;

  @GET
  @Path("user/wallet")
  BitmexWallet getWallet(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest /*,
           @Nullable @QueryParam("currency") String currency*/)
      throws IOException, BitmexException;

  /** Get a history of all of your wallet transactions (deposits, withdrawals, PNL) */
  @GET
  @Path("user/walletHistory")
  BitmexWalletTransactionList getWalletHistory(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @QueryParam("currency") String currency,
      @Nullable @QueryParam("count") Integer count,
      @Nullable @QueryParam("start") Long start)
      throws IOException, BitmexException;

  /** Get a summary of all of your wallet transactions (deposits, withdrawals, PNL) */
  @GET
  @Path("user/walletSummary")
  BitmexWalletTransactionList getWalletSummary(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("currency") String currency)
      throws IOException, BitmexException;

  @GET
  @Path("user/margin")
  BitmexMarginAccount getMarginAccountStatus(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("currency") String currency)
      throws IOException, BitmexException;

  @GET
  @Path("user/margin?currency=all")
  BitmexMarginAccountList getMarginAccountsStatus(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest)
      throws IOException, BitmexException;

  @GET
  @Path("user/depositAddress")
  String getDepositAddress(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @QueryParam("currency") String currency)
      throws IOException, BitmexException;

  @POST
  @Path("user/requestWithdrawal")
  BitmexWalletTransaction withdrawFunds(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws IOException, BitmexException;
}
