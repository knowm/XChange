package org.knowm.xchange.bybit;

import java.io.IOException;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.marketdata.BybitSymbol;
import org.knowm.xchange.bybit.dto.marketdata.BybitTicker;
import org.knowm.xchange.bybit.service.BybitException;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Bybit {

  /**
   * @apiSpec <a href="https://bybit-exchange.github.io/docs/inverse/#t-latestsymbolinfo">API</a>
   */
  @GET
  @Path("/v2/public/tickers")
  BybitResult<List<BybitTicker>> getTicker24h(@QueryParam("symbol") String symbol) throws IOException, BybitException;

  /**
   * @apiSpec <a href="https://bybit-exchange.github.io/docs/inverse/#t-querysymbol">API</a>
   */
  @GET
  @Path("/v2/public/symbols")
  BybitResult<List<BybitSymbol>> getSymbols() throws IOException, BybitException;

}