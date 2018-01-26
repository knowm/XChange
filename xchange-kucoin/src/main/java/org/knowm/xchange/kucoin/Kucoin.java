package org.knowm.xchange.kucoin;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinResponse;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTicker;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Kucoin {

  public static String toSymbol(CurrencyPair pair) {
    return pair.base.getCurrencyCode() + "-" + pair.counter.getCurrencyCode();
  }

  @GET
  @Path("/{symbol}/open/tick")
  KucoinResponse<KucoinTicker> getTicker(@PathParam("symbol") String symbol) throws KucoinException, IOException;

  @GET
  @Path("/market/open/symbols")
  KucoinResponse<List<KucoinTicker>> getTickers() throws KucoinException, IOException;
}
