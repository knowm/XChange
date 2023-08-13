package org.knowm.xchange.mexc;

import org.knowm.xchange.mexc.dto.MEXCResult;
import org.knowm.xchange.mexc.dto.account.MEXCBalance;
import org.knowm.xchange.mexc.dto.trade.MEXCOrder;
import org.knowm.xchange.mexc.dto.trade.MEXCOrderRequestPayload;
import org.knowm.xchange.mexc.service.MEXCException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Path("/open/api/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface MEXCAuthenticated {

  @GET
  @Path("/account/info")
  MEXCResult<Map<String, MEXCBalance>> getWalletBalances(
          @HeaderParam("ApiKey") String apiKey,
          @HeaderParam("Request-Time") SynchronizedValueFactory<Long> timestamp,
          @HeaderParam("Signature") ParamsDigest signature
  ) throws IOException, MEXCException;

  @POST
  @Path("/order/place")
  @Consumes(MediaType.APPLICATION_JSON)
  MEXCResult<String> placeOrder(@HeaderParam("ApiKey") String apiKey,
                                @HeaderParam("Request-Time") SynchronizedValueFactory<Long> timestamp,
                                @HeaderParam("Signature") ParamsDigest signature,
                                MEXCOrderRequestPayload orderRequestPayload) throws IOException, MEXCException;

  @GET
  @Path("/order/query")
  MEXCResult<List<MEXCOrder>> getOrders(
          @HeaderParam("ApiKey") String apiKey,
          @HeaderParam("Request-Time") SynchronizedValueFactory<Long> timestamp,
          @HeaderParam("Signature") ParamsDigest signature,
          @QueryParam("order_ids") List<String> orderIds) throws IOException, MEXCException;

}