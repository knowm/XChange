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
package com.xeiam.xchange.btce.v3;

import java.io.IOException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEDepthWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEExchangeInfo;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETradesWrapper;

/**
 * @author timmolter
 */
@Path("/")
public interface BTCE {

  @GET
  @Path("api/3/info")
  BTCEExchangeInfo getInfo() throws IOException;

  @GET
  @Path("api/3/ticker/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  BTCETickerWrapper getTicker(@PathParam("pairs") String pairs, @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid) throws IOException;

  @GET
  @Path("api/3/depth/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  BTCEDepthWrapper getDepth(@PathParam("pairs") String pairs, @DefaultValue("150") @QueryParam("limit") int limit, @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid)
      throws IOException;

  @GET
  @Path("api/3/trades/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  BTCETradesWrapper getTrades(@PathParam("pairs") String pairs, @DefaultValue("1") @QueryParam("limit") int limit, @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid)
      throws IOException;

}
