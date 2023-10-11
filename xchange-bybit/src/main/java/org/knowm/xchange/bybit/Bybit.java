package org.knowm.xchange.bybit;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentsInfo;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTickers;
import org.knowm.xchange.bybit.service.BybitException;

@Path("/v5/market")
@Produces(MediaType.APPLICATION_JSON)
public interface Bybit {

  /** @apiSpec <a href="https://bybit-exchange.github.io/docs/v5/market/tickers">API</a> */
  @GET
  @Path("/tickers")
  BybitResult<BybitTickers<BybitTicker>> getTicker24h(
      @QueryParam("category") String category, @QueryParam("symbol") String symbol)
      throws IOException, BybitException;

  /** @apiSpec <a href="https://bybit-exchange.github.io/docs/v5/market/instrument">API</a> */
  @GET
  @Path("/instruments-info")
  BybitResult<BybitInstrumentsInfo<BybitInstrumentInfo>> getInstrumentsInfo(
      @QueryParam("category") String category,
      @QueryParam("symbol") String symbol,
      @QueryParam("status") String status,
      @QueryParam("baseCoin") String baseCoin,
      @QueryParam("limit") Integer limit,
      @QueryParam("cursor") String cursor

  ) throws IOException, BybitException;

  @GET
  @Path("/asset/transfer/query-asset-info")
  BybitResult<String> getAssetsInfo(
      @QueryParam("accountType") String accountType,
      @QueryParam("coin") String coin
  ) throws IOException, BybitException;
}
