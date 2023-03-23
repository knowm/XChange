package org.knowm.xchange.btcturk;

import java.io.IOException;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOHLC;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOrderBook;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTicker;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTrades;

/**
 * @author semihunaldi
 * @author mertguner
 */
@Path("api/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCTurk {

  @GET
  @Path("ticker/")
  BTCTurkTicker getTicker(@QueryParam("pairSymbol") String pairSymbol) throws IOException;

  @GET
  @Path("ticker/")
  List<BTCTurkTicker> getTicker() throws IOException;

  @GET
  @Path("orderbook/")
  BTCTurkOrderBook getOrderBook(@QueryParam("pairSymbol") String pairSymbol) throws IOException;

  @GET
  @Path("trades/")
  List<BTCTurkTrades> getTrades(
      @QueryParam("pairSymbol") String pairSymbol, @QueryParam("last") Integer last)
      throws IOException;

  @GET
  @Path("ohlcdata/")
  List<BTCTurkOHLC> getOHLC(@QueryParam("pairSymbol") String pairSymbol) throws IOException;

  @GET
  @Path("ohlcdata/")
  List<BTCTurkOHLC> getOHLC(
      @QueryParam("pairSymbol") String pairSymbol, @QueryParam("last") Integer last)
      throws IOException;
}
