package org.knowm.xchange.getbtc;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.getbtc.dto.GetbtcException;
import org.knowm.xchange.getbtc.dto.account.GetbtcAccountInformation;
import org.knowm.xchange.getbtc.dto.trade.GetbtcCancelOrder;
import org.knowm.xchange.getbtc.dto.trade.GetbtcOpenOrders;
import org.knowm.xchange.getbtc.dto.trade.GetbtcOrder;
import org.knowm.xchange.getbtc.dto.trade.GetbtcPlaceOrder;

/**
 * kevinobamatheus@gmail.com
 * @author kevingates
 *
 */

@Path("api/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface GetbtcAuthenticated extends Getbtc {

  public static final String API_KEY = "apikey";
  public static final String API_SIGNATURE = "signature";
  
  @POST
  @Path("balances-and-info")
  GetbtcAccountInformation getAccountInfo(Map params);
 
  @GET
  @Path("orders/new")
  GetbtcPlaceOrder placeLimitOrder(Map params);
  
  @GET
  @Path("orders/cancel")
  GetbtcCancelOrder cancelOrder(Map params) throws IOException, GetbtcException;

  @GET
  @Path("open-orders")
  GetbtcOpenOrders getOpenOrders(Map params) throws IOException, GetbtcException;

}
