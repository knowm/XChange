package org.knowm.xchange.abucoins;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTicker;

/**
 * @author bryant_harris
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Abucoins {

  @GET
  @Path("products/{product-id}/ticker")
  AbucoinsTicker getTicker(@PathParam("product-id") String product_id) throws IOException;

  /*
  @GET
  @Path("order_book/{ident}/{currency}")
  AbucoinsDepth getDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("trade_history/{ident}/{currency}/")
  AbucoinsTrade[] getTrades(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @POST
  @Path("trade_history/{ident}/{currency}/")
  AbucoinsTrade[] getTradesSince(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency,
      @DefaultValue("1") @FormParam("since") long since) throws IOException;
      */

}
