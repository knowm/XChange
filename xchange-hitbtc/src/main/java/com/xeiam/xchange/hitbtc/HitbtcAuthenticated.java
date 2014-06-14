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
package com.xeiam.xchange.hitbtc;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalanceResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrdersResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcTradeResponse;

@Path("/api/1/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public interface HitbtcAuthenticated extends Hitbtc {

  @GET
  @Path("trading/orders/active")
  public HitbtcOrdersResponse getHitbtcActiveOrders(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") long nonce, @QueryParam("apikey") String apiKey
  /* @QueryParam("symbols") String symbols */) throws IOException;

  @POST
  @Path("trading/new_order")
  @Produces(MediaType.APPLICATION_FORM_URLENCODED)
  public HitbtcExecutionReportResponse postHitbtcNewOrder(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") long nonce, @QueryParam("apikey") String apiKey,
      @FormParam("clientOrderId") String clientOrderId, @FormParam("symbol") String symbol, @FormParam("side") String side, @FormParam("price") BigDecimal price,
      @FormParam("quantity") BigDecimal quantity, // 1 lot = 0.01 BTC
      @FormParam("type") String type, @FormParam("timeInForce") String timeInForce) throws IOException;

  @POST
  @Path("trading/cancel_order")
  @Produces(MediaType.APPLICATION_FORM_URLENCODED)
  public HitbtcExecutionReportResponse postHitbtcCancelOrder(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") long nonce, @QueryParam("apikey") String apiKey,
      @FormParam("clientOrderId") String clientOrderId, @FormParam("cancelRequestClientOrderId") String cancelRequestClientOrderId, @FormParam("symbol") String symbol, @FormParam("side") String side)
      throws IOException;

  @GET
  @Path("trading/trades")
  public HitbtcTradeResponse getHitbtcTrades(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") long nonce, @QueryParam("apikey") String apiKey, @QueryParam("by") String by,
      @QueryParam("start_index") int start_index, @QueryParam("max_results") int max_results, @QueryParam("symbols") String symbols
  // @QueryParam("sort") String sort,
  // @QueryParam("from") String from,
  // @QueryParam("till") String till
      ) throws IOException;

  @GET
  @Path("trading/balance")
  public HitbtcBalanceResponse getHitbtcBalance(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") long nonce, @QueryParam("apikey") String apiKey) throws IOException;

}
