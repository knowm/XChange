package org.knowm.xchange.quoine;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.quoine.dto.account.QuoineAccountInfo;
import org.knowm.xchange.quoine.dto.account.QuoineTradingAccountInfo;
import org.knowm.xchange.quoine.dto.trade.QuoineNewOrderRequest;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderDetailsResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrdersList;

import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface QuoineAuthenticated extends Quoine {

  @GET
  @Path("accounts")
  public QuoineAccountInfo getAccountInfo(@HeaderParam("Content-Type") String contentType, @HeaderParam("Content-MD5") ParamsDigest contentMD5,
      @HeaderParam("Date") String date, @HeaderParam("NONCE") String nonce, @HeaderParam("Authorization") ParamsDigest signer) throws IOException;

  @GET
  @Path("trading_accounts")
  public QuoineTradingAccountInfo[] getTradingAccountInfo(@HeaderParam("Content-Type") String contentType,
      @HeaderParam("Content-MD5") ParamsDigest contentMD5, @HeaderParam("Date") String date, @HeaderParam("NONCE") String nonce,
      @HeaderParam("Authorization") ParamsDigest signer) throws IOException;

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  public QuoineOrderResponse placeOrder(@HeaderParam("Content-Type") String contentType, @HeaderParam("Content-MD5") ParamsDigest contentMD5,
      @HeaderParam("Date") String date, @HeaderParam("NONCE") String nonce, @HeaderParam("Authorization") ParamsDigest signer,
      QuoineNewOrderRequest quoineNewOrderRequest) throws IOException;

  @PUT
  @Path("orders/{order_id}/cancel")
  public QuoineOrderResponse cancelOrder(@HeaderParam("Content-Type") String contentType, @HeaderParam("Content-MD5") ParamsDigest contentMD5,
      @HeaderParam("Date") String date, @HeaderParam("NONCE") String nonce, @HeaderParam("Authorization") ParamsDigest signer,
      @PathParam("order_id") String orderID) throws IOException;

  @GET
  @Path("orders/{order_id}")
  public QuoineOrderDetailsResponse orderDetails(@HeaderParam("Content-Type") String contentType, @HeaderParam("Content-MD5") ParamsDigest contentMD5,
      @HeaderParam("Date") String date, @HeaderParam("NONCE") String nonce, @HeaderParam("Authorization") ParamsDigest signer,
      @PathParam("order_id") String orderID) throws IOException;

  @GET
  @Path("orders")
  public QuoineOrdersList listOrders(@HeaderParam("Content-Type") String contentType, @HeaderParam("Content-MD5") ParamsDigest contentMD5,
      @HeaderParam("Date") String date, @HeaderParam("NONCE") String nonce, @HeaderParam("Authorization") ParamsDigest signer,
      @QueryParam("currency_pair_code") String currencyPair) throws IOException;

}
