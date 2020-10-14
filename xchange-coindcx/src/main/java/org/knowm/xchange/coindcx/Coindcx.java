package org.knowm.xchange.coindcx;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.coindcx.dto.CoindcxException;
import org.knowm.xchange.coindcx.dto.CoindcxNewOrderRequest;
import org.knowm.xchange.coindcx.dto.CoindcxOrderBook;
import org.knowm.xchange.coindcx.dto.CoindcxOrderStatusRequest;
import org.knowm.xchange.coindcx.dto.CoindcxOrderStatusResponse;
import org.knowm.xchange.coindcx.dto.CoindcxTrade;

import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Coindcx {

	@GET
	@Path("/market_data/orderbook")
	CoindcxOrderBook getOrderBook(@QueryParam("pair") String pair) throws IOException;

	@GET
	@Path("/market_data/trade_history")
	List<CoindcxTrade> getTrade(@QueryParam("pair") String pair);

	@POST
	@Path("/exchange/v1/orders/create")
	CoindcxOrderStatusResponse newOrder(@HeaderParam("X-AUTH-APIKEY") String apiKey,
			@HeaderParam("X-AUTH-SIGNATURE") ParamsDigest signature, CoindcxNewOrderRequest newOrderRequest);

	@POST
	@Path("/exchange/v1/orders/status")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	CoindcxOrderStatusResponse orderStatus(@HeaderParam("X-AUTH-APIKEY") String apiKey,
			@HeaderParam("X-AUTH-SIGNATURE") ParamsDigest signature, CoindcxOrderStatusRequest orderStatusRequest)
			throws IOException, CoindcxException;

}
