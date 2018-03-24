package org.knowm.xchange.cexio;

import java.io.IOException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.cexio.dto.marketdata.CexIOCurrencyLimits;
import org.knowm.xchange.cexio.dto.marketdata.CexIODepth;
import org.knowm.xchange.cexio.dto.marketdata.CexIOTicker;
import org.knowm.xchange.cexio.dto.marketdata.CexIOTrade;

/**
 * @author brox
 */
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface CexIO {

  @GET
  @Path("ticker/{ident}/{currency}")
  CexIOTicker getTicker(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("order_book/{ident}/{currency}")
  CexIODepth getDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("trade_history/{ident}/{currency}/")
  CexIOTrade[] getTrades(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @POST
  @Path("trade_history/{ident}/{currency}/")
  CexIOTrade[] getTradesSince(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency,
      @DefaultValue("1") @FormParam("since") long since) throws IOException;

  @GET
  @Path("currency_limits")
  CexIOCurrencyLimits getCurrencyLimits() throws IOException;

}
