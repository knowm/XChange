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
package com.xeiam.xchange.bitfinex.v1;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexBalancesRequest;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexBalancesResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCancelOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNewOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNonceOnlyRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexPastTradesRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BitfinexAuthenticated extends Bitfinex {

  @POST
  @Path("order/new")
  BitfinexOrderStatusResponse newOrder(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload, @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature,
      BitfinexNewOrderRequest newOrderRequest) throws IOException;

  @POST
  @Path("balances")
  BitfinexBalancesResponse[] balances(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload, @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature,
      BitfinexBalancesRequest balancesRequest) throws IOException;

  @POST
  @Path("order/cancel")
  BitfinexOrderStatusResponse cancelOrders(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload, @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature,
      BitfinexCancelOrderRequest cancelOrderRequest) throws IOException;

  @POST
  @Path("orders")
  BitfinexOrderStatusResponse[] activeOrders(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload, @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature,
      BitfinexNonceOnlyRequest nonceOnlyRequest) throws IOException;

  @POST
  @Path("order/status")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  BitfinexOrderStatusResponse orderStatus(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload, @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature,
      BitfinexOrderStatusRequest orderStatusRequest) throws IOException;

  @POST
  @Path("mytrades")
  BitfinexTradeResponse[] pastTrades(@HeaderParam("X-BFX-APIKEY") String apiKey, @HeaderParam("X-BFX-PAYLOAD") ParamsDigest payload, @HeaderParam("X-BFX-SIGNATURE") ParamsDigest signature,
      BitfinexPastTradesRequest pastTradesRequest) throws IOException;
}
