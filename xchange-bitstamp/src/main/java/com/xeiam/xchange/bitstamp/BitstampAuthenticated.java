package com.xeiam.xchange.bitstamp;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBooleanResponse;
import com.xeiam.xchange.bitstamp.dto.account.BitstampDepositAddress;
import com.xeiam.xchange.bitstamp.dto.account.BitstampWithdrawal;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampOrder;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;

/**
 * @author Benedikt Bünz See https://www.bitstamp.net/api/ for up-to-date docs.
 */
@Path("api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitstampAuthenticated {

  @POST
  @Path("open_orders/")
  public BitstampOrder[] getOpenOrders(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;

  @POST
  @Path("buy/")
  public BitstampOrder buy(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price) throws IOException;

  @POST
  @Path("sell/")
  public BitstampOrder sell(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price) throws IOException;

  /** @return true if order has been canceled. */
  @POST
  @Path("cancel_order/")
  public BitstampBooleanResponse cancelOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("id") int orderId)
      throws IOException;

  @POST
  @Path("balance/")
  public BitstampBalance getBalance(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;

  @POST
  @Path("user_transactions/")
  public BitstampUserTransaction[] getUserTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce,
      @FormParam("limit") long numberOfTransactions) throws IOException;

  @POST
  @Path("user_transactions/")
  public BitstampUserTransaction[] getUserTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce,
                                                       @FormParam("limit") long numberOfTransactions, @FormParam("offset") long offset,
                                                       @FormParam("sort") String sort) throws IOException;

  @POST
  @Path("bitcoin_deposit_address/")
  public BitstampDepositAddress getBitcoinDepositAddress(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;

  @POST
  @Path("bitcoin_withdrawal/")
  public BitstampWithdrawal withdrawBitcoin(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address) throws IOException;

}
