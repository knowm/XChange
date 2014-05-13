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
package com.xeiam.xchange.cexio;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.cexio.dto.account.CexIOBalanceInfo;
import com.xeiam.xchange.cexio.dto.trade.CexIOOpenOrders;
import com.xeiam.xchange.cexio.dto.trade.CexIOOrder;

/**
 * Author: brox
 * Since: 2/5/14
 */

@Path("api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CexIOAuthenticated {

  @POST
  @Path("balance/")
  CexIOBalanceInfo getBalance(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;

  @POST
  @Path("open_orders/{ident}/{currency}/")
  CexIOOpenOrders getOpenOrders(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") long nonce) throws IOException;

  @POST
  @Path("cancel_order/")
  Object cancelOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("id") int orderId) throws IOException;

  @POST
  @Path("place_order/{ident}/{currency}/")
  CexIOOrder placeOrder(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") long nonce, @FormParam("type") CexIOOrder.Type type, @FormParam("price") BigDecimal price, @FormParam("amount") BigDecimal amount) throws IOException;

}
