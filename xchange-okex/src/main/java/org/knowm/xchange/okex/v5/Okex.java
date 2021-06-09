package org.knowm.xchange.okex.v5;

import org.knowm.xchange.okex.v5.dto.OkexException;
import org.knowm.xchange.okex.v5.dto.OkexResponse;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexInstrument;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import java.io.IOException;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/v5")
@Produces(APPLICATION_JSON)
public interface Okex {
    @GET
    @Path("/public/instruments")
    OkexResponse<List<OkexInstrument>> getInstruments(
            @QueryParam("instType") String instrumentType,
            @QueryParam("uly") String underlying,
            @QueryParam("instId") String instrumentId
    ) throws OkexException, IOException;

}
