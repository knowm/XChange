package com.xeiam.xchange.cryptofacilities;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.cryptofacilities.dto.account.CryptoFacilitiesBalance;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCancel;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenOrders;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrder;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTrades;


/**
 * @author Jean-Christophe Laruelle
 */

	@Path("/api")
	@Produces(MediaType.APPLICATION_JSON)
	public interface CryptoFacilitiesAuthenticated extends CryptoFacilities {


	@POST
	@Path("/balance")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CryptoFacilitiesBalance balance(@HeaderParam("APIKey") String apiKey, @HeaderParam("Authent") ParamsDigest signer,
		@HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

	@POST
	@Path("/placeOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CryptoFacilitiesOrder placeOrder(@HeaderParam("APIKey") String apiKey, @HeaderParam("Authent") ParamsDigest signer,
		@HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("type") String type, @QueryParam("tradeable") String tradeable, @QueryParam("unit") String unit,
		@QueryParam("dir") String dir, @QueryParam("qty") BigDecimal qty, @QueryParam("price") BigDecimal price) throws IOException;

	@POST
	@Path("/cancelOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CryptoFacilitiesCancel cancelOrder(@HeaderParam("APIKey") String apiKey, @HeaderParam("Authent") ParamsDigest signer,
		@HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("uid") String uid, @QueryParam("tradeable") String tradeable, @QueryParam("unit") String unit) throws IOException;

	@POST
	@Path("/openOrders")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CryptoFacilitiesOpenOrders openOrders(@HeaderParam("APIKey") String apiKey, @HeaderParam("Authent") ParamsDigest signer,
		@HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

	@POST
	@Path("/trades")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CryptoFacilitiesTrades trades(@HeaderParam("APIKey") String apiKey, @HeaderParam("Authent") ParamsDigest signer,
		@HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("number") Integer number) throws IOException;

}
