package com.xeiam.xchange.lakebtc;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTickers;

/**
 * @author kpysniak
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface LakeBTC {

  /**
   * @return LakeBTC ticker
   * @throws IOException
   */
  @GET
  @Path("ticker")
  public LakeBTCTickers getLakeBTCTickers() throws IOException;

  @GET
  @Path("bcorderbook")
  public LakeBTCOrderBook getLakeBTCOrderBookUSD() throws IOException;

  @GET
  @Path("bcorderbook_cny")
  public LakeBTCOrderBook getLakeBTCOrderBookCNY() throws IOException;
}
