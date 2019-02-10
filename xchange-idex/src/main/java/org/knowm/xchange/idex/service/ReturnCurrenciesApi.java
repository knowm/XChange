package org.knowm.xchange.idex.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.ReturnCurrenciesResponse;

@Path("/returnCurrencies")
@Api(description = "the returnCurrencies API")
@Consumes("application/json")
@Produces("application/json")
public interface ReturnCurrenciesApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(value = "", notes = "", tags = "market")
  @ApiResponses(
      @ApiResponse(code = 200, message = "null", response = ReturnCurrenciesResponse.class))
  ReturnCurrenciesResponse currencies();
}
