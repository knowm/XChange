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
package com.xeiam.xchange.bitcoinium;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTickerHistory;

/**
 * @author veken0m
 */
@Path("service")
public interface Bitcoinium {

  @GET
  @Path("tickerupdate")
  @Produces(MediaType.APPLICATION_JSON)
  public BitcoiniumTicker getTicker(@QueryParam("pair") String pair, @QueryParam("apikey") String apikey);

  @GET
  @Path("orderbook")
  @Produces(MediaType.APPLICATION_JSON)
  public BitcoiniumOrderbook getDepth(@QueryParam("pair") String pair, @QueryParam("pricewindow") String pricewindow, @QueryParam("apikey") String apikey);

  @GET
  @Path("tickerhistory")
  @Produces(MediaType.APPLICATION_JSON)
  public BitcoiniumTickerHistory getTickerHistory(@QueryParam("pair") String pair, @QueryParam("timewindow") String timewindow, @QueryParam("apikey") String apikey);

}
