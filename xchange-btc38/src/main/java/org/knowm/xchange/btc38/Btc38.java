package org.knowm.xchange.btc38;

import org.knowm.xchange.btc38.dto.marketdata.Btc38TickerReturn;
import org.knowm.xchange.btc38.dto.marketdata.Btc38Trade;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Yingzhe on 12/17/2014.
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Btc38 {

  /**
   * Gets all tickers from Btc38 for a particular target currency URL: http://api.btc38.com/v1/ticker.php?c=all&mk_type=cny
   *
   * @param marketType Target currency
   * @return Map of tickers
   * @throws IOException
   */
  @GET
  @Path("ticker.php?c=all&mk_type={marketType}")
  Map<String, Btc38TickerReturn> getMarketTicker(@PathParam("marketType") String marketType) throws IOException;


  @GET
  @Path("trades.php?c={c}&mk_type={marketType}")
  Btc38Trade[] getTrades(@PathParam("c") String base, @PathParam("marketType") String quote) throws IOException;

  @GET
  @Path("trades.php?c={c}&mk_type={marketType}&tid={from}")
  Btc38Trade[] getTradesFrom(@PathParam("c") String base, @PathParam("marketType") String quote, @PathParam("from") Long from) throws IOException;
}
