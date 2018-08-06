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
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsFundtransferHistoryResponse;
import org.knowm.xchange.btcmarkets.dto.trade.*;
import org.knowm.xchange.btcmarkets.service.BTCMarketsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCMarketsAuthenticated {

  @GET
  @Path("account/balance")
  List<BTCMarketsBalance> getBalance(
      @HeaderParam("apikey") String publicKey,
      @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("signature") BTCMarketsDigest signer)
      throws BTCMarketsException, IOException;

  @POST
  @Path("order/create")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsPlaceOrderResponse placeOrder(
      @HeaderParam("apikey") String publicKey,
      @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("signature") BTCMarketsDigest signer,
      BTCMarketsOrder order)
      throws BTCMarketsException, IOException;

  @POST
  @Path("order/cancel")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsCancelOrderResponse cancelOrder(
      @HeaderParam("apikey") String publicKey,
      @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("signature") BTCMarketsDigest signer,
      BTCMarketsCancelOrderRequest request)
      throws BTCMarketsException, IOException;

  @POST
  @Path("order/open")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsOrders getOpenOrders(
      @HeaderParam("apikey") String publicKey,
      @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("signature") BTCMarketsDigest signer,
      BTCMarketsOpenOrdersAndTradeHistoryRequest request)
      throws BTCMarketsException, IOException;

  @POST
  @Path("order/history")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsOrders getOrderHistory(
      @HeaderParam("apikey") String publicKey,
      @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("signature") BTCMarketsDigest signer,
      BTCMarketsOpenOrdersAndTradeHistoryRequest request)
      throws BTCMarketsException, IOException;

  @POST
  @Path("order/trade/history")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsTradeHistory getTradeHistory(
      @HeaderParam("apikey") String publicKey,
      @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("signature") BTCMarketsDigest signer,
      BTCMarketsOpenOrdersAndTradeHistoryRequest request)
      throws BTCMarketsException, IOException;

  @POST
  @Path("order/detail")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsOrders getOrderDetails(
      @HeaderParam("apikey") String publicKey,
      @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("signature") BTCMarketsDigest signer,
      BTCMarketsOrderDetailsRequest request)
      throws BTCMarketsException, IOException;

  @POST
  @Path("fundtransfer/withdrawCrypto")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsWithdrawCryptoResponse withdrawCrypto(
      @HeaderParam("apikey") String publicKey,
      @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("signature") BTCMarketsDigest signer,
      BTCMarketsWithdrawCryptoRequest request)
      throws BTCMarketsException, IOException;

  @GET
  @Path("fundtransfer/history")
  BTCMarketsFundtransferHistoryResponse fundtransferHistory(
      @HeaderParam("apikey") String publicKey,
      @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("signature") BTCMarketsDigest signer)
      throws BTCMarketsException, IOException;
}
