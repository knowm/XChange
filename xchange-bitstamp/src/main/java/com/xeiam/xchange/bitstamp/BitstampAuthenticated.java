package com.xeiam.xchange.bitstamp;

import java.math.BigDecimal;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.xeiam.xchange.bitstamp.dto.trade.BitstampOrder;

import si.mazi.rescu.ParamsDigest;

/**
 * @author Benedikt Bünz See https://www.bitstamp.net/api/ for up-to-date docs.
 */
@Path("api")
@Produces("application/json")
public interface BitstampAuthenticated {

  @POST
  @Path("open_orders/")
  @Produces("application/json")
  public BitstampOrder[] getOpenOrders(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce);

  @POST
  @Path("buy/")
  @Produces("application/json")
  public BitstampOrder buy(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price);

}
