package org.knowm.xchange.kucoin;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinOrderBook;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTicker;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Kucoin {

  @GET
  @Path("open/tick")
  KucoinResponse<KucoinTicker> tick(@QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("open/orders")
  KucoinResponse<KucoinOrderBook> orders(
      @QueryParam("symbol") String symbol,
      @QueryParam("group") Integer group,
      @QueryParam("limit") Integer limit) throws IOException;

}
