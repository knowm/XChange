package org.knowm.xchange.blockbid;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.blockbid.dto.Health;
import org.knowm.xchange.blockbid.dto.Ticker;
import org.knowm.xchange.blockbid.dto.Market;
import org.knowm.xchange.blockbid.dto.Ohlc;
import org.knowm.xchange.blockbid.dto.Currency;
import org.knowm.xchange.blockbid.dto.Trade;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Blockbid {
	@GET
	@Path("healthz")
	Health getHealth() throws IOException;
	
	@GET
	@Path("tickers")
	Ticker []getTickers() throws IOException;
	
	@GET
	@Path("markets")
	Market []getMarkets() throws IOException;
	
	@GET
	@Path("ohlc")
	Ohlc []getOhlc(@QueryParam("market") String market) throws IOException;
	
	@GET
	@Path("currencies")
	Currency []getCurrencies() throws IOException;	
	
	@GET
	@Path("trades")
	Trade []getTrades(@QueryParam("market") String market) throws IOException;
}
