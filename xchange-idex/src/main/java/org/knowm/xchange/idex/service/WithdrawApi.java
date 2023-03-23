package org.knowm.xchange.idex.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.WithdrawReq;
import org.knowm.xchange.idex.dto.WithdrawResponse;

@Path("/withdraw")
@Api(description = "the withdraw API")
@Consumes("application/json")
@Produces("application/json")
public interface WithdrawApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(
      value =
          "Withdraws funds associated with the address. You cannot withdraw funds that are tied up in open orders. JSON payload must include the following properties",
      notes = "",
      tags = "account")
  @ApiResponses(
      @ApiResponse(
          code = 200,
          message =
              "To derive the signature for this API call, hash the following parameters in this order contract addresstokenamountaddressnonceSalt the hash as described earlier and sign it to produce your signature triplet.Useful response upon withdrawal success is in the works, for now simply test that there is no error propertyin the result object to confirm your withdrawal has succeeded.",
          response = WithdrawResponse.class))
  WithdrawResponse withdraw(WithdrawReq withdrawReq);
}
