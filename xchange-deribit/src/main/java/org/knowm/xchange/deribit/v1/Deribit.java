package org.knowm.xchange.deribit.v1;

import org.knowm.xchange.deribit.v1.dto.marketdata.response.DeribitCurrencyResponse;
import org.knowm.xchange.deribit.v1.dto.marketdata.response.DeribitInstrumentResponse;
import org.knowm.xchange.deribit.v1.dto.marketdata.response.DeribitOrderbookResponse;

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
  DeribitInstrumentResponse getInstruments() throws IOException;

  @GET
  @Path("getcurrencies")
  DeribitCurrencyResponse getCurrencies() throws IOException;

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
}
