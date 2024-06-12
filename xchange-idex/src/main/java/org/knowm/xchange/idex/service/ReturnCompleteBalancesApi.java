package org.knowm.xchange.idex.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.CompleteBalancesReq;
import org.knowm.xchange.idex.dto.ReturnCompleteBalancesResponse;

@Path("/returnCompleteBalances")
@Api(description = "the returnCompleteBalances API")
@Consumes("application/json")
@Produces("application/json")
public interface ReturnCompleteBalancesApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(
      value =
          "Returns your available balances along with the amount you have in open orders for each token, indexed by token symbol.",
      notes = "",
      tags = "account")
  @ApiResponses(
      @ApiResponse(code = 200, message = "", response = ReturnCompleteBalancesResponse.class))
  ReturnCompleteBalancesResponse completeBalances(CompleteBalancesReq completeBalancesReq);
}
