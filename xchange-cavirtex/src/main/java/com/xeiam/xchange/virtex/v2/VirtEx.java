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
package com.xeiam.xchange.virtex.v2;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExDepthWrapper;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTickerWrapper;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTradesWrapper;

/**
 * @author veken0m
 */
@Path("api2")
@Produces(MediaType.APPLICATION_JSON)
public interface VirtEx {

  @GET
  @Path("ticker.json?currencypair={ident}{currency}")
  public VirtExTickerWrapper getTicker(@PathParam("ident") String tradableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("orderbook.json?currencypair={ident}{currency}")
  public VirtExDepthWrapper getFullDepth(@PathParam("ident") String tradableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("trades.json?currencypair={ident}{currency}")
  public VirtExTradesWrapper getTrades(@PathParam("ident") String tradableIdentifier, @PathParam("currency") String currency) throws IOException;

}
