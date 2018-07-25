package org.knowm.xchange.idex.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.BalancesReq;
import org.knowm.xchange.idex.dto.ReturnBalancesResponse;

@Path("/returnBalances")
@Api(description = "the returnBalances API")
@Consumes("application/json")
@Produces("application/json")
public interface ReturnBalancesApi {
  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(
      value = "",
      notes =
          "Returns your available balances (total deposited minus amount in open orders) indexed by token symbol.",
      tags = "account")
  @ApiResponses(@ApiResponse(code = 200, message = "", response = ReturnBalancesResponse.class))
  ReturnBalancesResponse balances(BalancesReq balancesReq);
}
