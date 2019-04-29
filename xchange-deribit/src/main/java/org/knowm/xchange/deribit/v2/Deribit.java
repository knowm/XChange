package org.knowm.xchange.deribit.v2;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.deribit.v2.dto.marketdata.response.*;

@Path("/api/v2/public")
@Produces(MediaType.APPLICATION_JSON)
public interface Deribit {

  @GET
  @Path("get_instruments")
  DeribitInstrumentsResponse getInstruments(@QueryParam("currency") String currency)
      throws IOException;

  @GET
  @Path("get_currencies")
  DeribitCurrenciesResponse getCurrencies() throws IOException;

  @GET
  @Path("get_order_book")
  DeribitOrderBookResponse getOrderBook(@QueryParam("instrument_name") String instrumentName)
      throws IOException;

  @GET
  @Path("get_order_book")
  DeribitOrderBookResponse getOrderBook(
      @QueryParam("instrument_name") String instrumentName, @QueryParam("depth") int depth)
      throws IOException;

  @GET
  @Path("get_last_trades_by_instrument")
  DeribitTradesResponse getLastTrades(@QueryParam("instrument_name") String instrumentName)
      throws IOException;

  @GET
  @Path("get_book_summary_by_instrument")
  DeribitSummaryResponse getSummary(@QueryParam("instrument_name") String instrumentName)
      throws IOException;

  @GET
  @Path("ticker")
  DeribitTickerResponse getTicker(@QueryParam("instrument_name") String instrumentName)
      throws IOException;
}
