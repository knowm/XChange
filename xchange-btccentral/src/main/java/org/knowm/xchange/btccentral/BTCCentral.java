package org.knowm.xchange.btccentral;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.btccentral.dto.marketdata.BTCCentralMarketDepth;
import org.knowm.xchange.btccentral.dto.marketdata.BTCCentralTicker;
import org.knowm.xchange.btccentral.dto.marketdata.BTCCentralTrade;

/**
 * @author kpysniak
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCCentral {

  /**
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
