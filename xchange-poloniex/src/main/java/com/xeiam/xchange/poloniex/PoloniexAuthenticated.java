package com.xeiam.xchange.poloniex;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

/**
 * @author Zach Holmes
 */

@Path("tradingApi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PoloniexAuthenticated extends Poloniex {

  @POST
  HashMap<String, BigDecimal> getBalances(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature, @QueryParam("command") String command, @QueryParam("nonce") String nonce);

}
