package com.xeiam.xchange.bitfinex.v1;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bitfinex.v1.dto.BitfinexException;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexBalancesRequest;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexBalancesResponse;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexMarginInfosRequest;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexMarginInfosResponse;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexWithdrawalRequest;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexWithdrawalResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexActiveCreditsRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexActivePositionsResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCancelOfferRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCancelOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCreditResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNewOfferRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNewOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNonceOnlyRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOfferStatusRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOfferStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexPastTradesRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;

import si.mazi.rescu.ParamsDigest;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BitfinexAuthenticated extends Bitfinex {

  @POST
  @Path("order/new")
  BitfinexOrderStatusResponse newOrder(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexNewOrderRequest newOrderRequest) throws IOException, BitfinexException;

  @POST
  @Path("offer/new")
  BitfinexOfferStatusResponse newOffer(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexNewOfferRequest newOfferRequest) throws IOException, BitfinexException;

  @POST
  @Path("balances")
  BitfinexBalancesResponse[] balances(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexBalancesRequest balancesRequest) throws IOException, BitfinexException;

  @POST
  @Path("order/cancel")
  BitfinexOrderStatusResponse cancelOrders(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexCancelOrderRequest cancelOrderRequest) throws IOException, BitfinexException;

  @POST
  @Path("offer/cancel")
  BitfinexOfferStatusResponse cancelOffer(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexCancelOfferRequest cancelOfferRequest) throws IOException, BitfinexException;

  @POST
  @Path("orders")
  BitfinexOrderStatusResponse[] activeOrders(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexNonceOnlyRequest nonceOnlyRequest) throws IOException, BitfinexException;

  @POST
  @Path("offers")
  BitfinexOfferStatusResponse[] activeOffers(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexNonceOnlyRequest nonceOnlyRequest) throws IOException, BitfinexException;

  @POST
  @Path("positions")
  BitfinexActivePositionsResponse[] activePositions(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexNonceOnlyRequest nonceOnlyRequest) throws IOException, BitfinexException;

  @POST
  @Path("order/status")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  BitfinexOrderStatusResponse orderStatus(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexOrderStatusRequest orderStatusRequest) throws IOException, BitfinexException;

  @POST
  @Path("offer/status")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  BitfinexOfferStatusResponse offerStatus(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexOfferStatusRequest offerStatusRequest) throws IOException, BitfinexException;

  @POST
  @Path("mytrades")
  BitfinexTradeResponse[] pastTrades(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexPastTradesRequest pastTradesRequest) throws IOException, BitfinexException;

  @POST
  @Path("credits")
  BitfinexCreditResponse[] activeCredits(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexActiveCreditsRequest activeCreditsRequest)
          throws IOException, BitfinexException;

  @POST
  @Path("margin_infos")
  BitfinexMarginInfosResponse[] marginInfos(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexMarginInfosRequest marginInfosRequest) throws IOException, BitfinexException;
  
 @POST
  @Path("withdraw")
  BitfinexWithdrawalResponse[] withdraw(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature, BitfinexWithdrawalRequest withdrawalRequest) throws IOException, BitfinexException;


}
