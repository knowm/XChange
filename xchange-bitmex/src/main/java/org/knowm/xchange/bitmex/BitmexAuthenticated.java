package org.knowm.xchange.bitmex;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface BitmexAuthenticated extends Bitmex {

  /*
   * @POST
   * @Path("private/Balance")
   * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   * BitmexBalanceResult balance(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
   * @POST
   * @Path("private/TradeBalance")
   * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   * BitmexTradeBalanceInfoResult tradeBalance(@FormParam("aclass") String assetClass, @FormParam("asset") String asset, @HeaderParam("API-Key") String apiKey,
   * @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
   * @POST
   * @Path("private/Ledgers")
   * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   * BitmexLedgerResult ledgers(@FormParam("aclass") String assetClass, @FormParam("asset") String assets, @FormParam("type") String ledgerType, @FormParam("start") String start,
   * @FormParam("end") String end, @FormParam("ofs") Long offset, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
   * @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
   * @POST
   * @Path("private/QueryLedgers")
   * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   * BitmexQueryLedgerResult queryLedgers(@FormParam("id") String ledgerIds, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
   * @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
   * @POST
   * @Path("private/AddOrder")
   * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   * BitmexOrderResult addOrder(@FormParam("pair") String pair, @FormParam("type") String type, @FormParam("ordertype") String ordertype, @FormParam("price") String price,
   * @FormParam("price2") String secondaryPrice, @FormParam("volume") String volume, @FormParam("leverage") String leverage, @FormParam("position") String positionTxId,
   * @FormParam("oflags") String orderFlags, @FormParam("starttm") String startTime, @FormParam("expiretm") String expireTime, @FormParam("userref") String userRefId,
   * @FormParam("close") Map<String, String> closeOrder, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
   * throws IOException;
   * @POST
   * @Path("private/AddOrder")
   * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   * BitmexOrderResult addOrderValidateOnly(@FormParam("pair") String pair, @FormParam("type") String type, @FormParam("ordertype") String ordertype, @FormParam("price") String price,
   * @FormParam("price2") String secondaryPrice, @FormParam("volume") String volume, @FormParam("leverage") String leverage, @FormParam("position") String positionTxId,
   * @FormParam("oflags") String orderFlags, @FormParam("starttm") String startTime, @FormParam("expiretm") String expireTime, @FormParam("userref") String userRefId,
   * @FormParam("validate") boolean validateOnly, @FormParam("close") Map<String, String> closeOrder, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
   * @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
   * @POST
   * @Path("private/CancelOrder")
   * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   * BitmexCancelOrderResult cancelOrder(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
   * @FormParam("txid") String transactionId) throws IOException;
   * @POST
   * @Path("private/OpenOrders")
   * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   * BitmexOpenOrdersResult openOrders(@FormParam("trades") boolean includeTrades, @FormParam("userref") String userReferenceId, @HeaderParam("API-Key") String apiKey,
   * @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
   * @POST
   * @Path("private/ClosedOrders")
   * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   * BitmexClosedOrdersResult closedOrders(@FormParam("trades") boolean includeTrades, @FormParam("userref") String userReferenceId, @FormParam("start") String start, @FormParam("end") String end,
   * @FormParam("ofs") String offset, @FormParam("closetime") String closeTime, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
   * @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
   * @POST
   * @Path("private/QueryOrders")
   * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   * BitmexQueryOrderResult queryOrders(@FormParam("trades") boolean includeTrades, @FormParam("userref") String userReferenceId, @FormParam("txid") String transactionIds,
   * @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
   *//**
      * Get trades history
      *
      * @param type ype = type of trade (optional) all = all types (default) any position = any position (open or closed) closed position = positions
      *          that have been closed closing position = any trade closing all or part of a position no position = non-positional trades
      * @param includeTrades whether or not to include trades related to position in output (optional. default = false)
      * @param start starting unix timestamp or trade tx id of results (optional. exclusive)
      * @param end ending unix timestamp or trade tx id of results (optional. inclusive)
      * @param offset result offset
      *//*
         * @POST
         * @Path("private/TradesHistory")
         * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
         * BitmexTradeHistoryResult tradeHistory(@FormParam("type") String type, @FormParam("trades") boolean includeTrades, @FormParam("start") Long start, @FormParam("end") Long end,
         * @FormParam("ofs") Long offset, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws
         * IOException;
         * @POST
         * @Path("private/QueryTrades")
         * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
         * BitmexQueryTradeResult queryTrades(@FormParam("trades") boolean includeTrades, @FormParam("txid") String transactionIds, @HeaderParam("API-Key") String apiKey,
         * @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
         * @POST
         * @Path("private/OpenPositions")
         * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
         * BitmexOpenPositionsResult openPositions(@FormParam("txid") String transactionIds, @FormParam("docalcs") boolean doCalcs, @HeaderParam("API-Key") String apiKey,
         * @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
         * @POST
         * @Path("private/TradeVolume")
         * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
         * BitmexTradeVolumeResult tradeVolume(@FormParam("pair") String assetPairs, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
         * @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
         * @POST
         * @Path("private/DepositAddresses")
         * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
         * BitmexDepositAddressResult getDepositAddresses(@FormParam("aclass") String assetPairs, @FormParam("asset") String assets, @FormParam("method") String method,
         * // @FormParam("new") boolean newAddress,
         * @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
         * @POST
         * @Path("private/DepositMethods")
         * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
         * BitmexDepositMethodsResults getDepositMethods(@FormParam("aclass") String assetPairs, @FormParam("asset") String assets, @HeaderParam("API-Key") String apiKey,
         * @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
         * @POST
         * @Path("private/WithdrawInfo")
         * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
         * WithdrawInfoResult getWithdrawInfo(@FormParam("aclass") String assetPairs, @FormParam("asset") String assets, @FormParam("key") String key, @FormParam("amount") BigDecimal amount,
         * @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
         * @POST
         * @Path("private/Withdraw")
         * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
         * WithdrawResult withdraw(@FormParam("aclass") String assetPairs, @FormParam("asset") String assets, @FormParam("key") String key, @FormParam("amount") BigDecimal amount,
         * @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
         * @POST
         * @Path("private/DepositStatus")
         * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
         * DepositStatusResult getDepositStatus(@FormParam("aclass") String assetPairs, @FormParam("asset") String assets, @FormParam("method") String method, @HeaderParam("API-Key") String apiKey,
         * @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
         * @POST
         * @Path("private/WithdrawStatus")
         * @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
         * WithdrawStatusResult getWithdrawStatus(@FormParam("aclass") String assetPairs, @FormParam("asset") String assets, @FormParam("method") String method, @HeaderParam("API-Key") String apiKey,
         * @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
         */
}
