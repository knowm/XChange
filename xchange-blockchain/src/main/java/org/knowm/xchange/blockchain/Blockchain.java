package org.knowm.xchange.blockchain;

import org.knowm.xchange.blockchain.dto.account.BlockchainSymbols;
import org.knowm.xchange.blockchain.dto.marketdata.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/** @author Tim Molter */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Blockchain {

  @Path("v3/exchange/tickers")
  @GET
  List<BlockchainTicker> getTickers();

  @Path("v3/exchange/tickers/{symbol}")
  @GET
  BlockchainTicker getTicker(@PathParam("symbol") String symbol);

  @Path("v3/exchange/l2/{symbol}")
  @GET
  BlockchainOrderBook getOrderBookL2(@PathParam("symbol") String symbol);

  @Path("v3/exchange/l3/{symbol}")
  @GET
  BlockchainOrderBook getOrderBookL3(@PathParam("symbol") String symbol);

  @Path("mercury-gateway/v1/trades/{id}")
  @GET
  List<BlockchainTrade> getExchangeTrades(@PathParam("id") Integer id);

  @Path("v3/exchange/symbols/{symbol}")
  @GET
  BlockchainSymbol getSymbol(@PathParam("symbol") String symbol);

  @Path("v3/exchange/symbols")
  @GET
  Map<String, BlockchainSymbols> getSymbols();
}
