package org.knowm.xchange.bitbns;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitbns.dto.BItbnsOrderBooks;
import org.knowm.xchange.bitbns.dto.BitbnsOrderBook;
import org.knowm.xchange.bitbns.dto.BitbnsTrade;

 
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitbns {

	@GET
	@Path("/v1/orderbook/sell/{symbol}")
	BItbnsOrderBooks getOrderBookSell(@PathParam("symbol") String symbol,@HeaderParam("X-BITBNS-APIKEY") String apikey) throws IOException;
	
	@GET
	@Path("/v1/orderbook/buy/{symbol}")
	BItbnsOrderBooks getOrderBookBuy(@PathParam("symbol") String symbol,@HeaderParam("X-BITBNS-APIKEY") String apikey) throws IOException;
	
	@GET
	@Path("/market_data/trade_history")
	List<BitbnsTrade> getTrade(@QueryParam("pair") String pair);

}
