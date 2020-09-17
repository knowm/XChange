package org.knowm.xchange.coindcx;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.coindcx.dto.CoindcxOrderBook;
import org.knowm.xchange.coindcx.dto.CoindcxTrade;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Coindcx {

	@GET
	@Path("/market_data/orderbook")
	CoindcxOrderBook getOrderBook(@QueryParam("pair") String pair) throws IOException;

	@GET
	@Path("/market_data/trade_history")
	List<CoindcxTrade> getTrade(@QueryParam("pair") String pair);

}
