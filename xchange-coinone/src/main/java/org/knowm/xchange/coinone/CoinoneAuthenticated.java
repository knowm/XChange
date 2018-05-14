package org.knowm.xchange.coinone;

import java.io.IOException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coinone.dto.CoinoneException;
import org.knowm.xchange.coinone.dto.account.*;
import org.knowm.xchange.coinone.dto.trade.*;
import si.mazi.rescu.ParamsDigest;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CoinoneAuthenticated extends Coinone {

  @POST
  @Path("v2/account/balance/")
  CoinoneBalancesResponse getWallet(
      @HeaderParam("X-COINONE-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-COINONE-SIGNATURE") ParamsDigest signature,
      CoinoneBalancesRequest coinoneBalanceRequest)
      throws IOException, CoinoneException;

  @POST
  @Path("v2/order/limit_buy/")
  CoinoneTradeResponse limitBuy(
      @HeaderParam("X-COINONE-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-COINONE-SIGNATURE") ParamsDigest signature,
      CoinoneTradeRequest coinoneTradeRequest)
      throws IOException, CoinoneException;

  @POST
  @Path("v2/order/limit_sell/")
  CoinoneTradeResponse limitSell(
      @HeaderParam("X-COINONE-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-COINONE-SIGNATURE") ParamsDigest signature,
      CoinoneTradeRequest coinoneTradeRequest)
      throws IOException, CoinoneException;

  @POST
  @Path("v2/order/order_info/")
  CoinoneOrderInfoResponse getOrder(
      @HeaderParam("X-COINONE-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-COINONE-SIGNATURE") ParamsDigest signature,
      CoinoneOrderInfoRequest coinoneOrderInfoRequest)
      throws IOException, CoinoneException;

  @POST
  @Path("v2/order/cancel/")
  CoinoneTradeResponse cancelOrder(
      @HeaderParam("X-COINONE-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-COINONE-SIGNATURE") ParamsDigest signature,
      CoinoneTradeCancelRequest orderParams)
      throws IOException, CoinoneException;

  @POST
  @Path("v2/transaction/auth_number/")
  CoinoneWithdrawResponse auth(
      @HeaderParam("X-COINONE-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-COINONE-SIGNATURE") ParamsDigest signature,
      CoinoneAuthRequest coinoneAuthRequest)
      throws IOException, CoinoneException;

  @POST
  @Path("v2/transaction/coin/")
  CoinoneWithdrawResponse withdrawFund(
      @HeaderParam("X-COINONE-PAYLOAD") ParamsDigest payload,
      @HeaderParam("X-COINONE-SIGNATURE") ParamsDigest signature,
      CoinoneWithdrawRequest coinoneWithdrawRequest)
      throws IOException, CoinoneException;
}
