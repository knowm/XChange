package org.knowm.xchange.ccex;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.ccex.dto.account.CCEXBalanceResponse;
import org.knowm.xchange.ccex.dto.account.CCEXBalancesResponse;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("t")
@Produces(MediaType.APPLICATION_JSON)
public interface CCEXAuthenticated extends CCEX {

	@GET
	@Path("api.html?a=getbalances&apikey={apikey}&nonce={nonce}")
	public CCEXBalancesResponse balances(@PathParam("apikey") String apikey, @HeaderParam("apisign") ParamsDigest signature, @PathParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

	@GET
	@Path("api.html?a=getbalance&apikey={apikey}&nonce={nonce}&currency={currency}")
	CCEXBalanceResponse getdepositaddress(@PathParam("apikey") String apikey, @HeaderParam("apisign") ParamsDigest signature, @PathParam("nonce") SynchronizedValueFactory<Long> nonce, @PathParam("currency") String currency) throws IOException;
	
}
