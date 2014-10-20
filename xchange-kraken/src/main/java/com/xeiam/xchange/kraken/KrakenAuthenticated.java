package com.xeiam.xchange.kraken;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.kraken.dto.account.results.KrakenBalanceResult;
import com.xeiam.xchange.kraken.dto.account.results.KrakenLedgerResult;
import com.xeiam.xchange.kraken.dto.account.results.KrakenQueryLedgerResult;
import com.xeiam.xchange.kraken.dto.account.results.KrakenTradeBalanceInfoResult;
import com.xeiam.xchange.kraken.dto.account.results.KrakenTradeVolumeResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenCancelOrderResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenClosedOrdersResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenOpenOrdersResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenOpenPositionsResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenOrderResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenQueryOrderResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenQueryTradeResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult;

@Path("0")
@Produces(MediaType.APPLICATION_JSON)
public interface KrakenAuthenticated extends Kraken {

  @POST
  @Path("private/Balance")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenBalanceResult balance(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/TradeBalance")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenTradeBalanceInfoResult tradeBalance(@FormParam("aclass") String assetClass, @FormParam("asset") String asset, @HeaderParam("API-Key") String apiKey,
      @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/Ledgers")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenLedgerResult ledgers(@FormParam("aclass") String assetClass, @FormParam("asset") String assets, @FormParam("type") String ledgerType, @FormParam("start") String start,
      @FormParam("end") String end, @FormParam("ofs") String offset, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/QueryLedgers")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenQueryLedgerResult queryLedgers(@FormParam("id") String ledgerIds, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/AddOrder")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenOrderResult
      addOrder(@FormParam("pair") String pair, @FormParam("type") String type, @FormParam("ordertype") String ordertype, @FormParam("price") String price, @FormParam("price2") String secondaryPrice,
          @FormParam("volume") String volume, @FormParam("leverage") String leverage, @FormParam("position") String positionTxId, @FormParam("oflags") String orderFlags,
          @FormParam("starttm") String startTime, @FormParam("expiretm") String expireTime, @FormParam("userref") String userRefId, @FormParam("close") Map<String, String> closeOrder,
          @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/AddOrder")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenOrderResult addOrderValidateOnly(@FormParam("pair") String pair, @FormParam("type") String type, @FormParam("ordertype") String ordertype, @FormParam("price") String price,
      @FormParam("price2") String secondaryPrice, @FormParam("volume") String volume, @FormParam("leverage") String leverage, @FormParam("position") String positionTxId,
      @FormParam("oflags") String orderFlags, @FormParam("starttm") String startTime, @FormParam("expiretm") String expireTime, @FormParam("userref") String userRefId,
      @FormParam("validate") boolean validateOnly, @FormParam("close") Map<String, String> closeOrder, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/CancelOrder")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenCancelOrderResult cancelOrder(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("txid") String transactionId) throws IOException;

  @POST
  @Path("private/OpenOrders")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenOpenOrdersResult openOrders(@FormParam("trades") boolean includeTrades, @FormParam("userref") String userReferenceId, @HeaderParam("API-Key") String apiKey,
      @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/ClosedOrders")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenClosedOrdersResult closedOrders(@FormParam("trades") boolean includeTrades, @FormParam("userref") String userReferenceId, @FormParam("start") String start,
      @FormParam("end") String end, @FormParam("ofs") String offset, @FormParam("closetime") String closeTime, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/QueryOrders")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenQueryOrderResult queryOrders(@FormParam("trades") boolean includeTrades, @FormParam("userref") String userReferenceId, @FormParam("txid") String transactionIds,
      @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/TradesHistory")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenTradeHistoryResult tradeHistory(@FormParam("type") String type, @FormParam("trades") boolean includeTrades, @FormParam("start") String start, @FormParam("end") String end,
      @FormParam("ofs") String offset, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("private/QueryTrades")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenQueryTradeResult queryTrades(@FormParam("trades") boolean includeTrades, @FormParam("txid") String transactionIds, @HeaderParam("API-Key") String apiKey,
      @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/OpenPositions")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenOpenPositionsResult openPositions(@FormParam("txid") String transactionIds, @FormParam("docalcs") boolean doCalcs, @HeaderParam("API-Key") String apiKey,
      @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/TradeVolume")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public KrakenTradeVolumeResult tradeVolume(@FormParam("pair") String assetPairs, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

}
