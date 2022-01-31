package org.knowm.xchange.btcmarkets;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
