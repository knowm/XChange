/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.knowm.xchange.coinmate;

import java.io.IOException;
import java.math.BigDecimal;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateOrderBook;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateQuickRate;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateTicker;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateTransactions;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

/** @author Martin Stachon */
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface Coinmate {

  @GET
  @Path("ticker")
  CoinmateTicker getTicker(@QueryParam("currencyPair") String currencyPair) throws IOException;

  @GET
  @Path("orderBook")
  CoinmateOrderBook getOrderBook(
      @QueryParam("currencyPair") String currencyPair,
      @QueryParam("groupByPriceLimit") boolean groupByPriceLimit)
      throws IOException;

  @GET
  @Path("transactions")
  CoinmateTransactions getTransactions(
      @QueryParam("minutesIntoHistory") int minutesIntoHistory,
      @QueryParam("currencyPair") String currencyPair)
      throws IOException;

  @GET
  @Path("xchange")
  ExchangeMetaData getMetadata() throws IOException;

  @GET
  @Path("buyQuickRate")
  CoinmateQuickRate getBuyQuickRate(
      @FormParam("total") BigDecimal total,
      @FormParam("currencyPair") String currencyPair) throws IOException;

  @GET
  @Path("sellQuickRate")
  CoinmateQuickRate getSellQuickRate(
      @FormParam("amount") BigDecimal amount,
      @FormParam("currencyPair") String currencyPair) throws IOException;
}
