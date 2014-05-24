package com.xeiam.xchange.btccentral;

import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralMarketDepth;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralTicker;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralTrade;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * @author kpysniak
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCCentral {


  /**
   *
   * @return BTCCentral ticker
   * @throws IOException
   */
  @GET
  @Path("ticker/")
  public BTCCentralTicker getBTCCentralTicker() throws IOException;

  @GET
  @Path("depth/")
  public BTCCentralMarketDepth getOrderBook() throws IOException;

  @GET
  @Path("trades/")
  public BTCCentralTrade[] getTrades() throws IOException;
}
