/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.mintpal;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.mintpal.dto.MintPalBaseResponse;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrders;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicTrade;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalTicker;

/**
 * @author jamespedwards42
 */
@Path("/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface MintPal {

  @GET
  @Path("market/summary")
  MintPalBaseResponse<List<MintPalTicker>> getAllTickers();

  @GET
  @Path("market/stats/{coin}/{exchange}")
  MintPalBaseResponse<MintPalTicker> getTicker(@PathParam("coin") final String coin, @PathParam("exchange") final String exchange);

  @GET
  @Path("market/orders/{coin}/{exchange}/ALL/200")
  MintPalBaseResponse<List<MintPalPublicOrders>> getOrders(@PathParam("coin") final String coin, @PathParam("exchange") final String exchange);

  @GET
  @Path("market/trades/{coin}/{exchange}")
  MintPalBaseResponse<List<MintPalPublicTrade>> getTrades(@PathParam("coin") final String coin, @PathParam("exchange") final String exchange);

}
