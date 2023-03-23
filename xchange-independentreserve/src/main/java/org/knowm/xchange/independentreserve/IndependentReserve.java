package org.knowm.xchange.independentreserve;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
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
