package org.known.xchange.dsx;

import java.io.IOException;
import java.math.BigDecimal;

import javax.swing.SortOrder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.known.xchange.dsx.dto.account.DSXAccountInfoReturn;
import org.known.xchange.dsx.dto.account.DSXCryptoDepositAddressReturn;
import org.known.xchange.dsx.dto.account.DSXCryptoWithdrawReturn;
import org.known.xchange.dsx.dto.account.DSXFiatWithdrawReturn;
import org.known.xchange.dsx.dto.account.DSXTransaction;
import org.known.xchange.dsx.dto.account.DSXTransactionReturn;
import org.known.xchange.dsx.dto.account.DSXTransactionStatusReturn;
import org.known.xchange.dsx.dto.trade.DSXActiveOrdersReturn;
import org.known.xchange.dsx.dto.trade.DSXCancelOrderReturn;
import org.known.xchange.dsx.dto.trade.DSXOrder;
import org.known.xchange.dsx.dto.trade.DSXOrderHistoryReturn;
import org.known.xchange.dsx.dto.trade.DSXTradeHistoryReturn;
import org.known.xchange.dsx.dto.trade.DSXTradeReturn;
import org.known.xchange.dsx.dto.trade.DSXTransHistoryReturn;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Mikhail Wall
 */

@Path("/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface DSXAuthenticated extends DSX {

  @POST
  @Path("tapi")
  @FormParam("method")
  DSXAccountInfoReturn getInfo(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("tapi")
  @FormParam("method")
  DSXActiveOrdersReturn ActiveOrders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("pair") String pair) throws IOException;

  @POST
  @Path("tapi")
  @FormParam("method")
  DSXTransHistoryReturn TransHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("from") Long from, @DefaultValue("1000") @FormParam("count") Long count,
      @FormParam("from_id") Long fromId, @FormParam("end_id") Long endId, @FormParam("order") SortOrder order, @FormParam("since") Long since,
      @FormParam("end") Long end) throws IOException;

  @POST
  @Path("tapi")
  @FormParam("method")
  DSXTradeHistoryReturn TradeHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("from") Long from, @DefaultValue("1000") @FormParam("count") Long count,
      @FormParam("from_id") Long fromId, @FormParam("end_id") Long endId, @FormParam("order") SortOrder order, @FormParam("since") Long since,
      @FormParam("end") Long end, @FormParam("pair") String pair) throws IOException;

  @POST
  @Path("tapi")
  @FormParam("method")
  DSXOrderHistoryReturn OrderHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("from") Long from, @DefaultValue("1000") @FormParam("count") Long count,
      @FormParam("from_id") Long fromId, @FormParam("end_id") Long endId, @FormParam("order") SortOrder order, @FormParam("since") Long since,
      @FormParam("end") Long end, @FormParam("pair") String pair) throws IOException;

  @POST
  @Path("tapi")
  @FormParam("method")
  DSXTradeReturn Trade(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("type") DSXOrder.Type type, @FormParam("rate") BigDecimal rate, @FormParam("amount") BigDecimal amount, @FormParam("pair") String
      pair) throws IOException;

  @POST
  @Path("tapi")
  @FormParam("method")
  DSXCancelOrderReturn CancelOrder(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce")
      SynchronizedValueFactory<Long> nonce, @FormParam("order_id") Long orderId) throws IOException;

  @POST
  @Path("dwapi")
  @FormParam("method")
  DSXCryptoDepositAddressReturn getCryptoDepositAddress(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce")
      SynchronizedValueFactory<Long> nonce, @FormParam("currency") String currency, @DefaultValue("0") @FormParam("newAddress") int newAddress)
      throws IOException;

  @POST
  @Path("dwapi")
  @FormParam("method")
  DSXCryptoWithdrawReturn cryptoWithdraw(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce")
      SynchronizedValueFactory<Long> nonce, @FormParam("currency") String currency, @FormParam("address") String address,
      @FormParam("amount") BigDecimal amount, @FormParam("commission") BigDecimal commission) throws IOException;

  @POST
  @Path("dwapi")
  @FormParam("method")
  DSXFiatWithdrawReturn fiatWithdraw(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce")
      SynchronizedValueFactory<Long> nonce, @FormParam("currency") String currency, @FormParam("amount") BigDecimal amount) throws IOException;

  @POST
  @Path("dwapi")
  @FormParam("method")
  DSXTransactionStatusReturn getTransactionsStatus(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce")
      SynchronizedValueFactory<Long> nonce, @FormParam("id") Long id) throws IOException;

  @POST
  @Path("dwapi")
  @FormParam("method")
  DSXTransactionReturn getTransactions(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce")
      SynchronizedValueFactory<Long> nonce, @FormParam("from") Long from, @FormParam("to") Long to, @FormParam("fromId") Long fromId,
      @FormParam("told") Long told, @FormParam("type") DSXTransaction.Type type, @FormParam("status") DSXTransaction.Status status,
      @FormParam("currency") String currency) throws IOException;
}
