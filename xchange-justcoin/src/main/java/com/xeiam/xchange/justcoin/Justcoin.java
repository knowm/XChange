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
package com.xeiam.xchange.justcoin;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinDepth;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinPublicTrade;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;

/**
 * @author jamespedwards42
 */
@Path("api/")
@Produces(MediaType.APPLICATION_JSON)
public interface Justcoin {

  @GET
  @Path("v1/markets")
  List<JustcoinTicker> getTickers() throws IOException;

  @GET
  @Path("v1/markets/{ident}{currency}/depth")
  JustcoinDepth getDepth(final @PathParam("ident") String tradeableIdentifier, final @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("bitcoincharts/{price_currency}/trades.json")
  List<JustcoinPublicTrade> getTrades(final @PathParam("price_currency") String priceCurrency, final @QueryParam("since") Long sinceTradeId) throws IOException;
}
