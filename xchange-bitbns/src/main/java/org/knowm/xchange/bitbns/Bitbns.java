package org.knowm.xchange.bitbns;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitbns.dto.BItbnsOrderBooks;
import org.knowm.xchange.bitbns.dto.BitbnsNewOrderRequest;
import org.knowm.xchange.bitbns.dto.BitbnsOrderPlaceStatusResponse;
import org.knowm.xchange.bitbns.dto.BitbnsOrderStatusResponse;
import org.knowm.xchange.bitbns.dto.BitbnsTicker;
import org.knowm.xchange.bitbns.dto.BitbnsTrade;
import org.knowm.xchange.bitbns.dto.OrderStatusBody;

import si.mazi.rescu.ParamsDigest;

 
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface Bitbns {

	@GET
	@Path("/v1/orderbook/sell/{symbol}")
	BItbnsOrderBooks getOrderBookSell(@PathParam("symbol") String symbol,@HeaderParam("X-BITBNS-APIKEY") String apikey) throws IOException;
	
	@GET
	@Path("/v1/orderbook/buy/{symbol}")
	BItbnsOrderBooks getOrderBookBuy(@PathParam("symbol") String symbol,@HeaderParam("X-BITBNS-APIKEY") String apikey) throws IOException;
	
	@GET
	@Path("/v1/tickers")
	Map<String, BitbnsTicker> getTicker(@HeaderParam("X-BITBNS-APIKEY") String apikey);

	@POST
	@Path("/v2/orders")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	Object newOrder(@HeaderParam("X-BITBNS-APIKEY") String apikey,
			@HeaderParam("X-BITBNS-PAYLOAD") ParamsDigest payload,
			@HeaderParam("X-BITBNS-SIGNATURE") ParamsDigest signature,
			String newOrderRequest);

	
	@POST
	@Path("/v1/orderStatus/{symbol}")
	BitbnsOrderStatusResponse orderStatus(@HeaderParam("X-BITBNS-APIKEY") String apikey,
			@HeaderParam("X-BITBNS-PAYLOAD") ParamsDigest payload,
			@HeaderParam("X-BITBNS-SIGNATURE") ParamsDigest signature,
			@PathParam("symbol") String symbol,
			OrderStatusBody orderStatusBody);
}
