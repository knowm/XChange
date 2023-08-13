package org.knowm.xchange.btcmarkets;

import java.io.IOException;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.btcmarkets.dto.v3.BTCMarketsExceptionV3;
import org.knowm.xchange.btcmarkets.dto.v3.account.BTCMarketsAddressesResponse;
import org.knowm.xchange.btcmarkets.dto.v3.account.BTCMarketsTradingFeesResponse;
import org.knowm.xchange.btcmarkets.dto.v3.trade.BTCMarketsPlaceOrderRequest;
import org.knowm.xchange.btcmarkets.dto.v3.trade.BTCMarketsPlaceOrderResponse;
import org.knowm.xchange.btcmarkets.dto.v3.trade.BTCMarketsTradeHistoryResponse;
import org.knowm.xchange.btcmarkets.service.BTCMarketsDigestV3;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/v3/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCMarketsAuthenticatedV3 {

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCMarketsPlaceOrderResponse placeOrder(
      @HeaderParam("BM-AUTH-APIKEY") String publicKey,
      @HeaderParam("BM-AUTH-TIMESTAMP") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("BM-AUTH-SIGNATURE") BTCMarketsDigestV3 signer,
      BTCMarketsPlaceOrderRequest order)
      throws BTCMarketsExceptionV3, IOException;

  @GET
  @Path("addresses")
  BTCMarketsAddressesResponse depositAddress(
      @HeaderParam("BM-AUTH-APIKEY") String publicKey,
      @HeaderParam("BM-AUTH-TIMESTAMP") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("BM-AUTH-SIGNATURE") BTCMarketsDigestV3 signer,
      @QueryParam("assetName") String assetName)
      throws BTCMarketsExceptionV3, IOException;

  @GET
  @Path("trades")
  List<BTCMarketsTradeHistoryResponse> trades(
      @HeaderParam("BM-AUTH-APIKEY") String publicKey,
      @HeaderParam("BM-AUTH-TIMESTAMP") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("BM-AUTH-SIGNATURE") BTCMarketsDigestV3 signer,
      @QueryParam("marketId") String marketId,
      @QueryParam("before") String before,
      @QueryParam("after") String after,
      @QueryParam("limit") Integer limit)
      throws BTCMarketsExceptionV3, IOException;

  @GET
  @Path("accounts/me/trading-fees")
  BTCMarketsTradingFeesResponse tradingFees(
      @HeaderParam("BM-AUTH-APIKEY") String publicKey,
      @HeaderParam("BM-AUTH-TIMESTAMP") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("BM-AUTH-SIGNATURE") BTCMarketsDigestV3 signer)
      throws BTCMarketsExceptionV3, IOException;
}