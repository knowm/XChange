package org.knowm.xchange.truefx;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.truefx.dto.marketdata.TrueFxTicker;

@Path("")
public interface TrueFxPublic {
  @GET
  @Path("rates/connect.html?f=csv&c={pair}")
  TrueFxTicker getTicker(@PathParam("pair") CurrencyPair pair) throws IOException;
}
