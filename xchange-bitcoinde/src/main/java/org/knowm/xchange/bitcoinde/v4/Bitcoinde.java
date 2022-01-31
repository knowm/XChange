package org.knowm.xchange.bitcoinde.v4;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeResponse;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountLedgerWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeCompactOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTradesWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeIdResponse;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyOrdersWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTradesWrapper;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("v4")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitcoinde {

  @GET
  @Path("{trading_pair}/orderbook/compact")
  BitcoindeCompactOrderbookWrapper getCompactOrderBook(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("trading_pair") String tradingPair)
      throws IOException, BitcoindeException;

  @GET
  @Path("{trading_pair}/orderbook")
  BitcoindeOrderbookWrapper getOrderBook(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("trading_pair") String tradingPair,
      @QueryParam("type") String type,
      @QueryParam("order_requirements_fullfilled") Integer orderRequirementsFullfilled,
      @QueryParam("only_kyc_full") Integer onlyKycFull,
      @QueryParam("only_express_orders") Integer onlyExpressOrders)
      throws IOException, BitcoindeException;

  @GET
  @Path("{trading_pair}/trades/history")
  BitcoindeTradesWrapper getTrades(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("trading_pair") String tradingPair,
      @QueryParam("since_tid") Integer since)
      throws IOException, BitcoindeException;

  @GET
  @Path("account")
  BitcoindeAccountWrapper getAccount(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest)
      throws IOException, BitcoindeException;

  @GET
  @Path("{currency}/account/ledger")
  BitcoindeAccountLedgerWrapper getAccountLedger(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("currency") String currency,
      @QueryParam("type") String type,
      @QueryParam("datetime_start") String datetimeStart,
      @QueryParam("datetime_end") String datetimeEnd,
      @QueryParam("page") Integer page)
      throws IOException, BitcoindeException;

  @GET
  @Path("orders")
  BitcoindeMyOrdersWrapper getMyOrders(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @QueryParam("type") String type,
      @QueryParam("state") Integer state,
      @QueryParam("date_start") String start,
      @QueryParam("date_end") String end,
      @QueryParam("page") Integer page)
      throws IOException, BitcoindeException;

  @GET
  @Path("{trading_pair}/orders")
  BitcoindeMyOrdersWrapper getMyOrders(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("trading_pair") String tradingPair,
      @QueryParam("type") String type,
      @QueryParam("state") Integer state,
      @QueryParam("date_start") String start,
      @QueryParam("date_end") String end,
      @QueryParam("page") Integer page)
      throws IOException, BitcoindeException;

  @POST
  @Path("{trading_pair}/orders")
  BitcoindeIdResponse createOrder(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @FormParam("max_amount_currency_to_trade") BigDecimal maxAmount,
      @FormParam("price") BigDecimal price,
      @PathParam("trading_pair") String tradingPair,
      @FormParam("type") String type)
      throws IOException, BitcoindeException;

  @DELETE
  @Path("{trading_pair}/orders/{order_id}")
  BitcoindeResponse deleteOrder(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("order_id") String orderId,
      @PathParam("trading_pair") String tradingPair)
      throws IOException, BitcoindeException;

  @GET
  @Path("trades")
  BitcoindeMyTradesWrapper getMyTrades(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @QueryParam("type") String type,
      @QueryParam("state") Integer state,
      @QueryParam("only_trades_with_action_for_payment_or_transfer_required")
          Integer onlyTradesWithActionForPaymentOrTransferRequired,
      @QueryParam("payment_method") Integer paymentMethod,
      @QueryParam("date_start") String dateStart,
      @QueryParam("date_end") String dateEnd,
      @QueryParam("page") Integer page)
      throws IOException, BitcoindeException;

  @GET
  @Path("{trading_pair}/trades")
  BitcoindeMyTradesWrapper getMyTrades(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("trading_pair") String tradingPair,
      @QueryParam("type") String type,
      @QueryParam("state") Integer state,
      @QueryParam("only_trades_with_action_for_payment_or_transfer_required")
          Integer onlyTradesWithActionForPaymentOrTransferRequired,
      @QueryParam("payment_method") Integer paymentMethod,
      @QueryParam("date_start") String dateStart,
      @QueryParam("date_end") String dateEnd,
      @QueryParam("page") Integer page)
      throws IOException, BitcoindeException;
}
