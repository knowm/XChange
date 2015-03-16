package com.xeiam.xchange.hitbtc;

import java.io.IOException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTrades;

/**
 * @author kpysniak
 */
@Path("/api/1/")
@Produces(MediaType.APPLICATION_JSON)
public interface Hitbtc {

  /**
   * @return BTCCentral ticker
   * @throws IOException
   */
  @GET
  @Path("public/{currencyPair}/ticker")
  public HitbtcTicker getHitbtcTicker(@PathParam("currencyPair") String currencyPair) throws IOException;

  @GET
  @Path("public/{currencyPair}/orderbook")
  public HitbtcOrderBook getOrderBook(@PathParam("currencyPair") String currencyPair) throws IOException;

  @GET
  @Path("public/{currencyPair}/trades")
  public HitbtcTrades getTrades(@PathParam("currencyPair") String currencyPair, @QueryParam("from") String from, @QueryParam("by") String sortBy,
      @QueryParam("sort") String sort, @QueryParam("start_index") String startIndex, @QueryParam("max_results") String max_results,
      @DefaultValue("object") @QueryParam("format_item") String format_item) throws IOException;

  @GET
  @Path("public/symbols")
  public HitbtcSymbols getSymbols() throws IOException;
}
