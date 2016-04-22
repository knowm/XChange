package org.knowm.xchange.mexbt;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.mexbt.dto.MeXBTException;
import org.knowm.xchange.mexbt.dto.MeXBTInsPaginationRequest;
import org.knowm.xchange.mexbt.dto.MeXBTInsRequest;
import org.knowm.xchange.mexbt.dto.MeXBTRequest;
import org.knowm.xchange.mexbt.dto.MeXBTResponse;
import org.knowm.xchange.mexbt.dto.account.MeXBTBalanceResponse;
import org.knowm.xchange.mexbt.dto.account.MeXBTDepositAddressesResponse;
import org.knowm.xchange.mexbt.dto.account.MeXBTOpenOrdersResponse;
import org.knowm.xchange.mexbt.dto.account.MeXBTTradeResponse;
import org.knowm.xchange.mexbt.dto.account.MeXBTUserInfoResponse;
import org.knowm.xchange.mexbt.dto.account.MeXBTWithdrawRequest;
import org.knowm.xchange.mexbt.dto.trade.MeXBTOrderCancelRequest;
import org.knowm.xchange.mexbt.dto.trade.MeXBTOrderCreateRequest;
import org.knowm.xchange.mexbt.dto.trade.MeXBTOrderModifyRequest;
import org.knowm.xchange.mexbt.dto.trade.MeXBTServerOrderIdResponse;

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
