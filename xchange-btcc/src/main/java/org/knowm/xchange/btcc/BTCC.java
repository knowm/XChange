package org.knowm.xchange.btcc;

import java.io.IOException;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.btcc.dto.marketdata.BTCCTicker;

@Path("data/pro/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCC {

  @GET
  @Path("ticker?symbol={symbol}")
  Map<String, BTCCTicker> getMarketTicker(@PathParam("symbol") String symbol) throws IOException;
}
