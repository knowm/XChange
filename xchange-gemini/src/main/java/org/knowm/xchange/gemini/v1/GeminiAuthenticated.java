package org.knowm.xchange.gemini.v1;

import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiDepositAddressRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiDepositAddressResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWithdrawalRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWithdrawalResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCancelOrderRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNewOrderRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNonceOnlyRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderStatusRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderStatusResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiPastTradesRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiTradeResponse;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface GeminiAuthenticated extends Gemini {

  @POST
  @Path("order/new")
  GeminiOrderStatusResponse newOrder(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
                                     @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiNewOrderRequest newOrderRequest) throws IOException, GeminiException;

  @POST
  @Path("balances")
  GeminiBalancesResponse[] balances(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
                                    @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiBalancesRequest balancesRequest) throws IOException, GeminiException;

  @POST
  @Path("order/cancel")
  GeminiOrderStatusResponse cancelOrders(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
                                         @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiCancelOrderRequest cancelOrderRequest) throws IOException, GeminiException;

  @POST
  @Path("orders")
  GeminiOrderStatusResponse[] activeOrders(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
                                           @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiNonceOnlyRequest nonceOnlyRequest) throws IOException, GeminiException;

  @POST
  @Path("order/status")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  GeminiOrderStatusResponse orderStatus(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
                                        @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiOrderStatusRequest orderStatusRequest) throws IOException, GeminiException;

  @POST
  @Path("mytrades")
  GeminiTradeResponse[] pastTrades(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
                                   @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature, GeminiPastTradesRequest pastTradesRequest) throws IOException, GeminiException;

  @POST
  @Path("withdraw/{currency}")
  GeminiWithdrawalResponse withdraw(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
                                    @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature,
                                    @PathParam("currency") String currency, GeminiWithdrawalRequest withdrawalRequest) throws IOException, GeminiException;

  @POST
  @Path("deposit/{currency}/newAddress")
  GeminiDepositAddressResponse requestNewAddress(@HeaderParam("X-GEMINI-APIKEY") String apiKey, @HeaderParam("X-GEMINI-PAYLOAD") ParamsDigest payload,
                                                 @HeaderParam("X-GEMINI-SIGNATURE") ParamsDigest signature,
                                                 @PathParam("currency") String currency, GeminiDepositAddressRequest depositRequest) throws IOException, GeminiException;
}
