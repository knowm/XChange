package org.knowm.xchange.bitbns;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitbns.dto.BitbnsOrderStatusResponse;
import org.knowm.xchange.bitbns.dto.OrderStatusBody;
import org.knowm.xchange.bitbns.dto.PadxNewOrderRequest;
import org.knowm.xchange.bitbns.dto.PdaxCancelData;
import org.knowm.xchange.bitbns.dto.PdaxCancleOrderResponse;
import org.knowm.xchange.bitbns.dto.PdaxOrder;
import org.knowm.xchange.bitbns.dto.PdaxOrderBooks;
import org.knowm.xchange.bitbns.dto.PdaxOrderPlaceStatusResponse;
import org.knowm.xchange.bitbns.dto.PdaxTickerData;

import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface Pdax {

  @GET
  @Path("/v1/order_book/{symbol}")
  PdaxOrderBooks getOrderBook(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("/v1/ticker/{symbol}")
  PdaxTickerData getTicker(@PathParam("symbol") String symbol);

  @POST
  @Path("/v1/order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  PdaxOrderPlaceStatusResponse newOrder(
      @HeaderParam("Access-Key") String apikey,
      @HeaderParam("Access-Signature") ParamsDigest payload,
      PadxNewOrderRequest newOrderRequest);
  
  @POST
  @Path("/v1/order/{orderId}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  PdaxOrder getOrderByOrderId(
      @HeaderParam("Access-Key") String apikey,
      @HeaderParam("Access-Signature") ParamsDigest payload,
      @PathParam("orderId") String orderId);

  
  @POST
  @Path("/v1/order/{orderId}/cancel")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  PdaxCancleOrderResponse cancelOrderByOrderId(
      @HeaderParam("Access-Key") String apikey,
      @HeaderParam("Access-Signature") ParamsDigest payload,
      @PathParam("orderId") String orderId);

}
