package org.knowm.xchange.kraken;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.kraken.dto.account.results.DepositStatusResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenBalanceResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenDepositAddressResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenDepositMethodsResults;
import org.knowm.xchange.kraken.dto.account.results.KrakenLedgerResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenQueryLedgerResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenTradeBalanceInfoResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenTradeVolumeResult;
import org.knowm.xchange.kraken.dto.account.results.WithdrawInfoResult;
import org.knowm.xchange.kraken.dto.account.results.WithdrawResult;
import org.knowm.xchange.kraken.dto.account.results.WithdrawStatusResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenCancelOrderResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenClosedOrdersResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenOpenOrdersResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenOpenPositionsResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenOrderResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenQueryOrderResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenQueryTradeResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("0")
@Produces(MediaType.APPLICATION_JSON)
public interface KrakenAuthenticated extends Kraken {

  @POST
  @Path("private/Balance")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenBalanceResult balance(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/TradeBalance")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenTradeBalanceInfoResult tradeBalance(@FormParam("aclass") String assetClass, @FormParam("asset") String asset,
      @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("private/Ledgers")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenLedgerResult ledgers(@FormParam("aclass") String assetClass, @FormParam("asset") String assets, @FormParam("type") String ledgerType,
      @FormParam("start") String start, @FormParam("end") String end, @FormParam("ofs") String offset, @HeaderParam("API-Key") String apiKey,
      @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/QueryLedgers")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenQueryLedgerResult queryLedgers(@FormParam("id") String ledgerIds, @HeaderParam("API-Key") String apiKey,
      @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/AddOrder")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenOrderResult addOrder(@FormParam("pair") String pair, @FormParam("type") String type, @FormParam("ordertype") String ordertype,
      @FormParam("price") String price, @FormParam("price2") String secondaryPrice, @FormParam("volume") String volume,
      @FormParam("leverage") String leverage, @FormParam("position") String positionTxId, @FormParam("oflags") String orderFlags,
      @FormParam("starttm") String startTime, @FormParam("expiretm") String expireTime, @FormParam("userref") String userRefId,
      @FormParam("close") Map<String, String> closeOrder, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/AddOrder")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenOrderResult addOrderValidateOnly(@FormParam("pair") String pair, @FormParam("type") String type,
      @FormParam("ordertype") String ordertype, @FormParam("price") String price, @FormParam("price2") String secondaryPrice,
      @FormParam("volume") String volume, @FormParam("leverage") String leverage, @FormParam("position") String positionTxId,
      @FormParam("oflags") String orderFlags, @FormParam("starttm") String startTime, @FormParam("expiretm") String expireTime,
      @FormParam("userref") String userRefId, @FormParam("validate") boolean validateOnly, @FormParam("close") Map<String, String> closeOrder,
      @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("private/CancelOrder")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenCancelOrderResult cancelOrder(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("txid") String transactionId) throws IOException;

  @POST
  @Path("private/OpenOrders")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenOpenOrdersResult openOrders(@FormParam("trades") boolean includeTrades, @FormParam("userref") String userReferenceId,
      @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("private/ClosedOrders")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenClosedOrdersResult closedOrders(@FormParam("trades") boolean includeTrades, @FormParam("userref") String userReferenceId,
      @FormParam("start") String start, @FormParam("end") String end, @FormParam("ofs") String offset, @FormParam("closetime") String closeTime,
      @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("private/QueryOrders")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenQueryOrderResult queryOrders(@FormParam("trades") boolean includeTrades, @FormParam("userref") String userReferenceId,
      @FormParam("txid") String transactionIds, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  /**
   * Get trades history
   *
   * @param type ype = type of trade (optional) all = all types (default) any position = any position (open or closed) closed position = positions
   *        that have been closed closing position = any trade closing all or part of a position no position = non-positional trades
   * @param includeTrades whether or not to include trades related to position in output (optional. default = false)
   * @param start starting unix timestamp or trade tx id of results (optional. exclusive)
   * @param end ending unix timestamp or trade tx id of results (optional. inclusive)
   * @param offset result offset
   */
  @POST
  @Path("private/TradesHistory")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenTradeHistoryResult tradeHistory(@FormParam("type") String type, @FormParam("trades") boolean includeTrades,
      @FormParam("start") Long start, @FormParam("end") Long end, @FormParam("ofs") Long offset, @HeaderParam("API-Key") String apiKey,
      @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/QueryTrades")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenQueryTradeResult queryTrades(@FormParam("trades") boolean includeTrades, @FormParam("txid") String transactionIds,
      @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("private/OpenPositions")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenOpenPositionsResult openPositions(@FormParam("txid") String transactionIds, @FormParam("docalcs") boolean doCalcs,
      @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("private/TradeVolume")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenTradeVolumeResult tradeVolume(@FormParam("pair") String assetPairs, @HeaderParam("API-Key") String apiKey,
      @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/DepositAddresses")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenDepositAddressResult getDepositAddresses(@FormParam("aclass") String assetPairs, @FormParam("asset") String assets,
      @FormParam("method") String method,
      //		  	@FormParam("new") boolean newAddress,
      @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("private/DepositMethods")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenDepositMethodsResults getDepositMethods(@FormParam("aclass") String assetPairs, @FormParam("asset") String assets,
      @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("private/WithdrawInfo")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public WithdrawInfoResult getWithdrawInfo(@FormParam("aclass") String assetPairs, @FormParam("asset") String assets, @FormParam("key") String key,
      @FormParam("amount") BigDecimal amount, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/Withdraw")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public WithdrawResult withdraw(@FormParam("aclass") String assetPairs, @FormParam("asset") String assets, @FormParam("key") String key,
      @FormParam("amount") BigDecimal amount, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;


 @POST
 @Path("private/DepositStatus")
 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
 public DepositStatusResult getDepositStatus(@FormParam("aclass") String assetPairs, @FormParam("asset") String assets, @FormParam("method") String method
         , @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer
         , @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

 @POST
 @Path("private/WithdrawStatus")
 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
 public WithdrawStatusResult getWithdrawStatus(@FormParam("aclass") String assetPairs, @FormParam("asset") String assets, @FormParam("method") String method
         , @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer
         , @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

}
