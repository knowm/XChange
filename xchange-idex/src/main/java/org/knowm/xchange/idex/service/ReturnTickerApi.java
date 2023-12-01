package org.knowm.xchange.idex.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.Market;
import org.knowm.xchange.idex.dto.ReturnTickerResponse;

@Path("/returnTicker")
@Api(description = "the returnTicker API")
@Consumes("application/json")
@Produces("application/json")
public interface ReturnTickerApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(value = "", notes = "", tags = "market")
  @ApiResponses(@ApiResponse(code = 200, message = "", response = ReturnTickerResponse.class))
  ReturnTickerResponse ticker(Market market);
}
