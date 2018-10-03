package org.knowm.xchange.idex.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.ReturnContractAddressResponse;

@Path("/returnContractAddress")
@Api(description = "the returnContractAddress API")
@Consumes("application/json")
@Produces("application/json")
public interface ReturnContractAddressApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(
      value = "Returns the contract address used for depositing, withdrawing, and posting orders",
      notes = "",
      tags = "trade")
  @ApiResponses(
      @ApiResponse(code = 200, message = "", response = ReturnContractAddressResponse.class))
  ReturnContractAddressResponse contractAddress();
}
