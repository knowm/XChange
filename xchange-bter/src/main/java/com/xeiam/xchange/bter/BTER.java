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

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bter.dto.marketdata.BTERCurrencyPairs;
import com.xeiam.xchange.bter.dto.marketdata.BTERDepth;
import com.xeiam.xchange.bter.dto.marketdata.BTERTicker;
import com.xeiam.xchange.bter.dto.marketdata.BTERTickers;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory;

@Path("api/1")
@Produces(MediaType.APPLICATION_JSON)
public interface BTER {

  @GET
  @Path("pairs")
  BTERCurrencyPairs getPairs() throws IOException;

  @GET
  @Path("tickers")
  BTERTickers getTickers() throws IOException;
  
  @GET
  @Path("ticker/{ident}_{currency}")
  BTERTicker getTicker(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("depth/{ident}_{currency}")
  BTERDepth getFullDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;
  
  @GET
  @Path("trade/{ident}_{currency}")
  BTERTradeHistory getTradeHistory(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("trade/{ident}_{currency}/{tradeId}")
  BTERTradeHistory getTradeHistorySince(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @PathParam("tradeId") String tradeId) throws IOException;
}
