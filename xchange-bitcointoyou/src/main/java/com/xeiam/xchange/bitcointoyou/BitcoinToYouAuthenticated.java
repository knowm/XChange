package com.xeiam.xchange.bitcointoyou;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.bitcointoyou.dto.BitcoinToYouBaseTradeApiResult;
import com.xeiam.xchange.bitcointoyou.dto.account.BitcoinToYouBalance;
import com.xeiam.xchange.bitcointoyou.dto.trade.BitcoinToYouOrder;

/**
 * Implementation note: the foobar param is necessary because if the body is empty, the server is returning HTTP 411 (missing Content-Length header).
 * At least until Dec 2014.
 *
 * @author Felipe Micaroni Lalli
 * @see BitcoinToYou
 */
@Path("API")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitcoinToYouAuthenticated extends BitcoinToYou {

  @POST
  @Path("balance.aspx")
  public BitcoinToYouBaseTradeApiResult<BitcoinToYouBalance[]> getBalance(@HeaderParam("key") String key, @HeaderParam("nonce") Long nonce,
      @HeaderParam("signature") ParamsDigest signature, @FormParam("foobar") Long foobar) throws IOException;

  @POST
  @Path("getOrders.aspx")
  public BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> getOrders(@HeaderParam("key") String key, @HeaderParam("nonce") Long nonce,
      @HeaderParam("signature") ParamsDigest signature, @FormParam("foobar") Long foobar, @QueryParam("status") String status) throws IOException;

  @POST
  @Path("createOrder.aspx")
  BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> createOrder(@HeaderParam("key") String key, @HeaderParam("nonce") Long nonce,
      @HeaderParam("signature") ParamsDigest signature, @FormParam("foobar") Long foobar, @QueryParam("asset") String asset,
      @QueryParam("action") String action, @QueryParam("amount") BigDecimal amount, @QueryParam("price") BigDecimal price);

  @POST
  @Path("deleteOrders.aspx")
  BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder> deleteOrder(@HeaderParam("key") String key, @HeaderParam("nonce") Long nonce,
      @HeaderParam("signature") ParamsDigest signature, @FormParam("foobar") Long foobar, @QueryParam("id") String id);
}
