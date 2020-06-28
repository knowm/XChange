package org.knowm.xchange.bitcoinde.v4;

import java.io.IOException;
import javax.ws.rs.Produces;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeCompactOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTradesWrapper;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("v4")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitcoinde {
    
  @GET
  @Path("{trading_pair}/orderbook/compact")
  BitcoindeCompactOrderbookWrapper getCompactOrderBook(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("trading_pair") String tradingPair)
      throws IOException, BitcoindeException;

  @GET
  @Path("{trading_pair}/orderbook")
  BitcoindeOrderbookWrapper getOrderBook(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("trading_pair") String tradingPair,
      @QueryParam("type") String type,
      @QueryParam("order_requirements_fullfilled") Integer orderRequirementsFullfilled,
      @QueryParam("only_kyc_full") Integer onlyKycFull,
      @QueryParam("only_express_orders") Integer onlyExpressOrders)
      throws IOException, BitcoindeException;

  @GET
  @Path("{trading_pair}/trades/history")
  BitcoindeTradesWrapper getTrades(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("trading_pair") String tradingPair,
      @QueryParam("since_tid") Integer since)
      throws IOException, BitcoindeException;
}
