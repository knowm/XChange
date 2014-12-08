package com.xeiam.xchange.bitstamp;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bitstamp.dto.BitstampException;
import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.account.BitstampDepositAddress;
import com.xeiam.xchange.bitstamp.dto.account.BitstampWithdrawal;
import com.xeiam.xchange.bitstamp.dto.account.DepositTransaction;
import com.xeiam.xchange.bitstamp.dto.account.WithdrawalRequest;
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
  public BitstampOrder[] getOpenOrders(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce)
          throws BitstampException, IOException;

  @POST
  @Path("buy/")
  public BitstampOrder buy(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price)
          throws BitstampException, IOException;

  @POST
  @Path("sell/")
  public BitstampOrder sell(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price)
          throws BitstampException, IOException;

  /** @return true if order has been canceled. */
  @POST
  @Path("cancel_order/")
  public boolean cancelOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("id") int orderId)
          throws BitstampException, IOException;

  @POST
  @Path("balance/")
  public BitstampBalance getBalance(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce) throws BitstampException, IOException;

  @POST
  @Path("user_transactions/")
  public BitstampUserTransaction[] getUserTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce,
      @FormParam("limit") long numberOfTransactions) throws BitstampException, IOException;

  @POST
  @Path("user_transactions/")
  public BitstampUserTransaction[] getUserTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce,
      @FormParam("limit") long numberOfTransactions, @FormParam("offset") long offset, @FormParam("sort") String sort) throws BitstampException, IOException;

  @POST
  @Path("bitcoin_deposit_address/")
  public BitstampDepositAddress getBitcoinDepositAddress(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce) throws BitstampException, IOException;

  @POST
  @Path("bitcoin_withdrawal/")
  public BitstampWithdrawal withdrawBitcoin(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address) throws BitstampException, IOException;

  @POST
  @Path("unconfirmed_btc/")
  public DepositTransaction[] getUnconfirmedDeposits(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce)
      throws BitstampException, IOException;

  @POST
  @Path("withdrawal_requests/")
  public WithdrawalRequest[] getWithdrawalRequests(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce)
      throws BitstampException, IOException;

}
