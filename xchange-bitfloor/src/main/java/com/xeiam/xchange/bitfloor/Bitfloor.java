/*
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitfloor;

import java.math.BigDecimal;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.bitfloor.dto.Product;
import com.xeiam.xchange.bitfloor.dto.account.BitfloorBalance;
import com.xeiam.xchange.bitfloor.dto.account.WithdrawResult;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorDayInfo;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorOrderBook;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorTicker;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorTransaction;
import com.xeiam.xchange.bitfloor.dto.trade.BitfloorOrder;
import com.xeiam.xchange.bitfloor.dto.trade.NewOrderResult;
import com.xeiam.xchange.bitfloor.dto.trade.OrderCancelResult;

/** @author Matija Mazi See https://www.bitfloor.net/api/ for up-to-date docs. */
@Path("/")
@Produces("application/json")
public interface Bitfloor {

  /** Returns "bids" and "asks". Each is a list of open orders and each order is represented as a list of price and amount. */
  @GET
  @Path("book/L2/1")
  @Produces("application/json")
  public BitfloorOrderBook getOrderBook();

  @GET
  @Path("ticker/1")
  @Produces("application/json")
  public BitfloorTicker getTicker();

  @GET
  @Path("day-info/1")
  @Produces("application/json")
  public BitfloorDayInfo getDayInfo();

  /** Returns descending list of transactions. */
  @GET
  @Path("trades/1")
  @Produces("application/json")
  public BitfloorTransaction[] getTransactions();

  @POST
  @Path("accounts")
  @Produces("application/json")
  public BitfloorBalance[] getBalance(@HeaderParam("bitfloor-key") String key, @HeaderParam("bitfloor-sign") ParamsDigest sign, @HeaderParam("bitfloor-passphrase") String passphrase,
      @HeaderParam("bitfloor-version") Version version, @FormParam("nonce") long nonce);

  // todo: converter from double to date
  @POST
  @Path("withdraw")
  @Produces("application/json")
  public WithdrawResult withdraw(@HeaderParam("bitfloor-key") String key, @HeaderParam("bitfloor-sign") ParamsDigest sign, @HeaderParam("bitfloor-passphrase") String passphrase,
      @HeaderParam("bitfloor-version") Version version, @FormParam("nonce") long nonce, @FormParam("amount") BigDecimal amount, @FormParam("currency") String currency,
      @FormParam("destination") String destination, @FormParam("method") WithdrawalMethod method);

  @POST
  @Path("order/cancel")
  @Produces("application/json")
  public OrderCancelResult cancelOrder(@HeaderParam("bitfloor-key") String key, @HeaderParam("bitfloor-sign") ParamsDigest sign, @HeaderParam("bitfloor-passphrase") String passphrase,
      @HeaderParam("bitfloor-version") Version version, @FormParam("nonce") long nonce, @FormParam("order_id") String orderId, @FormParam("product_id") Product product);

  @POST
  @Path("orders")
  @Produces("application/json")
  public BitfloorOrder[] getOpenOrders(@HeaderParam("bitfloor-key") String key, @HeaderParam("bitfloor-sign") ParamsDigest sign, @HeaderParam("bitfloor-passphrase") String passphrase,
      @HeaderParam("bitfloor-version") Version version, @FormParam("nonce") long nonce);

  @POST
  @Path("order/new")
  @Produces("application/json")
  public NewOrderResult newOrder(@HeaderParam("bitfloor-key") String key, @HeaderParam("bitfloor-sign") ParamsDigest sign, @HeaderParam("bitfloor-passphrase") String passphrase,
      @HeaderParam("bitfloor-version") Version version, @FormParam("nonce") long nonce, @FormParam("size") BigDecimal amount, @FormParam("price") BigDecimal price,
      @FormParam("side") BitfloorOrder.Side side, @FormParam("product_id") Product product);

  public static enum Version {
    v1;

    @Override
    public String toString() {

      return name().substring(1);
    }
  }

  public static enum WithdrawalMethod {
    bitcoin, ach
  }
}
