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
package com.xeiam.xchange.hitbtc;

import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTrades;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * @author kpysniak
 */
@Path("/api/1/")
@Produces(MediaType.APPLICATION_JSON)
public interface Hitbtc {

  /**
   * @return BTCCentral ticker
   * @throws IOException
   */
  @GET
  @Path("public/{currencyPair}/ticker")
  public HitbtcTicker getHitbtcTicker(@PathParam("currencyPair") String currencyPair) throws IOException;

  @GET
  @Path("public/{currencyPair}/orderbook")
  public HitbtcOrderBook getOrderBook(@PathParam("currencyPair") String currencyPair) throws IOException;

  @GET
  @Path("public/{currencyPair}/trades")
  public HitbtcTrades getTrades(@PathParam("currencyPair") String currencyPair, @QueryParam("from") String from, @QueryParam("by") String sortBy, @QueryParam("start_index") String startIndex,
      @QueryParam("max_results") String max_results, @DefaultValue("object") @QueryParam("format_item") String format_item) throws IOException;

  @GET
  @Path("public/symbols")
  public HitbtcSymbols getSymbols() throws IOException;
}
