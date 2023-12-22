package org.knowm.xchange.bitbay;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayTicker;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayTrade;

/**
 * @author kpysniak
 */
@Path("/Public")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitbay {

  /**
   * @return Bitbay ticker
   * @throws IOException
   */
  @GET
  @Path("{currencyPair}/ticker.json")
  BitbayTicker getBitbayTicker(@PathParam("currencyPair") String currencyPair) throws IOException;

  @GET
  @Path("{currencyPair}/orderbook.json")
  BitbayOrderBook getBitbayOrderBook(@PathParam("currencyPair") String currencyPair)
      throws IOException;

  @GET
  @Path("{currencyPair}/trades.json")
  BitbayTrade[] getBitbayTrades(
      @PathParam("currencyPair") String currencyPair,
      @QueryParam("since") long sinceId,
      @QueryParam("sort") String sort,
      @QueryParam("limit") int limit)
      throws IOException;
}
