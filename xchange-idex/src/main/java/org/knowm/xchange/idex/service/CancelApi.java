package org.knowm.xchange.idex.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.CancelReq;
import org.knowm.xchange.idex.dto.CancelResponse;

@Path("/cancel")
@Api(description = "the cancel API")
@Consumes("application/json")
@Produces("application/json")
public interface CancelApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(
      value =
          "Cancels an order associated with the address. JSON input must include the following properties",
      notes = "",
      tags = "trade")
  @ApiResponses(
      @ApiResponse(
          code = 200,
          message =
              "To derive the signature for this API call, hash the following parameters in this orderorderHashnonceSalt and sign the hash as usual to prepare your payloadSample output:{ success: 1 }",
          response = CancelResponse.class))
  CancelResponse cancel(CancelReq cancelReq);
}
