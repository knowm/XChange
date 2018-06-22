package org.knowm.xchange.bitfinex.v2;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitfinex.v1.dto.BitfinexException;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTicker;

@Path("v2")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitfinex {

  @GET
  @Path("tickers?symbols={symbols}")
  BitfinexTicker[] getTickers(@PathParam("symbols") String symbols)
      throws IOException, BitfinexException;
}
