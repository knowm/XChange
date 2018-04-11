package org.knowm.xchange.bitmarket;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTrade;

/** @author kpysniak */
@Path("json")
@Produces(MediaType.APPLICATION_JSON)
public interface BitMarket {

  /**
   * @return BTCCentral ticker
   * @throws IOException
   */
  @GET
  @Path("{currencyPair}/ticker.json")
  BitMarketTicker getTicker(@PathParam("currencyPair") String currency) throws IOException;

  @GET
  @Path("{currencyPair}/orderbook.json")
  BitMarketOrderBook getOrderBook(@PathParam("currencyPair") String currency) throws IOException;

  @GET
  @Path("{currencyPair}/trades.json")
  BitMarketTrade[] getTrades(@PathParam("currencyPair") String currency) throws IOException;
}
