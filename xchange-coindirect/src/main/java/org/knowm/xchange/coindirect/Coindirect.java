package org.knowm.xchange.coindirect;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coindirect.dto.CoindirectException;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectMarket;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectOrderbook;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectTicker;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectTrades;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Coindirect {
  @GET
  @Path("api/v1/exchange/market/book")
  CoindirectOrderbook getExchangeOrderBook(@QueryParam("symbol") String symbol)
      throws IOException, CoindirectException;

  @GET
  @Path("api/v1/exchange/historical/trades/{market}/{history}")
  CoindirectTrades getHistoricalExchangeTrades(
      @PathParam("market") String market, @PathParam("history") String history)
      throws IOException, CoindirectException;

  @GET
  @Path("api/v1/exchange/historical/{market}/{history}/{grouping}")
  CoindirectTicker getHistoricalExchangeData(
      @PathParam("market") String market,
      @PathParam("history") String history,
      @PathParam("grouping") String grouping)
      throws IOException, CoindirectException;

  @GET
  @Path("api/v1/exchange/market")
  List<CoindirectMarket> listExchangeMarkets(@QueryParam("max") long max)
      throws IOException, CoindirectException;
}
