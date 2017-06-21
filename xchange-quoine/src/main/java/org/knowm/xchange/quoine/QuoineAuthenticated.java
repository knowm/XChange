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
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.quoine.dto.account.BitcoinAccount;
import org.knowm.xchange.quoine.dto.account.FiatAccount;
import org.knowm.xchange.quoine.dto.account.QuoineAccountBalance;
import org.knowm.xchange.quoine.dto.account.QuoineTradingAccountInfo;
import org.knowm.xchange.quoine.dto.trade.QuoineNewOrderRequestWrapper;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderDetailsResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrdersList;

import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface QuoineAuthenticated extends Quoine {

  @GET
  @Path("fiat_accounts")
  FiatAccount[] getFiatAccountInfo(@HeaderParam("X-Quoine-API-Version") int apiVersion, @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentType) throws IOException;

  @GET
  @Path("crypto_accounts")
  BitcoinAccount[] getCryptoAccountInfo(@HeaderParam("X-Quoine-API-Version") int apiVersion, @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentType) throws IOException;

  @GET
  @Path("accounts/balance")
  QuoineAccountBalance[] getAllBalance(@HeaderParam("X-Quoine-API-Version") int apiVersion, @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentType) throws IOException;

  @GET
  @Path("trading_accounts")
  QuoineTradingAccountInfo[] getTradingAccountInfo(@HeaderParam("X-Quoine-API-Version") int apiVersion,
      @HeaderParam("X-Quoine-Auth") ParamsDigest signer, @HeaderParam("Content-Type") String contentType) throws IOException;

  @POST
  @Path("orders/")
  @Consumes(MediaType.APPLICATION_JSON)
  QuoineOrderResponse placeOrder(@HeaderParam("X-Quoine-API-Version") int apiVersion, @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentTyp, QuoineNewOrderRequestWrapper quoineNewOrderRequestWrapper) throws IOException;

  @PUT
  @Path("orders/{order_id}/cancel")
  QuoineOrderResponse cancelOrder(@HeaderParam("X-Quoine-API-Version") int apiVersion, @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentTyp, @PathParam("order_id") String orderID) throws IOException;

  @GET
  @Path("orders/{order_id}")
  QuoineOrderDetailsResponse orderDetails(@HeaderParam("X-Quoine-API-Version") int apiVersion, @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentTyp, @PathParam("order_id") String orderID) throws IOException;

  @GET
  @Path("orders")
  QuoineOrdersList listOrders(@HeaderParam("X-Quoine-API-Version") int apiVersion, @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentTyp) throws IOException;

}
