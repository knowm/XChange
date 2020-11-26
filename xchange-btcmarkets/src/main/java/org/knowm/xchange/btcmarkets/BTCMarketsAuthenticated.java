package org.knowm.xchange.btcmarkets;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.*;
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
      BTCMarketsOpenOrdersRequest request)
      throws BTCMarketsException, IOException;

  @POST
  @Path("order/history")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsOrders getOrderHistory(
      @HeaderParam("apikey") String publicKey,
      @HeaderParam("timestamp") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("signature") BTCMarketsDigest signer,
      BTCMarketsOpenOrdersRequest request)
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
