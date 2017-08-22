package org.knowm.xchange.bittrex;

import org.knowm.xchange.bittrex.dto.marketdata.BittrexChartData;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("v2.0")
@Produces(MediaType.APPLICATION_JSON)
public interface BittrexV2 {

    @GET
    @Path("pub/market/getTicks")
    BittrexChartData[] getChartData(@QueryParam("marketName") String market, @QueryParam("tickInterval") String tickInterval);

}
