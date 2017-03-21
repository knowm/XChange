package org.knowm.xchange.bitbay;

import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
  public BitbayTicker getBitbayTicker(@PathParam("currencyPair") String currencyPair) throws IOException;

  @GET
  @Path("{currencyPair}/orderbook.json")
  public BitbayOrderBook getBitbayOrderBook(@PathParam("currencyPair") String currencyPair) throws IOException;

  @GET
  @Path("{currencyPair}/trades.json")
  public BitbayTrade[] getBitbayTrades(@PathParam("currencyPair") String currencyPair, @QueryParam("since") long sinceId) throws IOException;
}
