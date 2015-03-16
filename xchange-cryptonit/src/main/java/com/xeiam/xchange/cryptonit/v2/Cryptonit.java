package com.xeiam.xchange.cryptonit.v2;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitTicker;

/**
 * @author veken0m
 */
@Path("apiv2/rest")
@Produces(MediaType.APPLICATION_JSON)
public interface Cryptonit {

  @GET
  @Path("public/pairs.json")
  public List<List<String>> getPairs() throws IOException;

  @GET
  @Path("public/ccorder.json?bid_currency={bid_currency}&ask_currency={ask_currency}&ticker")
  public CryptonitTicker getTicker(@PathParam("bid_currency") String bid_currency, @PathParam("ask_currency") String ask_currency) throws IOException;

  @GET
  @Path("public/ccorder.json?bid_currency={bid_currency}&ask_currency={ask_currency}&type={type}&limit={limit}")
  public CryptonitOrders getOrders(@PathParam("bid_currency") String bid_currency, @PathParam("ask_currency") String ask_currency, @PathParam("type") String type, @PathParam("limit") String limit)
      throws IOException;
  @Path("ccorder.json?bid_currency={bid_currency}&ask_currency={ask_currency}&type={type}&limit={limit}")
  public CryptonitOrders getOrders(@PathParam("bid_currency") String bid_currency, @PathParam("ask_currency") String ask_currency,
      @PathParam("type") String type, @PathParam("limit") String limit) throws IOException;

}
