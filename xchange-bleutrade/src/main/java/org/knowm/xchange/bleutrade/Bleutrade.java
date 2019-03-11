package org.knowm.xchange.bleutrade;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeCurrenciesReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarketHistoryReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeOrderBookReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTickerReturn;

@Path("v2")
@Produces(MediaType.APPLICATION_JSON)
public interface Bleutrade {

  @GET
  @Path("public/getcurrencies")
  BleutradeCurrenciesReturn getBleutradeCurrencies() throws IOException;

  @GET
  @Path("public/getmarkets")
  BleutradeMarketsReturn getBleutradeMarkets() throws IOException;

  @GET
  @Path("public/getmarketsummary")
  BleutradeTickerReturn getBleutradeTicker(@QueryParam("market") String market) throws IOException;

  @GET
  @Path("public/getmarketsummaries")
  BleutradeTickerReturn getBleutradeTickers() throws IOException;

  @GET
  @Path("public/getorderbook")
  BleutradeOrderBookReturn getBleutradeOrderBook(
      @QueryParam("market") String market,
      @QueryParam("type") String type,
      @QueryParam("depth") int depth)
      throws IOException;

  @GET
  @Path("public/getmarkethistory")
  BleutradeMarketHistoryReturn getBleutradeMarketHistory(
      @QueryParam("market") String market, @QueryParam("count") int count) throws IOException;
}
