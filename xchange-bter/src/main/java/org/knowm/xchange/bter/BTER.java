package org.knowm.xchange.bter;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bter.dto.marketdata.BTERCurrencyPairs;
import org.knowm.xchange.bter.dto.marketdata.BTERDepth;
import org.knowm.xchange.bter.dto.marketdata.BTERMarketInfoWrapper;
import org.knowm.xchange.bter.dto.marketdata.BTERTicker;
import org.knowm.xchange.bter.dto.marketdata.BTERTickers;
import org.knowm.xchange.bter.dto.marketdata.BTERTradeHistory;

@Path("api/1")
@Produces(MediaType.APPLICATION_JSON)
public interface BTER {

  @GET
  @Path("marketinfo")
  BTERMarketInfoWrapper getMarketInfo() throws IOException;

  @GET
  @Path("pairs")
  BTERCurrencyPairs getPairs() throws IOException;

  @GET
  @Path("tickers")
  BTERTickers getTickers() throws IOException;

  @GET
  @Path("ticker/{ident}_{currency}")
  BTERTicker getTicker(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("depth/{ident}_{currency}")
  BTERDepth getFullDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("trade/{ident}_{currency}")
  BTERTradeHistory getTradeHistory(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("trade/{ident}_{currency}/{tradeId}")
  BTERTradeHistory getTradeHistorySince(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency,
      @PathParam("tradeId") String tradeId) throws IOException;
}
