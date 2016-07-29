package org.knowm.xchange.cexio;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.cexio.dto.account.CexIOBalanceInfo;
import org.knowm.xchange.cexio.dto.account.GHashIOHashrate;
import org.knowm.xchange.cexio.dto.account.GHashIOWorkers;
import org.knowm.xchange.cexio.dto.trade.CexIOOpenOrders;
import org.knowm.xchange.cexio.dto.trade.CexIOOrder;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author brox
 */
@Path("api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CexIOAuthenticated extends CexIO {

  @POST
  @Path("balance/")
  CexIOBalanceInfo getBalance(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("open_orders/{ident}/{currency}/")
  CexIOOpenOrders getOpenOrders(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency,
      @FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("cancel_order/")
  Object cancelOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("id") long orderId) throws IOException;

  @POST
  @Path("place_order/{ident}/{currency}/")
  CexIOOrder placeOrder(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("type") CexIOOrder.Type type,
      @FormParam("price") BigDecimal price, @FormParam("amount") BigDecimal amount) throws IOException;

  // GHash.IO calls
  @POST
  @Path("ghash.io/hashrate")
  GHashIOHashrate getHashrate(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("ghash.io/workers")
  GHashIOWorkers getWorkers(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

}
