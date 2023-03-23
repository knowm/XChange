package org.knowm.xchange.bitcoincharts;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.bitcoincharts.dto.marketdata.BitcoinChartsTicker;

/** @author Matija Mazi */
@Path("/")
public interface BitcoinCharts {

  @GET
  @Path("v1/markets.json")
  @Produces(MediaType.APPLICATION_JSON)
  BitcoinChartsTicker[] getMarketData() throws IOException;
}
