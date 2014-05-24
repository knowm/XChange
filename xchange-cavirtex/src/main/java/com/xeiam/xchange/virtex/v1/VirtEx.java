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
package com.xeiam.xchange.virtex.v1;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExTrade;

/**
 * @author timmolter
 */
@Deprecated
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface VirtEx {

  @GET
  @Path("{currency}/ticker.json")
  public VirtExTicker getTicker(@PathParam("currency") String currency) throws IOException;

  @GET
  @Path("{currency}/orderbook.json")
  public VirtExDepth getFullDepth(@PathParam("currency") String currency) throws IOException;

  @GET
  @Path("{currency}/trades.json")
  public VirtExTrade[] getTrades(@PathParam("currency") String currency) throws IOException;

}
