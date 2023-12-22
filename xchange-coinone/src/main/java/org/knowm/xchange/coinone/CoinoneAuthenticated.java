package org.knowm.xchange.coinone;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.knowm.xchange.coinone.dto.CoinoneException;
import org.knowm.xchange.coinone.dto.account.CoinoneAuthRequest;
import org.knowm.xchange.coinone.dto.account.CoinoneBalancesRequest;
import org.knowm.xchange.coinone.dto.account.CoinoneBalancesResponse;
import org.knowm.xchange.coinone.dto.account.CoinoneWithdrawRequest;
import org.knowm.xchange.coinone.dto.account.CoinoneWithdrawResponse;
import org.knowm.xchange.coinone.dto.trade.CoinoneOrderInfoRequest;
import org.knowm.xchange.coinone.dto.trade.CoinoneOrderInfoResponse;
import org.knowm.xchange.coinone.dto.trade.CoinoneTradeCancelRequest;
import org.knowm.xchange.coinone.dto.trade.CoinoneTradeRequest;
import org.knowm.xchange.coinone.dto.trade.CoinoneTradeResponse;
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
