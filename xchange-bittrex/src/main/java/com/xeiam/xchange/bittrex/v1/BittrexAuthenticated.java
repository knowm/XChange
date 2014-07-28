package com.xeiam.xchange.bittrex.v1;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.bittrex.v1.dto.account.BittrexBalancesResponse;

@Path("v1.1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BittrexAuthenticated extends Bittrex {

  @GET
  @Path("account/getbalances")
  BittrexBalancesResponse balances(@QueryParam("apikey") String apiKey, @QueryParam("apisign") ParamsDigest signature, @QueryParam("nonce") String nonce) throws IOException;

}