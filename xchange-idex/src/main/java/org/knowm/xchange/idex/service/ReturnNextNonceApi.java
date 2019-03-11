package org.knowm.xchange.idex.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.NextNonceReq;
import org.knowm.xchange.idex.dto.ReturnNextNonceResponse;

@Path("/returnNextNonce")
@Api(description = "the returnNextNonce API")
@Consumes("application/json")
@Produces("application/json")
public interface ReturnNextNonceApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(
      value =
          "Returns the lowest nonce that you can use from the given address in one of the trade functions (see below)",
      notes = "",
      tags = "trade")
  @ApiResponses(
      @ApiResponse(
          code = 200,
          message = "Sample output: { nonce: 2650 }",
          response = ReturnNextNonceResponse.class))
  ReturnNextNonceResponse nextNonce(NextNonceReq nextNonceReq);
}
