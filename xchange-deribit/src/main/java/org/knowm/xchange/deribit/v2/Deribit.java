package org.knowm.xchange.deribit.v2;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.deribit.v2.dto.DeribitException;
import org.knowm.xchange.deribit.v2.dto.DeribitResponse;
import org.knowm.xchange.deribit.v2.dto.marketdata.*;

@Path("/api/v2/public")
@Produces(MediaType.APPLICATION_JSON)
public interface Deribit {

  @GET
  @Path("get_instruments")
  DeribitResponse<List<DeribitInstrument>> getInstruments(@QueryParam("currency") String currency)
      throws DeribitException, IOException;

  @GET
  @Path("get_currencies")
  DeribitResponse<List<DeribitCurrency>> getCurrencies() throws DeribitException, IOException;

  @GET
  @Path("get_order_book")
  DeribitResponse<DeribitOrderBook> getOrderBook(
      @QueryParam("instrument_name") String instrumentName) throws DeribitException, IOException;

  @GET
  @Path("get_order_book")
  DeribitResponse<DeribitOrderBook> getOrderBook(
      @QueryParam("instrument_name") String instrumentName, @QueryParam("depth") int depth)
      throws DeribitException, IOException;

  @GET
  @Path("get_last_trades_by_instrument")
  DeribitResponse<DeribitTrades> getLastTrades(@QueryParam("instrument_name") String instrumentName)
      throws DeribitException, IOException;

  @GET
  @Path("get_book_summary_by_instrument")
  DeribitResponse<List<DeribitSummary>> getSummary(
      @QueryParam("instrument_name") String instrumentName) throws DeribitException, IOException;

  @GET
  @Path("ticker")
  DeribitResponse<DeribitTicker> getTicker(@QueryParam("instrument_name") String instrumentName)
      throws DeribitException, IOException;
}
