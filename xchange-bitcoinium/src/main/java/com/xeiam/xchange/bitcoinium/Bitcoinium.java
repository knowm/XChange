/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
  @Produces("application/json")
  public BitcoiniumTicker getTicker(@QueryParam("pair") String pair);

  @GET
  @Path("orderbook?exchange={exchange}&pair={baseCurrency}_{currency}&pricewindow={window}")
  @Produces("application/json")
  public BitcoiniumOrderbook getFullDepth(@PathParam("exchange") String exchange, @PathParam("currency") String currency, @PathParam("baseCurrency") String baseCurrency,
      @PathParam("window") String window);

  @GET
  @Path("tickerhistory?exchange={exchange}&pair={baseCurrency}_{currency}&timewindow={window}")
  @Produces("application/json")
  public BitcoiniumTickerHistory getTrades(@PathParam("exchange") String exchange, @PathParam("currency") String currency, @PathParam("baseCurrency") String baseCurrency,
      @PathParam("window") String window);

}
