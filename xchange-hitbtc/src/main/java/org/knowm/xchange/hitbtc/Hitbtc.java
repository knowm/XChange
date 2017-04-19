package org.knowm.xchange.hitbtc;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.hitbtc.dto.HitbtcException;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTime;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTrades;

/**
 * @author kpysniak
 */
@Path("/api/1/")
@Produces(MediaType.APPLICATION_JSON)
public interface Hitbtc {

  @GET
  @Path("public/time")
  HitbtcTime getHitbtcTime() throws IOException, HitbtcException;

  @GET
  @Path("public/symbols")
  HitbtcSymbols getSymbols() throws IOException, HitbtcException;

  /**
   * @return BTCCentral ticker
   * @throws IOException
   */
  @GET
  @Path("public/{currencyPair}/ticker")
  HitbtcTicker getHitbtcTicker(@PathParam("currencyPair") String currencyPair) throws IOException, HitbtcException;

  @GET
  @Path("public/ticker")
  Map<String, HitbtcTicker> getHitbtcTickers() throws IOException, HitbtcException;

  @GET
  @Path("public/{currencyPair}/orderbook")
  HitbtcOrderBook getOrderBook(@PathParam("currencyPair") String currencyPair) throws IOException, HitbtcException;

  @GET
  @Path("public/{currencyPair}/trades")
  HitbtcTrades getTrades(@PathParam("currencyPair") String currencyPair, @QueryParam("from") String from, @QueryParam("by") String sortBy,
      @QueryParam("sort") String sort, @QueryParam("start_index") String startIndex,
      @DefaultValue("1000") @QueryParam("max_results") String max_results, @DefaultValue("object") @QueryParam("format_item") String format_item,
      @DefaultValue("true") @QueryParam("side") String side) throws IOException, HitbtcException;

  @GET
  @Path("public/{currencyPair}/trades/recent")
  HitbtcTrades getTradesRecent(@PathParam("currencyPair") String currencyPair, @DefaultValue("1000") @QueryParam("max_results") String max_results,
      @DefaultValue("object") @QueryParam("format_item") String format_item,
      @DefaultValue("true") @QueryParam("side") String side) throws IOException, HitbtcException;
}
