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
package com.xeiam.xchange.bitcurex;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexDepth;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTrade;

@Path("data")
public interface Bitcurex {

  @GET
  @Path("ticker.json")
  @Produces("application/json")
  public BitcurexTicker getTicker() throws IOException;

  @GET
  @Path("orderbook.json")
  @Produces("application/json")
  public BitcurexDepth getFullDepth() throws IOException;

  @GET
  @Path("trades.json")
  @Produces("application/json")
  public BitcurexTrade[] getTrades() throws IOException;

}
