package org.knowm.xchange.wazirx;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.wazirx.dto.WazirxOrderBook;
import org.knowm.xchange.wazirx.dto.WazirxTicker;
import org.knowm.xchange.wazirx.dto.WazirxTrade;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Wazirx {

	@GET
	@Path("/v2/depth")
	/**
	 * Order book 
	 * @param pair
	 * @return
	 * @throws IOException
	 */
	WazirxOrderBook getOrderBook(@QueryParam("market") String pair) throws IOException;

	@GET
	@Path("/v2/trades")
	/**
	 * Trade history for symbol wise
	 * @param pair
	 * @return
	 */
	List<WazirxTrade> getTrade(@QueryParam("market") String pair);
	
	@GET
	@Path("/v2/tickers")
	Map<String,WazirxTicker> getTicker();

}
