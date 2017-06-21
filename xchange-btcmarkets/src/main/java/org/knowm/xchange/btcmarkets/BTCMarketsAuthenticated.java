package org.knowm.xchange.btcmarkets;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.btcmarkets.dto.BTCMarketsException;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsCancelOrderRequest;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsCancelOrderResponse;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsMyTradingRequest;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsPlaceOrderResponse;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsTradeHistory;
import org.knowm.xchange.btcmarkets.service.BTCMarketsDigest;

import si.mazi.rescu.SynchronizedValueFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCMarketsAuthenticated {

  @GET
  @Path("account/balance")
  List<BTCMarketsBalance> getBalance(@HeaderParam("apikey") String publicKey, @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("signature") BTCMarketsDigest signer) throws BTCMarketsException, IOException;

  @POST
  @Path("order/create")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsPlaceOrderResponse placeOrder(@HeaderParam("apikey") String publicKey,
      @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory, @HeaderParam("signature") BTCMarketsDigest signer,
      BTCMarketsOrder order) throws BTCMarketsException, IOException;

  @POST
  @Path("order/cancel")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsCancelOrderResponse cancelOrder(@HeaderParam("apikey") String publicKey,
      @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory, @HeaderParam("signature") BTCMarketsDigest signer,
      BTCMarketsCancelOrderRequest request) throws BTCMarketsException, IOException;

  @POST
  @Path("order/open")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsOrders getOpenOrders(@HeaderParam("apikey") String publicKey, @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("signature") BTCMarketsDigest signer, BTCMarketsMyTradingRequest request) throws BTCMarketsException, IOException;

  @POST
  @Path("order/history")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsOrders getOrderHistory(@HeaderParam("apikey") String publicKey, @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("signature") BTCMarketsDigest signer, BTCMarketsMyTradingRequest request) throws BTCMarketsException, IOException;

  @POST
  @Path("order/trade/history")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsTradeHistory getTradeHistory(@HeaderParam("apikey") String publicKey,
      @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory, @HeaderParam("signature") BTCMarketsDigest signer,
      BTCMarketsMyTradingRequest request) throws BTCMarketsException, IOException;
}
