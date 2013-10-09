package com.xeiam.xchange.bitstamp;

import java.math.BigDecimal;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampOrder;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;

/**
 * @author Benedikt Bünz See https://www.bitstamp.net/api/ for up-to-date docs.
 */
@Path("api")
@Produces("application/json")
public interface BitstampAuthenticated {

  @POST
  @Path("open_orders/")
  @Produces("application/json")
  public BitstampOrder[] getOpenOrders(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce);

  @POST
  @Path("buy/")
  @Produces("application/json")
  public BitstampOrder buy(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price);
  @POST
  @Path("sell/")
  @Produces("application/json")
  public BitstampOrder sell(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price);
  /** @return true if order has been canceled. */
  @POST
  @Path("cancel_order/")
  @Produces("application/json")
  public Object cancelOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("id") int orderId);
  @POST
  @Path("balance/")
  @Produces("application/json")
  public BitstampBalance getBalance(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce);


  @POST
  @Path("user_transactions/")
  @Produces("application/json")
  public BitstampUserTransaction[] getUserTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("limit") long numberOfTransactions);
 
  @POST
  @Path("bitcoin_deposit_address/")
  @Produces("application/json")
  public String getBitcoinDepositAddress(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce);

  @POST
  @Path("bitcoin_withdrawal/")
  @Produces("application/json")
  public Object withdrawBitcoin(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("amount") BigDecimal amount, @FormParam("address") String address);

  @POST
  @Path("user_transactions/")
  @Produces("application/json")
  public BitstampUserTransaction[] getUserTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("amount") BigDecimal amount, @FormParam("limit") long numberOfTransactions);
 
 
}
