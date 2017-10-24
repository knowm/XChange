package org.knowm.xchange.bitcoincharts;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitcoincharts.dto.marketdata.BitcoinChartsTicker;

/**
 * @author Matija Mazi
 */
@Path("/")
public interface BitcoinCharts {

  @GET
  @Path("v1/markets.json")
  @Produces(MediaType.APPLICATION_JSON)
  BitcoinChartsTicker[] getMarketData() throws IOException;

}
