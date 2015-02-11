package com.xeiam.xchange.bitbay;

import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bitbay.dto.marketdata.BitbayMarketAll;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTicker;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTrade;

/**
 * @author kpysniak
 */
@Path("/API/Public/")
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
  public BitbayTrade[] getBitbayTrades(@PathParam("currencyPair") String currencyPair, @QueryParam("since") Long since) throws IOException;

  @GET
  @Path("{currencyPair}/all.json")
  public BitbayMarketAll getAll(@PathParam("currencyPair") String currencyPair) throws IOException;
}
