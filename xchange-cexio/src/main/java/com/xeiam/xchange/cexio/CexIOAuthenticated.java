package com.xeiam.xchange.cexio;

import com.xeiam.xchange.cexio.dto.account.CexIOBalanceInfo;
import com.xeiam.xchange.cexio.dto.trade.*;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.*;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Author: brox
 * Since:  2/5/14
 */

@Path("api")
@Produces("application/json")
public interface CexIOAuthenticated {

  @POST
  @Path("balance/")
  CexIOBalanceInfo getBalance(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;

  @POST
  @Path("open_orders/{ident}/{currency}/")
  CexIOOrder[] getOpenOrders(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;

  @POST
  @Path("cancel_order/")
  Object cancelOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("id") int orderId) throws IOException;

  @POST
  @Path("place_order/{ident}/{currency}/")
  CexIOOrder placeOrder(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("type") CexIOOrder.Type type, @FormParam("price") BigDecimal price, @FormParam("amount") BigDecimal amount) throws IOException;

}
