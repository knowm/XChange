package org.knowm.xchange.idex.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.Volume24Response;

@Path("/return24Volume")
@Api(description = "the return24Volume API")
@Consumes("application/json")
@Produces("application/json")
public interface Return24VolumeApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(
      value = "Returns the 24-hour volume for all markets, plus totals for primary currencies. ",
      notes = "",
      tags = "market")
  @ApiResponses(
      @ApiResponse(
          code = 200,
          message =
              "This function takes no JSON arguments{ ETH_REP: { ETH: '1.3429046745', REP: '105.29046745' },ETH_DVIP: { ETH: '4', DVIP: '4' },totalETH: '5.3429046745' }",
          response = Volume24Response.class))
  Volume24Response volume24();
}
