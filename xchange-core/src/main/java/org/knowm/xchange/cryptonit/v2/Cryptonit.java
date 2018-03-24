package org.knowm.xchange.cryptonit.v2;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import org.knowm.xchange.cryptonit.v2.dto.marketdata.CryptonitTicker;

/**
 * @author veken0m
 */
@Path("apiv2/rest/public")
@Produces(MediaType.APPLICATION_JSON)
public interface Cryptonit {

  @GET
  @Path("pairs.json")
  List<List<String>> getPairs() throws IOException;

  @GET
  @Path("ccorder.json?bid_currency={bid_currency}&ask_currency={ask_currency}&ticker")
  CryptonitTicker getTicker(@PathParam("bid_currency") String bid_currency, @PathParam("ask_currency") String ask_currency) throws IOException;

  @GET
  @Path("ccorder.json?bid_currency={bid_currency}&ask_currency={ask_currency}&type={type}&limit={limit}")
  CryptonitOrders getOrders(@PathParam("bid_currency") String bid_currency, @PathParam("ask_currency") String ask_currency,
      @PathParam("type") String type, @PathParam("limit") String limit) throws IOException;

}
