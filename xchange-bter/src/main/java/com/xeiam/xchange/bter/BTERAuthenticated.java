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
package com.xeiam.xchange.bter;

import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.bter.dto.account.BTERAccountInfoReturn;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrdersReturn;
import com.xeiam.xchange.bter.dto.trade.BTEROrder;
import com.xeiam.xchange.bter.dto.trade.BTEROrderStatusReturn;
import com.xeiam.xchange.bter.dto.trade.BTERPlaceOrderReturn;

@Path("api/1/private")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BTERAuthenticated {

  @POST
  @Path("getfunds")
  BTERAccountInfoReturn getInfo(@HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer, @FormParam("nonce") int nonce);

  @POST
  @Path("placeorder")
  BTERPlaceOrderReturn Trade(@HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("pair") String pair,
      @FormParam("type") BTEROrder.Type type, @FormParam("rate") BigDecimal rate, @FormParam("amount") BigDecimal amount);

  @POST
  @Path("orderlist")
  BTEROpenOrdersReturn getOpenOrders(@HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer, @FormParam("nonce") int nonce);

  @POST
  @Path("getorder")
  BTEROrderStatusReturn getOrderStatus(@HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer, @FormParam("nonce") int nonce,
                                       @FormParam("order_id") String orderId);
}
