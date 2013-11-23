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
package com.xeiam.xchange.btce.v2;

import java.io.IOException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.xeiam.xchange.btce.v2.dto.marketdata.BTCEDepth;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCEDepthV3;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCEInfoV3;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETicker;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETickerV3;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETrade;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETradesV3;

/**
 * @author Matija Mazi
 */
@Path("api")
public interface BTCE {

  @GET
  @Path("2/{ident}_{currency}/ticker")
  BTCETicker getTicker(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("2/{ident}_{currency}/depth")
  BTCEDepth getPartialDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("2/{ident}_{currency}/trades")
  BTCETrade[] getTrades(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("3/info")
  BTCEInfoV3 getInfoV3() throws IOException;

  @GET
  @Path("3/ticker/{pairs}")
  BTCETickerV3 getTickerV3(@PathParam("pairs") String pairs, @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid) throws IOException;

  @GET
  @Path("3/depth/{pairs}")
  BTCEDepthV3 getDepthV3(@PathParam("pairs") String pairs, @DefaultValue("1") @QueryParam("limit") int limit, @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid) throws IOException;

  @GET
  @Path("3/trades/{pairs}")
  BTCETradesV3 getTradesV3(@PathParam("pairs") String pairs, @DefaultValue("1") @QueryParam("limit") int limit, @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid) throws IOException;

}
