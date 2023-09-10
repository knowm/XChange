package org.knowm.xchange.bybit;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.knowm.xchange.bybit.dto.BybitCategory;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfos;
import org.knowm.xchange.bybit.dto.marketdata.ticker.BybitTicker;
import org.knowm.xchange.bybit.dto.marketdata.ticker.BybitTickers;
import org.knowm.xchange.bybit.service.BybitException;

@Path("/v5/market")
@Produces(MediaType.APPLICATION_JSON)
public interface Bybit {

  /** @apiSpec <a href="https://bybit-exchange.github.io/docs/v5/market/tickers">API</a> */
  @GET
  @Path("/tickers")
  BybitResult<BybitTickers<BybitTicker>> getTicker24h(
      @QueryParam("category") BybitCategory category, @QueryParam("symbol") String symbol)
      throws IOException, BybitException;

  /** @apiSpec <a href="https://bybit-exchange.github.io/docs/v5/market/instrument">API</a> */
  @GET
  @Path("/instruments-info")
  BybitResult<BybitInstrumentInfos<BybitInstrumentInfo>> getInstrumentsInfo(
      @QueryParam("category") BybitCategory category) throws IOException, BybitException;
}
