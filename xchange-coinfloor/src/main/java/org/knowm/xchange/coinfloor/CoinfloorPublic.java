package org.knowm.xchange.coinfloor;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
