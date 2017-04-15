package org.knowm.xchange.empoex;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.empoex.dto.account.EmpoExBalance;
import org.knowm.xchange.empoex.dto.trade.EmpoExOpenOrder;
import org.knowm.xchange.empoex.dto.trade.EmpoExOrderResponse;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface EmpoExAuthenticated extends EmpoEx {

  @GET
  @Path("account/balance/{apiKey}/")
  Map<String, List<EmpoExBalance>> getEmpoExBalances(@PathParam("apiKey") String apiKey) throws IOException, EmpoExErrorException;

  @GET
  @Path("trade/openorders/{apiKey}/")
  Map<String, List<EmpoExOpenOrder>> getEmpoExOpenOrders(@PathParam("apiKey") String apiKey) throws IOException, EmpoExErrorException;

  @GET
  @Path("trade/cancel/{apiKey}/{orderId}/")
  Map<String, Boolean> cancelEmpoExOrder(@PathParam("apiKey") String apiKey,
      @PathParam("orderId") String orderId) throws IOException, EmpoExException;

  @GET
  @Path("trade/buy/{apiKey}/{pairString}/{amount}/{price}/")
  EmpoExOrderResponse buy(@PathParam("apiKey") String apiKey, @PathParam("pairString") String pairString, @PathParam("amount") String amount,
      @PathParam("price") String price) throws IOException, EmpoExException;

  @GET
  @Path("trade/sell/{apiKey}/{pairString}/{amount}/{price}/")
  EmpoExOrderResponse sell(@PathParam("apiKey") String apiKey, @PathParam("pairString") String pairString, @PathParam("amount") String amount,
      @PathParam("price") String price) throws IOException, EmpoExException;

}
