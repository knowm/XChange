package com.xeiam.xchange.justcoin;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinDepth;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinPublicTrade;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;

/**
 * @author jamespedwards42
 */
@Path("api/")
@Produces(MediaType.APPLICATION_JSON)
public interface Justcoin {

  @GET
  @Path("v1/markets")
  List<JustcoinTicker> getTickers() throws IOException;

  @GET
  @Path("v1/markets/{ident}{currency}/depth")
  JustcoinDepth getDepth(final @PathParam("ident") String tradeableIdentifier, final @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("bitcoincharts/{price_currency}/trades.json")
  List<JustcoinPublicTrade> getTrades(final @PathParam("price_currency") String priceCurrency, final @QueryParam("since") Long sinceTradeId) throws IOException;
}
