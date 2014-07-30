package com.xeiam.xchange.cryptotrade;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeDepth;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicTrades;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;

@Path("api/1")
@Produces(MediaType.APPLICATION_JSON)
public interface CryptoTrade {

  @GET
  @Path("depth/{ident}_{currency}")
  CryptoTradeDepth getFullDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency);

  @GET
  @Path("ticker/{ident}_{currency}")
  CryptoTradeTicker getTicker(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency);

  @GET
  @Path("tradeshistory/{ident}_{currency}")
  CryptoTradePublicTrades getTradeHistory(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency);

  @GET
  @Path("tradeshistory/{ident}_{currency}/{since}")
  CryptoTradePublicTrades getTradeHistory(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @PathParam("since") long sinceTimestamp);

}
