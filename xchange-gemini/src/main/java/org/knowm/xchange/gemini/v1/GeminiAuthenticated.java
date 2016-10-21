package org.knowm.xchange.gemini.v1;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiDepositAddressRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiDepositAddressResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiMarginInfosRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiMarginInfosResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWithdrawalRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWithdrawalResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiActiveCreditsRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiActivePositionsResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCancelOfferRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCancelOrderMultiRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCancelOrderMultiResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCancelOrderRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCreditResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNewOfferRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNewOrderMultiRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNewOrderMultiResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNewOrderRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNonceOnlyRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOfferStatusRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOfferStatusResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderStatusRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderStatusResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiPastTradesRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiTradeResponse;

import si.mazi.rescu.ParamsDigest;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface GeminiAuthenticated extends Gemini {

  @POST
  @Path("order/new")
  GeminiOrderStatusResponse newOrder(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiNewOrderRequest newOrderRequest) throws IOException, GeminiException;

  @POST
  @Path("order/new/multi")
  GeminiNewOrderMultiResponse newOrderMulti(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiNewOrderMultiRequest newOrderMultiRequest) throws IOException, GeminiException;

  @POST
  @Path("offer/new")
  GeminiOfferStatusResponse newOffer(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiNewOfferRequest newOfferRequest) throws IOException, GeminiException;

  @POST
  @Path("balances")
  GeminiBalancesResponse[] balances(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiBalancesRequest balancesRequest) throws IOException, GeminiException;

  @POST
  @Path("order/cancel")
  GeminiOrderStatusResponse cancelOrders(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiCancelOrderRequest cancelOrderRequest) throws IOException, GeminiException;

  @POST
  @Path("order/cancel/multi")
  GeminiCancelOrderMultiResponse cancelOrderMulti(@HeaderParam("X-GEMINI-APIKEY") String apiKey,
      @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload, @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature,
      GeminiCancelOrderMultiRequest cancelOrderRequest) throws IOException, GeminiException;

  @POST
  @Path("offer/cancel")
  GeminiOfferStatusResponse cancelOffer(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiCancelOfferRequest cancelOfferRequest) throws IOException, GeminiException;

  @POST
  @Path("orders")
  GeminiOrderStatusResponse[] activeOrders(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiNonceOnlyRequest nonceOnlyRequest) throws IOException, GeminiException;

  @POST
  @Path("offers")
  GeminiOfferStatusResponse[] activeOffers(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiNonceOnlyRequest nonceOnlyRequest) throws IOException, GeminiException;

  @POST
  @Path("positions")
  GeminiActivePositionsResponse[] activePositions(@HeaderParam("X-GEMINI-APIKEY") String apiKey,
      @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload, @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature,
      GeminiNonceOnlyRequest nonceOnlyRequest) throws IOException, GeminiException;

  @POST
  @Path("order/status")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  GeminiOrderStatusResponse orderStatus(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiOrderStatusRequest orderStatusRequest) throws IOException, GeminiException;

  @POST
  @Path("offer/status")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  GeminiOfferStatusResponse offerStatus(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiOfferStatusRequest offerStatusRequest) throws IOException, GeminiException;

  @POST
  @Path("mytrades")
  GeminiTradeResponse[] pastTrades(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiPastTradesRequest pastTradesRequest) throws IOException, GeminiException;

  @POST
  @Path("credits")
  GeminiCreditResponse[] activeCredits(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiActiveCreditsRequest activeCreditsRequest) throws IOException, GeminiException;

  @POST
  @Path("margin_infos")
  GeminiMarginInfosResponse[] marginInfos(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiMarginInfosRequest marginInfosRequest) throws IOException, GeminiException;

  @POST
  @Path("withdraw")
  GeminiWithdrawalResponse[] withdraw(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiWithdrawalRequest withdrawalRequest) throws IOException, GeminiException;

  @POST
  @Path("deposit/new")
  GeminiDepositAddressResponse requestDeposit(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiDepositAddressRequest depositRequest) throws IOException, GeminiException;

}
