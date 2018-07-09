package org.knowm.xchange.upbit;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.knowm.xchange.upbit.dto.UpbitException;
import org.knowm.xchange.upbit.dto.marketdata.UpbitOrderBooks;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTickers;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTrades;

@Path("v1")
public interface Upbit {

  @GET
  @Path("ticker")
  UpbitTickers getTicker(@QueryParam("markets") String markets) throws IOException, UpbitException;

  @GET
  @Path("orderbook")
  UpbitOrderBooks getOrderBook(@QueryParam("markets") String markets)
      throws IOException, UpbitException;

  @GET
  @Path("trades/ticks")
  UpbitTrades getTrades(@QueryParam("market") String market, @QueryParam("count") int count)
      throws IOException, UpbitException;
}
