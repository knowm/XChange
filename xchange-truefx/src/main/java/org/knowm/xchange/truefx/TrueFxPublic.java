package org.knowm.xchange.truefx;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.truefx.dto.marketdata.TrueFxTicker;

@Path("")
public interface TrueFxPublic {
  @GET
  @Path("rates/connect.html?f=csv&c={pair}")
  TrueFxTicker getTicker(@PathParam("pair") CurrencyPair pair) throws IOException;
}
