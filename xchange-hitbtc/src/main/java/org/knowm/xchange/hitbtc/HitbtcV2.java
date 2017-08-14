package org.knowm.xchange.hitbtc;

import org.knowm.xchange.hitbtc.dto.HitbtcException;
import org.knowm.xchange.hitbtc.dto.marketdata.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Map;

@Path("/api/2/")
@Produces(MediaType.APPLICATION_JSON)
public interface HitbtcV2 {

  @GET
  @Path("public/symbol")
  HitbtcSymbols getSymbols() throws IOException, HitbtcException;

  @GET
  @Path("public/ticker/{symbol}")
  HitbtcTicker getHitbtcTicker(@PathParam("symbol") String symbol) throws IOException, HitbtcException;

  @GET
  @Path("public/ticker")
  Map<String, HitbtcTicker> getHitbtcTickers() throws IOException, HitbtcException;

  @GET
  @Path("public/orderbook/{symbol}")
  HitbtcOrderBook getOrderBook(@PathParam("symbol") String symbol) throws IOException, HitbtcException;

//  /**
//   *
//   * @param symbol
//   * @param from If filter by timestamp, then datetime in iso format or timestamp in millisecond otherwise trade id
//   * @param till If filter by timestamp, then datetime in iso format or timestamp in millisecond otherwise trade id
//   * @param sortBy Filter field
//   * @param sort Sort direction
//   * @param offset
//   * @param limit
//   * @return
//   * @throws IOException
//   * @throws HitbtcException
//   */
//  @GET
//  @Path("public/trades/{symbol}")
//  HitbtcTrades getTrades(@PathParam("symbol") String symbol, @QueryParam("from") String from, @QueryParam("till") String till, @QueryParam("by") String sortBy,
//                         @QueryParam("sort") String sort, @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) throws IOException, HitbtcException;


  //TODO update with query params
  HitbtcTrades getTrades(@PathParam("symbol") String symbol) throws IOException, HitbtcException;

}
