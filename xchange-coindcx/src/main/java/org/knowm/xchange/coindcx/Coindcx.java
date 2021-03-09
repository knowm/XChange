package org.knowm.xchange.coindcx;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.coindcx.dto.*;
import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Coindcx {

  @GET
  @Path("/market_data/orderbook")
  CoindcxOrderBook getOrderBook(@QueryParam("pair") String pair) throws IOException;

  @GET
  @Path("/market_data/trade_history")
  List<CoindcxTrade> getTrade(@QueryParam("pair") String pair);

  @GET
  @Path("/exchange/ticker")
  List<CoindcxTickersResponse> getTicker();

  @POST
  @Path("/exchange/v1/orders/create")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  CoindcxOrderStatusResponse newOrder(
      @HeaderParam("X-AUTH-APIKEY") String apiKey,
      @HeaderParam("X-AUTH-SIGNATURE") String signature,
      CoindcxNewOrderRequest coindcxNewOrderRequest);

  @POST
  @Path("/exchange/v1/orders/status")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  CoindcxOrderStatusResponse orderStatus(
        @HeaderParam("Content-Type") String contentType,
        @HeaderParam("X-AUTH-APIKEY") String apiKey,
        @HeaderParam("X-AUTH-SIGNATURE") String signature)
        throws IOException, CoindcxException;


  @POST
  @Path("/exchange/v1/orders/trade_history")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  List<CoindcxTradeHistoryResponse> getAccountTradeHistory(
          @HeaderParam("Content-Type") String contentType,
          @HeaderParam("X-AUTH-APIKEY") String apiKey,
          @HeaderParam("X-AUTH-SIGNATURE") String signature)
          throws IOException, CoindcxException;
}
