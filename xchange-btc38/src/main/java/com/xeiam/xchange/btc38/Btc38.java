package com.xeiam.xchange.btc38;

import com.xeiam.xchange.btc38.dto.marketdata.Btc38TickerReturn;

import javax.ws.rs.*;
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
   * Gets all tickers from Btc38 for a particular target currency
   * URL: http://api.btc38.com/v1/ticker.php?c=all&mk_type=cny
   * 
   * @param marketType Target currency
   * @return Map of tickers
   * @throws IOException
   */
  @GET
  @Path("ticker.php?c=all&mk_type={marketType}")
  public Map<String, Btc38TickerReturn> getMarketTicker(@PathParam("marketType") String marketType) throws IOException;
}
