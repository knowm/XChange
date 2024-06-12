package org.knowm.xchange.idex.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.DepositsWithdrawalsReq;
import org.knowm.xchange.idex.dto.ReturnDepositsWithdrawalsResponse;

@Path("/returnDepositsWithdrawals")
@Api(description = "the returnDepositsWithdrawals API")
@Consumes("application/json")
@Produces("application/json")
public interface ReturnDepositsWithdrawalsApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(
      value =
          "Returns your deposit and withdrawal history within a range, specified by the \"start\" and \"end\" properties of the JSON input, both of which must be UNIX timestamps. Withdrawals can be marked as \"PENDING\" if they are queued for dispatch, \"PROCESSING\" if the transaction has been dispatched, and \"COMPLETE\" if the transaction has been mined.",
      notes = "",
      tags = "account")
  @ApiResponses(
      @ApiResponse(code = 200, message = "", response = ReturnDepositsWithdrawalsResponse.class))
  ReturnDepositsWithdrawalsResponse fundingHistory(DepositsWithdrawalsReq depositsWithdrawalsReq);
}
