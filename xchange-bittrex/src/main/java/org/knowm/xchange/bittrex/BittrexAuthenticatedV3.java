package org.knowm.xchange.bittrex;

import java.io.IOException;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bittrex.dto.account.BittrexAccountVolume;
import org.knowm.xchange.bittrex.service.batch.BatchOrderResponse;
import org.knowm.xchange.bittrex.service.batch.order.BatchOrder;
import org.knowm.xchange.bittrex.service.batch.order.neworder.NewOrderPayload;
import si.mazi.rescu.ParamsDigest;

@Path("v3")
@Produces(MediaType.APPLICATION_JSON)
public interface BittrexAuthenticatedV3 {

  @GET
  @Path("account/volume")
  BittrexAccountVolume getAccountVolume(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature)
      throws IOException;

  @POST
  @Path("batch")
  @Consumes(MediaType.APPLICATION_JSON)
  BatchOrderResponse[] executeOrdersBatch(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      BatchOrder[] batchOrders)
      throws IOException;

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  Map<String, Object> placeOrder(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      NewOrderPayload newOrderPayload)
      throws IOException;

  @DELETE
  @Path("orders/{order_id}")
  Map<String, Object> cancelOrder(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @PathParam("order_id") String accountId)
      throws IOException;
}
