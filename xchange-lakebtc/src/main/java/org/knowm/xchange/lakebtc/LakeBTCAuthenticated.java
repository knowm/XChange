package org.knowm.xchange.lakebtc;

import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.lakebtc.dto.LakeBTCRequest;
import org.knowm.xchange.lakebtc.dto.account.LakeBTCAccountInfoResponse;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCBuyOrderRequest;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCCancelRequest;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCCancelResponse;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCOrderResponse;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCOrdersRequest;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCOrdersResponse;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCSellOrderRequest;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCTradeResponse;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCTradesRequest;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** User: cristian.lucaci Date: 10/3/2014 Time: 5:04 PM */
@Path("api_v1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface LakeBTCAuthenticated {

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  LakeBTCAccountInfoResponse getAccountInfo(
      @HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> nonce,
      LakeBTCRequest getAccountInfoRequest)
      throws IOException;

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  LakeBTCOrderResponse placeBuyOrder(
      @HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> nonce,
      LakeBTCBuyOrderRequest orderRequest)
      throws IOException;

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  LakeBTCOrderResponse placeSellOrder(
      @HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> nonce,
      LakeBTCSellOrderRequest orderRequest)
      throws IOException;

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  LakeBTCCancelResponse cancelOrder(
      @HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> nonce,
      LakeBTCCancelRequest orderRequest)
      throws IOException;

  @POST
  @Path("/")
  LakeBTCTradeResponse[] pastTrades(
      @HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> nonce,
      LakeBTCTradesRequest pastTradesRequest)
      throws IOException;

  @POST
  @Path("/")
  LakeBTCOrdersResponse[] getOrders(
      @HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> nonce,
      LakeBTCOrdersRequest orderRequest)
      throws IOException;
}
