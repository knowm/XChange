package org.knowm.xchange.bitcoinium;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumTickerHistory;

/**
 * @author veken0m
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitcoinium {

  @GET
  @Path("ticker")
  BitcoiniumTicker getTicker(@QueryParam("pair") String pair, @HeaderParam("X-BITCOINIUM-API-KEY") String apikey) throws IOException;

  @GET
  @Path("orderbook")
  BitcoiniumOrderbook getDepth(@QueryParam("pair") String pair, @QueryParam("orderbookwindow") String orderbookwindow,
      @HeaderParam("X-BITCOINIUM-API-KEY") String apikey) throws IOException;

  @GET
  @Path("tickerhistory")
  BitcoiniumTickerHistory getTickerHistory(@QueryParam("pair") String pair, @QueryParam("historytimewindow") String historytimewindow,
      @HeaderParam("X-BITCOINIUM-API-KEY") String apikey) throws IOException;

}
