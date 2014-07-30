package com.xeiam.xchange.bter;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.bter.dto.BTEROrderType;
import com.xeiam.xchange.bter.dto.account.BTERFunds;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrders;
import com.xeiam.xchange.bter.dto.trade.BTEROrderStatus;
import com.xeiam.xchange.bter.dto.trade.BTERPlaceOrderReturn;
import com.xeiam.xchange.bter.dto.trade.BTERTradeHistoryReturn;

@Path("api/1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BTERAuthenticated extends BTER {

  @POST
  @Path("private/getfunds")
  BTERFunds getFunds(@HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer, @FormParam("nonce") int nonce) throws IOException;

  @POST
  @Path("private/cancelorder")
  BTERFunds cancelOrder(@FormParam("order_id") String orderId, @HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer, @FormParam("nonce") int nonce) throws IOException;

  @POST
  @Path("private/placeorder")
  BTERPlaceOrderReturn placeOrder(@FormParam("pair") String pair, @FormParam("type") BTEROrderType type, @FormParam("rate") BigDecimal rate, @FormParam("amount") BigDecimal amount,
      @HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer, @FormParam("nonce") int nonce) throws IOException;

  @POST
  @Path("private/orderlist")
  BTEROpenOrders getOpenOrders(@HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer, @FormParam("nonce") int nonce) throws IOException;

  @POST
  @Path("private/mytrades")
  BTERTradeHistoryReturn getUserTradeHistory(@HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("pair") String pair)
      throws IOException;

  @POST
  @Path("private/getorder")
  BTEROrderStatus getOrderStatus(@FormParam("order_id") String orderId, @HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer, @FormParam("nonce") int nonce) throws IOException;
}
