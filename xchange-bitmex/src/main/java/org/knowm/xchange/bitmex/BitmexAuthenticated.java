package org.knowm.xchange.bitmex;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Nullable;
import org.knowm.xchange.bitmex.dto.account.BitmexAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexMarginAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexMarginAccountList;
import org.knowm.xchange.bitmex.dto.account.BitmexWallet;
import org.knowm.xchange.bitmex.dto.account.BitmexWalletTransaction;
import org.knowm.xchange.bitmex.dto.account.BitmexWalletTransactionList;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrderList;
import org.knowm.xchange.bitmex.dto.params.FilterParam;
import org.knowm.xchange.bitmex.dto.trade.BitmexCancelAll;
import org.knowm.xchange.bitmex.dto.trade.BitmexPlaceOrderParameters;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import org.knowm.xchange.bitmex.dto.trade.BitmexPositionList;
import org.knowm.xchange.bitmex.dto.trade.BitmexPrivateExecution;
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
      @Nullable @QueryParam("filter") FilterParam filterParam,
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
   * @param filterParam Generic table filter
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
      @Nullable @QueryParam("filter") FilterParam filterParam,
      @Nullable @QueryParam("columns") String columns,
      @Nullable @QueryParam("count") Integer count,
      @Nullable @QueryParam("start") Long start,
      @Nullable @QueryParam("reverse") Boolean reverse,
      @Nullable @QueryParam("startTime") Date startTime,
      @Nullable @QueryParam("endTime") Date endTime)
      throws IOException, BitmexException;

  @POST
  @Path("order")
  @Consumes(MediaType.APPLICATION_JSON)
  BitmexPrivateOrder placeOrder(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      BitmexPlaceOrderParameters bitmexPlaceOrderParameters
)
      throws IOException, BitmexException;

  /**
   * @param apiKey
   * @param nonce
   * @param paramsDigest
   * @param orderId Order ID
   * @param origClOrdID Client Order ID. See {@link BitmexAuthenticated#placeOrder}.
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
   * @param clOrdID Client Order ID(s). See {@link BitmexAuthenticated#placeOrder}.
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
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @QueryParam("filter") FilterParam filterParam)
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
  HttpResponseAwareList<BitmexWallet> getWallet(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @QueryParam("currency") String currency)
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
