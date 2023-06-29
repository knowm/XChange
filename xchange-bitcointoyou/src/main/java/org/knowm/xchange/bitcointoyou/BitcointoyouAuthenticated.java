package org.knowm.xchange.bitcointoyou;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.bitcointoyou.dto.account.BitcointoyouBalance;
import org.knowm.xchange.bitcointoyou.dto.trade.BitcointoyouOrderResponse;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Bitcointoyou Exchange end-points that needs a credential to be used.
 *
 * @author Danilo Guimaraes
 * @author Jonathas Carrijo
 */
@Path("API/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public interface BitcointoyouAuthenticated {

  @GET
  @Path("balance.aspx")
  BitcointoyouBalance returnBalances(
      @HeaderParam("key") String apiKey,
      @HeaderParam("nonce") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("signature") ParamsDigest signature);

  @POST
  @Path("getorders.aspx")
  BitcointoyouOrderResponse returnOrderById(
      @HeaderParam("key") String apiKey,
      @HeaderParam("nonce") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("signature") ParamsDigest signature,
      @QueryParam("id") String orderId)
      throws IOException;

  @GET
  @Path("getorders.aspx?status=OPEN")
  BitcointoyouOrderResponse returnOpenOrders(
      @HeaderParam("key") String apiKey,
      @HeaderParam("nonce") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("signature") ParamsDigest signature)
      throws IOException;

  @GET
  @Path("getorders.aspx?status=CANCELED")
  HashMap<String, BitcointoyouOrderResponse[]> returnCanceledOrders(
      @HeaderParam("key") String apiKey,
      @HeaderParam("nonce") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("signature") ParamsDigest signature)
      throws IOException;

  @GET
  @Path("getorders.aspx?status=EXECUTED")
  HashMap<String, BitcointoyouOrderResponse[]> returnExecutedOrders(
      @HeaderParam("key") String apiKey,
      @HeaderParam("nonce") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("signature") ParamsDigest signature)
      throws IOException;

  @POST
  @Path("createorder.aspx")
  BitcointoyouOrderResponse createOrder(
      @HeaderParam("key") String apiKey,
      @HeaderParam("nonce") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("signature") ParamsDigest signature,
      @QueryParam("asset") String currency,
      @QueryParam("action") String action,
      @QueryParam("amount") BigDecimal amount,
      @QueryParam("price") BigDecimal price)
      throws BitcointoyouException, IOException;

  @POST
  @Path("deleteorders.aspx")
  HashMap<String, String> deleteOrder(
      @HeaderParam("key") String apiKey,
      @HeaderParam("nonce") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("signature") ParamsDigest signature,
      @QueryParam("id") String orderNumber)
      throws IOException;
}