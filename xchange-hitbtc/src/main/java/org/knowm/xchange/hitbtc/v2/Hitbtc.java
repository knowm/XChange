package org.knowm.xchange.hitbtc.v2;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.knowm.xchange.hitbtc.v2.dto.HitbtcCandle;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrderBook;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTicker;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTrade;

/**
 * Version 2 of HitBtc API. See https://api.hitbtc.com/api/2/explore/
 */
@Path("/api/2/")
public interface Hitbtc {

  @GET
  @Path("public/symbol")
  List<HitbtcSymbol> getSymbols() throws IOException;

  @GET
  @Path("public/ticker")
  List<HitbtcTicker> getTickers() throws IOException;

  @GET
  @Path("public/ticker/{symbol}")
  HitbtcTicker getTicker(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("public/orderbook/{symbol}")
  HitbtcOrderBook getOrderBook(@PathParam("symbol") String symbol, @QueryParam("limit") Integer limit) throws IOException;

  @GET
  @Path("public/trades/{symbol}")
  List<HitbtcTrade> getTrades(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("public/ticker")
  List<HitbtcTicker> getHitbtcTickers() throws IOException;

  @GET
  @Path("public/candles/{symbol}")
  List<HitbtcCandle> getHitbtcOHLC(@PathParam("symbol") String symbol, @QueryParam("limit") int limit, @QueryParam("period") String period) throws IOException;

}
