package com.xeiam.xchange.mtgox.v1;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.xeiam.xchange.mtgox.v0.dto.marketdata.MtGoxDepth;
import com.xeiam.xchange.mtgox.v0.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v0.dto.marketdata.MtGoxTrades;
import com.xeiam.xchange.mtgox.v0.dto.trade.MtGoxCancelOrder;
import com.xeiam.xchange.rest.ParamsDigest;

/**
 * @author timmolter
 */
@Path("api/0")
public interface MtGoxV0 {

  @GET
  @Path("data/getDepth.php?Currency={currency}")
  MtGoxDepth getFullDepth(@PathParam("currency") String currency);

  @GET
  @Path("data/ticker.php?Currency={currency}")
  MtGoxTicker getTicker(@PathParam("currency") String currency);

  @GET
  @Path("data/getTrades.php?Currency={currency}")
  MtGoxTrades[] getTrades(@PathParam("currency") String currency);

  @POST
  @Path("cancelOrder.php")
  MtGoxCancelOrder cancelOrder(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce, @FormParam("oid") String orderId);

}
