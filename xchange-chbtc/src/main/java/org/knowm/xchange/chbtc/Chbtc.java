package org.knowm.xchange.chbtc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.chbtc.dto.marketdata.ChbtcOrderBook;
import org.knowm.xchange.chbtc.dto.marketdata.ChbtcTickerResponse;
import org.knowm.xchange.chbtc.dto.marketdata.ChbtcTrade;

import si.mazi.rescu.HttpStatusIOException;

@Path("data")
@Produces(MediaType.APPLICATION_JSON)
public interface Chbtc {

  @GET
  @Path("depth")
  ChbtcOrderBook getOrderBookBtc() throws HttpStatusIOException;

  @GET
  @Path("ticker")
  ChbtcTickerResponse getTickerBtc() throws HttpStatusIOException;

  @GET
  @Path("getTrades")
  ChbtcTrade[] getTransactionsBtc(@QueryParam("since") Integer sinceTid) throws HttpStatusIOException;

  @GET
  @Path("{currency}/depth")
  ChbtcOrderBook getOrderBook(@PathParam("currency") String currency) throws HttpStatusIOException;

  @GET
  @Path("{currency}/ticker")
  ChbtcTickerResponse getTicker(@PathParam("currency") String currency) throws HttpStatusIOException;

  @GET
  @Path("{currency}/getTrades")
  ChbtcTrade[] getTransactions(@PathParam("currency") String currency, @QueryParam("since") Integer sinceTid) throws HttpStatusIOException;
}
