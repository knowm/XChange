package com.xeiam.xchange.bitcoinium;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTickerHistory;

/**
 * @author veken0m
 */
@Path("service")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitcoinium {

  @GET
  @Path("tickerupdate")
  public BitcoiniumTicker getTicker(@QueryParam("pair") String pair, @QueryParam("apikey") String apikey);

  @GET
  @Path("orderbook")
  public BitcoiniumOrderbook getDepth(@QueryParam("pair") String pair, @QueryParam("pricewindow") String pricewindow, @QueryParam("apikey") String apikey);

  @GET
  @Path("tickerhistory")
  public BitcoiniumTickerHistory getTickerHistory(@QueryParam("pair") String pair, @QueryParam("timewindow") String timewindow, @QueryParam("apikey") String apikey);

}
