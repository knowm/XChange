/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.itbit.v1;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.itbit.v1.dto.account.ItBitAccountInfoReturn;
import com.xeiam.xchange.itbit.v1.dto.trade.ItBitOrder;
import com.xeiam.xchange.itbit.v1.dto.trade.ItBitPlaceOrderRequest;

@Path("v1/wallets")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface ItBitAuthenticated {

  @GET
  @Path("?userId={userId}")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitAccountInfoReturn[] getInfo(@HeaderParam("Authorization") ParamsDigest signer, @HeaderParam("X-Auth-Timestamp") long timestamp, @HeaderParam("X-Auth-Nonce") int nonce,
      @PathParam("userId") String userId) throws IOException;

  @GET
  @Path("/{walletId}/orders")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitOrder[] getOrders(@HeaderParam("Authorization") ParamsDigest signer, @HeaderParam("X-Auth-Timestamp") long timestamp, @HeaderParam("X-Auth-Nonce") int nonce,
      @QueryParam("instrument") String instrument, @QueryParam("page") String page, @QueryParam("perPage") String perPage, @QueryParam("status") String status, @PathParam("walletId") String walletId)
      throws IOException;

  @POST
  @Path("/{walletId}/orders")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitOrder postOrder(@HeaderParam("Authorization") ParamsDigest signer, @HeaderParam("X-Auth-Timestamp") long timestamp, @HeaderParam("X-Auth-Nonce") int nonce,
      @PathParam("walletId") String walletId, ItBitPlaceOrderRequest request) throws IOException;

  /** Returns empty body, return object is always null */
  @DELETE
  @Path("/{walletId}/orders/{orderId}")
  @Consumes(MediaType.APPLICATION_JSON)
  Object cancelOrder(@HeaderParam("Authorization") ParamsDigest signer, @HeaderParam("X-Auth-Timestamp") long timestamp, @HeaderParam("X-Auth-Nonce") int nonce,
      @PathParam("walletId") String walletId, @PathParam("orderId") String orderId) throws IOException;

  @POST
  @Path("/{walletId}/cryptocurrency_withdrawals")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitOrder requestWithdrawal(@HeaderParam("Authorization") ParamsDigest signer, @HeaderParam("X-Auth-Timestamp") long timestamp, @HeaderParam("X-Auth-Nonce") int nonce,
      @PathParam("walletId") String walletId, ItBitPlaceOrderRequest request) throws IOException;
}
