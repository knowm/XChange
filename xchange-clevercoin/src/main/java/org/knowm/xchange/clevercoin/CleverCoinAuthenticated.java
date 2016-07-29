package org.knowm.xchange.clevercoin;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.clevercoin.dto.CleverCoinException;
import org.knowm.xchange.clevercoin.dto.account.CleverCoinBalance;
import org.knowm.xchange.clevercoin.dto.account.CleverCoinDepositAddress;
import org.knowm.xchange.clevercoin.dto.account.CleverCoinWithdrawal;
import org.knowm.xchange.clevercoin.dto.trade.CleverCoinCancelOrder;
import org.knowm.xchange.clevercoin.dto.trade.CleverCoinOpenOrder;
import org.knowm.xchange.clevercoin.dto.trade.CleverCoinOrder;
import org.knowm.xchange.clevercoin.dto.trade.CleverCoinUserTransaction;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Karsten Nilsen & Konstantin Indjov See https://github.com/CleverCoin/clever-api-client/ for up-to-date info.
 */
@Path("v1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CleverCoinAuthenticated extends CleverCoin {

  @GET
  @Path("orders/limited")
  public CleverCoinOrder[] getOpenOrders(@HeaderParam("X-CleverAPI-Key") String apiKey, @HeaderParam("X-CleverAPI-Signature") ParamsDigest signer,
      @HeaderParam("X-CleverAPI-Nonce") SynchronizedValueFactory<Long> nonce) throws CleverCoinException, IOException;

  @POST
  @Path("orders/limited")
  public CleverCoinOpenOrder createLimitedOrder(@HeaderParam("X-CleverAPI-Key") String apiKey,
      @HeaderParam("X-CleverAPI-Signature") ParamsDigest signer, @HeaderParam("X-CleverAPI-Nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("type") String type, @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal price)
      throws CleverCoinException, IOException;

  @DELETE
  @Path("orders/limited")
  public CleverCoinCancelOrder cancelOrder(@HeaderParam("X-CleverAPI-Key") String apiKey, @HeaderParam("X-CleverAPI-Signature") ParamsDigest signer,
      @HeaderParam("X-CleverAPI-Nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("orderID") int orderId)
      throws CleverCoinException, IOException;

  @GET
  @Path("wallets")
  public CleverCoinBalance[] getBalance(@HeaderParam("X-CleverAPI-Key") String apiKey, @HeaderParam("X-CleverAPI-Signature") ParamsDigest signer,
      @HeaderParam("X-CleverAPI-Nonce") SynchronizedValueFactory<Long> nonce) throws CleverCoinException, IOException;

  @GET
  @Path("trades")
  public CleverCoinUserTransaction[] getUserTransactions(@HeaderParam("X-CleverAPI-Key") String apiKey,
      @HeaderParam("X-CleverAPI-Signature") ParamsDigest signer, @HeaderParam("X-CleverAPI-Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("count") int count) throws CleverCoinException, IOException;

  @GET
  @Path("bitcoin/depositAddress")
  public CleverCoinDepositAddress getBitcoinDepositAddress(@HeaderParam("X-CleverAPI-Key") String apiKey,
      @HeaderParam("X-CleverAPI-Signature") ParamsDigest signer, @HeaderParam("X-CleverAPI-Nonce") SynchronizedValueFactory<Long> nonce)
      throws CleverCoinException, IOException;

  @POST
  @Path("bitcoin/withdrawal")
  public CleverCoinWithdrawal withdrawBitcoin(@HeaderParam("X-CleverAPI-Key") String apiKey,
      @HeaderParam("X-CleverAPI-Signature") ParamsDigest signer, @HeaderParam("X-CleverAPI-Nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount, @FormParam("toAddress") String toAddress) throws CleverCoinException, IOException;

}
