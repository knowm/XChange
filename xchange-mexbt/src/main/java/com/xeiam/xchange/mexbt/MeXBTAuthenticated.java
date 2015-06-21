package com.xeiam.xchange.mexbt;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.mexbt.dto.MeXBTException;
import com.xeiam.xchange.mexbt.dto.MeXBTInsPaginationRequest;
import com.xeiam.xchange.mexbt.dto.MeXBTInsRequest;
import com.xeiam.xchange.mexbt.dto.MeXBTRequest;
import com.xeiam.xchange.mexbt.dto.MeXBTResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTBalanceResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTDepositAddressesResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTOpenOrdersResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTTradeResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTUserInfoResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTWithdrawRequest;
import com.xeiam.xchange.mexbt.dto.trade.MeXBTOrderCancelRequest;
import com.xeiam.xchange.mexbt.dto.trade.MeXBTOrderCreateRequest;
import com.xeiam.xchange.mexbt.dto.trade.MeXBTOrderModifyRequest;
import com.xeiam.xchange.mexbt.dto.trade.MeXBTServerOrderIdResponse;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface MeXBTAuthenticated extends MeXBT {

  @POST
  @Path("v1/orders/create")
  MeXBTServerOrderIdResponse createOrder(MeXBTOrderCreateRequest request) throws MeXBTException, IOException;

  @POST
  @Path("v1/orders/modify")
  MeXBTServerOrderIdResponse modifyOrder(MeXBTOrderModifyRequest request) throws MeXBTException, IOException;

  @POST
  @Path("v1/orders/cancel")
  MeXBTServerOrderIdResponse cancelOrder(MeXBTOrderCancelRequest request) throws MeXBTException, IOException;

  @POST
  @Path("v1/orders/cancel-all")
  MeXBTResponse cancelAll(MeXBTInsRequest request) throws MeXBTException, IOException;

  @POST
  @Path("v1/me")
  MeXBTUserInfoResponse getMe(MeXBTRequest request) throws MeXBTException, IOException;

  @POST
  @Path("v1/balance")
  MeXBTBalanceResponse getBalance(MeXBTRequest request) throws MeXBTException, IOException;

  @POST
  @Path("v1/trades")
  MeXBTTradeResponse getTrades(MeXBTInsPaginationRequest request) throws MeXBTException, IOException;

  @POST
  @Path("v1/orders")
  MeXBTOpenOrdersResponse getOpenOrders(MeXBTRequest request) throws MeXBTException, IOException;

  @POST
  @Path("v1/deposit-addresses")
  MeXBTDepositAddressesResponse getDepositAddresses(MeXBTRequest request) throws MeXBTException, IOException;

  @POST
  @Path("v1/withdraw")
  MeXBTResponse withdraw(MeXBTWithdrawRequest request) throws MeXBTException, IOException;

}
