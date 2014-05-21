package com.xeiam.xchange;

import com.xeiam.xchange.dto.marketdata.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * @author kpysniak
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Hitbtc {

  /**
   *
   * @return BTCCentral ticker
   * @throws IOException
   */
  @GET
  @Path("{currencyPair}/ticker")
  public HitbtcTicker getHitbtcTicker(@PathParam("currencyPair") String currencyPair) throws IOException;

  @GET
  @Path("{currencyPair}/orderbook")
  public HitbtcOrderBook getOrderBook(@PathParam("currencyPair") String currencyPair) throws IOException;

  @GET
  @Path("{currencyPair}/trades")
  public HitbtcTrades getTrades(@PathParam("currencyPair") String currencyPair,
                                @QueryParam("from") String from,
                                @QueryParam("by") String sortBy,
                                @QueryParam("start_index") String startIndex,
                                @QueryParam("max_results") String max_results,
                                @DefaultValue("object") @QueryParam("format_item") String format_item) throws IOException;

  @GET
  @Path("symbols")
  public HitbtcSymbols getSymbols() throws IOException;
}
