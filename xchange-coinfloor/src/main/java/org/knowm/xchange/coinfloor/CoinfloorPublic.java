package org.knowm.xchange.coinfloor;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.coinfloor.dto.CoinfloorException;
import org.knowm.xchange.coinfloor.dto.markedata.CoinfloorOrderBook;
import org.knowm.xchange.coinfloor.dto.markedata.CoinfloorTicker;
import org.knowm.xchange.coinfloor.dto.markedata.CoinfloorTransaction;
import org.knowm.xchange.coinfloor.service.CoinfloorMarketDataServiceRaw;
import org.knowm.xchange.currency.Currency;

@Path("bist")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinfloorPublic {
  @GET
  @Path("{base}/{counter}/ticker/")
  CoinfloorTicker getTicker(
      @PathParam("base") Currency base, @PathParam("counter") Currency counter)
      throws CoinfloorException, IOException;

  @GET
  @Path("{base}/{counter}/order_book/")
  CoinfloorOrderBook getOrderBook(
      @PathParam("base") Currency base, @PathParam("counter") Currency counter)
      throws CoinfloorException, IOException;

  @GET
  @Path("{base}/{counter}/transactions/")
  CoinfloorTransaction[] getTransactions(
      @PathParam("base") Currency base,
      @PathParam("counter") Currency counter,
      @QueryParam("time") CoinfloorMarketDataServiceRaw.CoinfloorInterval period)
      throws CoinfloorException, IOException;
}