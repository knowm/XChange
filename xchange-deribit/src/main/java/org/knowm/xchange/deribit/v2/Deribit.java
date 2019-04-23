package org.knowm.xchange.deribit.v2;

import org.knowm.xchange.deribit.v2.dto.marketdata.response.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/api/v2/public")
@Produces(MediaType.APPLICATION_JSON)
public interface Deribit {

  @GET
  @Path("get_instruments")
  DeribitInstrumentsResponse getInstruments(
          @QueryParam("currency") String currency
  ) throws IOException;

  @GET
  @Path("get_currencies")
  DeribitCurrenciesResponse getCurrencies() throws IOException;

  @GET
  @Path("get_order_book")
  DeribitOrderbookResponse getOrderbook(
          @QueryParam("instrument_name") String instrumentName
  ) throws IOException;

  @GET
  @Path("get_order_book")
  DeribitOrderbookResponse getOrderbook(
          @QueryParam("instrument_name") String instrumentName,
          @QueryParam("depth") int depth
  ) throws IOException;

  @GET
  @Path("getlasttrades")
  DeribitTradesResponse getLastTrades(
          @QueryParam("instrument") String instrument
  ) throws IOException;

  @GET
  @Path("getsummary")
  DeribitSummaryResponse getSummary(
          @QueryParam("instrument") String instrument
  ) throws IOException;

  @GET
  @Path("getsummary")
  DeribitSummariesResponse getSummaries(
          @QueryParam("instrument") String instrument
  ) throws IOException;

  @GET
  @Path("getsummary")
  DeribitSummariesResponse getSummaries(
          @QueryParam("instrument") String instrument,
          @QueryParam("currency") String currency
  ) throws IOException;

}
