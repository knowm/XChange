package com.xeiam.xchange.cryptotrade;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.cryptotrade.dto.CryptoTradeException;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeAccountInfo;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeTransactions;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeCancelOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrderInfoReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrdering;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradePlaceOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades;

@Path("api/1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CryptoTradeAuthenticated extends CryptoTrade {

  @POST
  @Path("private/getinfo")
  CryptoTradeAccountInfo getInfo(@HeaderParam("AuthKey") String apiKey, @HeaderParam("AuthSign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws CryptoTradeException, IOException;

  @POST
  @Path("private/trade")
  CryptoTradePlaceOrderReturn trade(@FormParam("pair") String pair, @FormParam("type") CryptoTradeOrderType type,
      @FormParam("price") BigDecimal rate, @FormParam("amount") BigDecimal amount, @HeaderParam("AuthKey") String apiKey,
      @HeaderParam("AuthSign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws CryptoTradeException,
      IOException;

  @POST
  @Path("private/cancelorder")
  CryptoTradeCancelOrderReturn cancelOrder(@FormParam("orderid") long orderId, @HeaderParam("AuthKey") String apiKey,
      @HeaderParam("AuthSign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws CryptoTradeException,
      IOException;

  @POST
  @Path("private/orderinfo")
  CryptoTradeOrderInfoReturn getOrderInfo(@FormParam("orderid") long orderId, @HeaderParam("AuthKey") String apiKey,
      @HeaderParam("AuthSign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws CryptoTradeException,
      IOException;

  @POST
  @Path("private/tradeshistory")
  CryptoTradeTrades getTradeHistory(@FormParam("start_id") Long startId, @FormParam("end_id") Long endId, @FormParam("start_date") Long startDate,
      @FormParam("end_date") Long endDate, @FormParam("count") Integer count, @FormParam("order") CryptoTradeOrdering ordering,
      @FormParam("pair") String currencyPair, @HeaderParam("AuthKey") String apiKey, @HeaderParam("AuthSign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws CryptoTradeException, IOException;

  @POST
  @Path("private/ordershistory")
  CryptoTradeOrders getOrderHistory(@FormParam("start_id") Long startId, @FormParam("end_id") Long endId, @FormParam("start_date") Long startDate,
      @FormParam("end_date") Long endDate, @FormParam("count") Integer count, @FormParam("order") CryptoTradeOrdering ordering,
      @FormParam("pair") String currencyPair, @HeaderParam("AuthKey") String apiKey, @HeaderParam("AuthSign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws CryptoTradeException, IOException;

  @POST
  @Path("private/transactions")
  CryptoTradeTransactions getTransactionHistory(@FormParam("start_id") Long startId, @FormParam("end_id") Long endId,
      @FormParam("start_date") Long startDate, @FormParam("end_date") Long endDate, @FormParam("count") Integer count,
      @FormParam("order") CryptoTradeOrdering ordering, @HeaderParam("AuthKey") String apiKey, @HeaderParam("AuthSign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws CryptoTradeException, IOException;

}
