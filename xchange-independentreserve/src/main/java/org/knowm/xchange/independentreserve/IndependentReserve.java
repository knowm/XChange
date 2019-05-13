package org.knowm.xchange.independentreserve;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.independentreserve.dto.marketdata.IndependentReserveOrderBook;
import org.knowm.xchange.independentreserve.dto.marketdata.IndependentReserveTicker;

/** Author: Kamil Zbikowski Date: 4/9/15 */
@Path("Public")
@Produces(MediaType.APPLICATION_JSON)
public interface IndependentReserve {

  @GET
  @Path("/GetAllOrders")
  IndependentReserveOrderBook getOrderBook(
      @QueryParam("primaryCurrencyCode") String primaryCurrencyCode,
      @QueryParam("secondaryCurrencyCode") String secondaryCurrencyCode)
      throws IOException;

  @GET
  @Path("/GetMarketSummary")
  IndependentReserveTicker getMarketSummary(
      @QueryParam("primaryCurrencyCode") String primaryCurrencyCode,
      @QueryParam("secondaryCurrencyCode") String secondaryCurrencyCode)
      throws IOException;
}
