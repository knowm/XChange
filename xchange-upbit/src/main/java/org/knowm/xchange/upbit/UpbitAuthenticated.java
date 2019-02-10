package org.knowm.xchange.upbit;

import java.io.IOException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.upbit.dto.UpbitException;
import org.knowm.xchange.upbit.dto.account.UpbitBalances;
import org.knowm.xchange.upbit.dto.trade.UpbitOrderRequest;
import org.knowm.xchange.upbit.dto.trade.UpbitOrderResponse;
import si.mazi.rescu.ParamsDigest;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UpbitAuthenticated extends Upbit {

  @GET
  @Path("accounts")
  UpbitBalances getWallet(@HeaderParam("Authorization") ParamsDigest signatureCreator)
      throws IOException, UpbitException;

  @POST
  @Path("orders")
  UpbitOrderResponse limitOrder(
      @HeaderParam("Authorization") ParamsDigest signatureCreator,
      UpbitOrderRequest upbitOrderRequest)
      throws IOException, UpbitException;

  @DELETE
  @Path("order")
  UpbitOrderResponse cancelOrder(
      @HeaderParam("Authorization") ParamsDigest signatureCreator, @QueryParam("uuid") String uuid)
      throws IOException, UpbitException;

  @GET
  @Path("order")
  UpbitOrderResponse getOrder(
      @HeaderParam("Authorization") ParamsDigest signatureCreator, @QueryParam("uuid") String uuid)
      throws IOException, UpbitException;
}
