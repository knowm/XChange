package org.knowm.xchange.btcturk;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
