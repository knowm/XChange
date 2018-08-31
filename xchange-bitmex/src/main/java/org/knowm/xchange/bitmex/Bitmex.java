package org.knowm.xchange.bitmex;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bitmex.dto.account.*;
import org.knowm.xchange.bitmex.dto.marketdata.*;
import org.knowm.xchange.bitmex.dto.marketdata.results.BitmexSymbolsAndPromptsResult;
import org.knowm.xchange.bitmex.dto.trade.*;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitmex {

  @GET
  @Path("user")
  BitmexAccount getAccount(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest)
      throws IOException;

  @GET
  @Path("user/wallet")
  BitmexWallet getWallet(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest /*,
           @Nullable @QueryParam("currency") String currency*/)
      throws IOException;

  // Get a history of all of your wallet transactions (deposits, withdrawals, PNL)
  @GET
  @Path("user/walletHistory")
  BitmexWalletTransactionList getWalletHistory(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("currency") String currency)
      throws IOException;

  // Get a summary of all of your wallet transactions (deposits, withdrawals, PNL)
  @GET
  @Path("user/walletSummary")
  BitmexWalletTransactionList getWalletSummary(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("currency") String currency)
      throws IOException;

  @GET
  @Path("user/margin")
  BitmexMarginAccount getMarginAccountStatus(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("currency") String currency)
      throws IOException;

  @GET
  @Path("user/margin?currency=all")
  BitmexMarginAccountList getMarginAccountsStatus(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest)
      throws IOException;

  @GET
  @Path("trade")
  BitmexPublicTradeList getTrades(
      @QueryParam("symbol") String currencyPair,
      @QueryParam("reverse") Boolean reverse,
      @Nullable @QueryParam("count") Integer count,
      @Nullable @QueryParam("start") Integer start)
      throws IOException;

  @GET
  @Path("trade/bucketed")
  BitmexKlineList getBucketedTrades(
      @QueryParam("binSize") String binSize,
      @QueryParam("partial") Boolean partial,
      @QueryParam("symbol") String symbol,
      @QueryParam("count") Long count,
      @QueryParam("reverse") Boolean reverse)
      throws IOException;

  @GET
  @Path("orderBook/L2")
  BitmexPublicOrderList getDepth(
      @QueryParam("symbol") String currencyPair, @QueryParam("depth") Double depth)
      throws IOException;

  @GET
  @Path("position")
  BitmexPositionList getPositions(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest)
      throws IOException;

  @GET
  @Path("position")
  BitmexPositionList getPositions(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("symbol") String symbol,
      @Nullable @QueryParam("filter") String filter)
      throws IOException;

  @GET
  @Path("instrument")
  BitmexTickerList getTickers(
      @Nullable @QueryParam("count") Integer count,
      @Nullable @QueryParam("start") Integer start,
      @Nullable @QueryParam("reverse") Boolean reverse)
      throws IOException, BitmexException;

  @GET
  @Path("instrument")
  BitmexTickerList getTicker(@QueryParam("symbol") String symbol)
      throws IOException, BitmexException;

  @GET
  @Path("instrument/active")
  BitmexTickerList getActiveTickers() throws IOException, BitmexException;

  @GET
  @Path("instrument/activeIntervals")
  BitmexSymbolsAndPromptsResult getPromptsAndSymbols() throws IOException, BitmexException;

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
      @Nullable @QueryParam("start") Integer start,
      @Nullable @QueryParam("reverse") Boolean reverse,
      @Nullable @QueryParam("startTime") Date startTime,
      @Nullable @QueryParam("endTime") Date endTime)
      throws IOException, BitmexException;

  /**
   * @param apiKey
   * @param nonce
   * @param paramsDigest
   * @param symbol Instrument symbol. e.g. {@code XBTUSD}.
   * @param side Order side. Valid options: Buy, Sell. Defaults to 'Buy' unless orderQty or
   *     simpleOrderQty is negative.
   * @param orderQuantity Order quantity in units of the instrument (i.e. contracts).
   * @param simpleOrderQuantity Order quantity in units of the underlying instrument (i.e. Bitcoin).
   * @param displayQuantity Optional quantity to display in the book. Use 0 for a fully hidden
   *     order.
   * @param price Optional limit price for 'Limit', 'StopLimit', and 'LimitIfTouched' orders.
   * @param stopPrice Optional trigger price for 'Stop', 'StopLimit', 'MarketIfTouched', and
   *     'LimitIfTouched' orders. Use a price below the current price for stop-sell orders and
   *     buy-if-touched orders. Use execInst of 'MarkPrice' or 'LastPrice' to define the current
   *     price used for triggering.
   * @param orderType Order type. Valid options: Market, Limit, Stop, StopLimit, MarketIfTouched,
   *     LimitIfTouched, MarketWithLeftOverAsLimit, Pegged. Defaults to 'Limit' when price is
   *     specified. Defaults to 'Stop' when stopPx is specified. Defaults to 'StopLimit' when price
   *     and stopPx are specified.
   * @param clOrdID Optional Client Order ID. This clOrdID will come back on the order and any
   *     related executions.
   * @param executionInstructions Optional execution instructions. Valid options:
   *     ParticipateDoNotInitiate, AllOrNone, MarkPrice, IndexPrice, LastPrice, Close, ReduceOnly,
   *     Fixed. 'AllOrNone' instruction requires displayQty to be 0. 'MarkPrice', 'IndexPrice' or
   *     'LastPrice' instruction valid for 'Stop', 'StopLimit', 'MarketIfTouched', and
   *     'LimitIfTouched' orders.
   * @param clOrdLinkID Optional Client Order Link ID for contingent orders.
   * @param contingencyType Optional contingency type for use with clOrdLinkID. Valid options:
   *     OneCancelsTheOther, OneTriggersTheOther, OneUpdatesTheOtherAbsolute,
   *     OneUpdatesTheOtherProportional.
   * @param pegPriceType Optional peg price type. Valid options: LastPeg, MidPricePeg, MarketPeg,
   *     PrimaryPeg, TrailingStopPeg.
   * @param pegOffsetValue Optional trailing offset from the current price for 'Stop', 'StopLimit',
   *     'MarketIfTouched', and 'LimitIfTouched' orders; use a negative offset for stop-sell orders
   *     and buy-if-touched orders. Optional offset from the peg price for 'Pegged' orders.
   * @param timeInForce Time in force. Valid options: Day, GoodTillCancel, ImmediateOrCancel,
   *     FillOrKill. Defaults to 'GoodTillCancel' for 'Limit', 'StopLimit', 'LimitIfTouched', and
   *     'MarketWithLeftOverAsLimit' orders.
   * @param text Optional order annotation. e.g. 'Take profit'.
   * @return {@link BitmexPrivateOrder} contains the results of the call.
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
   * @param price Optional limit price for 'Limit', 'StopLimit', and 'LimitIfTouched' orders.
   * @param stopPrice Optional trigger price for 'Stop', 'StopLimit', 'MarketIfTouched', and
   *     'LimitIfTouched' orders. Use a price below the current price for stop-sell orders and
   *     buy-if-touched orders.
   * @param pegOffsetValue Optional trailing offset from the current price for 'Stop', 'StopLimit',
   *     'MarketIfTouched', and 'LimitIfTouched' orders; use a negative offset for stop-sell orders
   *     and buy-if-touched orders. Optional offset from the peg price for 'Pegged' orders.
   * @param text Optional amend annotation. e.g. 'Adjust skew'.
   * @return {@link BitmexPrivateOrder} contains the results of the call.
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

  @POST
  @Path("position/leverage")
  BitmexPosition updateLeveragePosition(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @FormParam("symbol") String symbol,
      @FormParam("leverage") BigDecimal leverage)
      throws IOException, BitmexException;

  class PlaceOrderCommand {

    @JsonProperty("symbol")
    private final String symbol;

    @JsonProperty("side")
    private final String side;

    @JsonProperty("orderQty")
    private final BigDecimal orderQuantity;

    @JsonProperty("simpleOrderQuantity")
    private final BigDecimal simpleOrderQuantity;

    @JsonProperty("displayQuantity")
    private final BigDecimal displayQuantity;

    @JsonProperty("price")
    private final BigDecimal price;

    @JsonProperty("stopPx")
    private final BigDecimal stopPrice;

    @JsonProperty("ordType")
    private final String orderType;

    @JsonProperty("clOrdID")
    private final String clOrdID;

    @JsonProperty("execInst")
    private final String executionInstructions;

    @JsonProperty("clOrdLinkID")
    private final String clOrdLinkID;

    @JsonProperty("contingencyType")
    private final String contingencyType;

    @JsonProperty("pegOffsetValue")
    private final BigDecimal pegOffsetValue;

    @JsonProperty("pegPriceType")
    private final String pegPriceType;

    @JsonProperty("timeInForce")
    private final String timeInForce;

    @JsonProperty("text")
    private final String text;

    /** See {@link Bitmex#placeOrder}. */
    public PlaceOrderCommand(
        String symbol,
        @Nullable BitmexSide side,
        @Nullable BigDecimal orderQuantity,
        @Nullable BigDecimal simpleOrderQuantity,
        @Nullable BigDecimal displayQuantity,
        @Nullable BigDecimal price,
        @Nullable BigDecimal stopPrice,
        @Nullable BitmexOrderType orderType,
        @Nullable String clOrdID,
        @Nullable List<BitmexExecutionInstruction> executionInstructions,
        @Nullable String clOrdLinkID,
        @Nullable BitmexContingencyType contingencyType,
        @Nullable BigDecimal pegOffsetValue,
        @Nullable BitmexPegPriceType pegPriceType,
        @Nullable BitmexTimeInForce timeInForce,
        @Nullable String text) {
      this.symbol = symbol;
      this.side = side != null ? side.getCapitalized() : null;
      this.orderQuantity = orderQuantity;
      this.simpleOrderQuantity = simpleOrderQuantity;
      this.displayQuantity = displayQuantity;
      this.price = price;
      this.stopPrice = stopPrice;
      this.orderType = orderType != null ? orderType.toApiParameter() : null;
      this.clOrdID = clOrdID;
      this.executionInstructions =
          executionInstructions != null ? StringUtils.join(executionInstructions, ",  ") : null;
      this.clOrdLinkID = clOrdLinkID;
      this.contingencyType = contingencyType != null ? contingencyType.toApiParameter() : null;
      this.pegOffsetValue = pegOffsetValue;
      this.pegPriceType = pegPriceType != null ? pegPriceType.toApiParameter() : null;
      this.timeInForce = timeInForce != null ? timeInForce.toApiParameter() : null;
      this.text = text;
    }

    @Override
    public String toString() {
      return "PlaceOrderCommand{"
          + "symbol='"
          + symbol
          + '\''
          + ", side='"
          + side
          + '\''
          + ", orderQuantity="
          + orderQuantity
          + ", simpleOrderQuantity="
          + simpleOrderQuantity
          + ", displayQuantity="
          + displayQuantity
          + ", price="
          + price
          + ", stopPrice="
          + stopPrice
          + ", orderType='"
          + orderType
          + '\''
          + ", clOrdID='"
          + clOrdID
          + '\''
          + ", executionInstructions='"
          + executionInstructions
          + '\''
          + ", clOrdLinkID='"
          + clOrdLinkID
          + '\''
          + ", contingencyType='"
          + contingencyType
          + '\''
          + ", pegOffsetValue="
          + pegOffsetValue
          + ", pegPriceType='"
          + pegPriceType
          + '\''
          + ", timeInForce='"
          + timeInForce
          + '\''
          + ", text='"
          + text
          + '\''
          + '}';
    }
  }

  class ReplaceOrderCommand {

    @Nullable
    @JsonProperty("orderID")
    private final String orderId;

    @Nullable
    @JsonProperty("origClOrdID")
    private final String origClOrdID;

    @Nullable
    @JsonProperty("clOrdID")
    private final String clOrdID;

    @Nullable
    @JsonProperty("simpleOrderQty")
    private final BigDecimal simpleOrderQty;

    @JsonProperty("orderQty")
    private final BigDecimal orderQuantity;

    @JsonProperty("simpleLeavesQty")
    private final BigDecimal simpleLeavesQty;

    @JsonProperty("leavesQty")
    private final BigDecimal leavesQty;

    @Nullable
    @JsonProperty("price")
    private final BigDecimal price;

    @Nullable
    @JsonProperty("stopPx")
    private final BigDecimal stopPrice;

    @Nullable
    @JsonProperty("pegOffsetValue")
    private final BigDecimal pegOffsetValue;

    @Nullable
    @JsonProperty("text")
    private final String text;

    /** See {@link Bitmex#replaceOrder}. */
    public ReplaceOrderCommand(
        @Nullable String orderId,
        @Nullable String origClOrdID,
        @Nullable String clOrdID,
        @Nullable BigDecimal simpleOrderQty,
        @Nullable BigDecimal orderQuantity,
        @Nullable BigDecimal simpleLeavesQty,
        @Nullable BigDecimal leavesQty,
        @Nullable BigDecimal price,
        @Nullable BigDecimal stopPrice,
        @Nullable BigDecimal pegOffsetValue,
        @Nullable String text) {
      this.orderId = orderId;
      this.origClOrdID = origClOrdID;
      this.clOrdID = clOrdID;
      this.simpleOrderQty = simpleOrderQty;
      this.orderQuantity = orderQuantity;
      this.simpleLeavesQty = simpleLeavesQty;
      this.leavesQty = leavesQty;
      this.price = price;
      this.stopPrice = stopPrice;
      this.pegOffsetValue = pegOffsetValue;
      this.text = text;
    }

    @Override
    public String toString() {
      return "ReplaceOrderCommand{"
          + ", orderId='"
          + orderId
          + '\''
          + ", origClOrdID='"
          + origClOrdID
          + '\''
          + ", clOrdID='"
          + clOrdID
          + '\''
          + ", simpleOrderQty="
          + simpleOrderQty
          + ", orderQuantity="
          + orderQuantity
          + ", simpleLeavesQty="
          + simpleLeavesQty
          + ", leavesQty="
          + leavesQty
          + ", price="
          + price
          + ", stopPrice="
          + stopPrice
          + ", pegOffsetValue="
          + pegOffsetValue
          + ", text='"
          + text
          + '\''
          + '}';
    }
  }
}
