package org.knowm.xchange.coincheck;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coincheck.dto.CoincheckException;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckOrderBook;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckPair;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckTicker;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckTradesContainer;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface Coincheck {
  @GET
  @Path("/ticker")
  CoincheckTicker getTicker(@QueryParam("pair") CoincheckPair pair)
      throws IOException, CoincheckException;

  @GET
  @Path("/order_books")
  CoincheckOrderBook getOrderBook(@QueryParam("pair") CoincheckPair pair)
      throws IOException, CoincheckException;

  @GET
  @Path("/trades")
  CoincheckTradesContainer getTrades(
      @QueryParam("pair") CoincheckPair pair,
      @QueryParam("limit") Integer limit,
      @QueryParam("order") String order,
      @QueryParam("starting_after") Long startingAfter,
      @QueryParam("ending_before") Long endingBefore)
      throws IOException, CoincheckException;
}
