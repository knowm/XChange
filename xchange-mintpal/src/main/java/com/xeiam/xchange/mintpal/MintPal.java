package com.xeiam.xchange.mintpal;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.mintpal.dto.MintPalBaseResponse;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrders;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicTrade;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalTicker;

/**
 * @author jamespedwards42
 */
@Path("/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface MintPal {

  @GET
  @Path("market/summary")
  MintPalBaseResponse<List<MintPalTicker>> getAllTickers();

  @GET
  @Path("market/stats/{coin}/{exchange}")
  MintPalBaseResponse<MintPalTicker> getTicker(@PathParam("coin") final String coin, @PathParam("exchange") final String exchange);

  @GET
  @Path("market/orders/{coin}/{exchange}/ALL/200")
  MintPalBaseResponse<List<MintPalPublicOrders>> getOrders(@PathParam("coin") final String coin, @PathParam("exchange") final String exchange);

  @GET
  @Path("market/trades/{coin}/{exchange}")
  MintPalBaseResponse<List<MintPalPublicTrade>> getTrades(@PathParam("coin") final String coin, @PathParam("exchange") final String exchange);

}
