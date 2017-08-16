package org.knowm.xchange.hitbtc;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.hitbtc.dto.HitbtcException;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcSymbol;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTrade;

@Path("/api/2/")
@Produces(MediaType.APPLICATION_JSON)
public interface Hitbtc {

  @GET
  @Path("public/symbol")
  List<HitbtcSymbol> getSymbols() throws IOException, HitbtcException;

  @GET
  @Path("public/ticker/{symbol}")
  HitbtcTicker getHitbtcTicker(@PathParam("symbol") String symbol) throws IOException, HitbtcException;

  @GET
  @Path("public/ticker")
  List<HitbtcTicker> getHitbtcTickers() throws IOException, HitbtcException;

  @GET
  @Path("public/orderbook/{symbol}")
  HitbtcOrderBook getOrderBook(@PathParam("symbol") String symbol) throws IOException, HitbtcException;

  //TODO update with query params
  @GET
  @Path("public/trades/{symbol}")
  List<HitbtcTrade> getTrades(@PathParam("symbol") String symbol, @QueryParam("limit") Integer limit) throws IOException, HitbtcException;

}

