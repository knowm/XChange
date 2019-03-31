package org.knowm.xchange.deribit.v1;

import org.knowm.xchange.deribit.v1.dto.marketdata.response.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/api/v1/public")
@Produces(MediaType.APPLICATION_JSON)
public interface Deribit {

  @GET
  @Path("getinstruments")
  DeribitInstrumentsResponse getInstruments() throws IOException;

  @GET
  @Path("getcurrencies")
  DeribitCurrenciesResponse getCurrencies() throws IOException;

  @GET
  @Path("getorderbook")
  DeribitOrderbookResponse getOrderbook(
          @QueryParam("instrument") String instrument
  ) throws IOException;

  @GET
  @Path("getorderbook")
  DeribitOrderbookResponse getOrderbook(
          @QueryParam("instrument") String instrument,
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
