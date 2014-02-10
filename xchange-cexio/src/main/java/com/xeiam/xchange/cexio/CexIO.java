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

import com.xeiam.xchange.cexio.dto.marketdata.CexIODepth;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTicker;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTrade;

import javax.ws.rs.*;
import java.io.IOException;

/**
 * Author: brox
 * Since:  2/5/14
 */

@Path("api")
@Produces("application/json")
public interface CexIO {
  @GET
  @Path("ticker/{ident}/{currency}")
  CexIOTicker getTicker(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency)
            throws IOException;

  @GET
  @Path("order_book/{ident}/{currency}")
  CexIODepth getDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("trade_history/{ident}/{currency}/")
  CexIOTrade[] getTrades(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("trade_history/{ident}/{currency}/")
  CexIOTrade[] getTrades(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @DefaultValue("1") @QueryParam("since") int since) throws IOException;

}
