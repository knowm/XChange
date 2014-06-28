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
package com.xeiam.xchange.btctrade;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.btctrade.dto.BTCTradeResult;
import com.xeiam.xchange.btctrade.dto.BTCTradeSecretResponse;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeBalance;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeWallet;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeDepth;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTicker;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTrade;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradeOrder;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradePlaceOrderResult;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCTrade {

  /**
   * Returns the quotations.
   *
   * @return the quotations.
   */
  @GET
  @Path("ticker")
  public BTCTradeTicker getTicker();

  /**
   * Returns the depth of the market.
   *
   * @return the depth of the market.
   */
  @GET
  @Path("depth")
  public BTCTradeDepth getDepth();

  /**
   * Returns 500 recent market transactions, in reverse chronological order.
   *
   * @return 500 recent market transactions.
   */
  @GET
  @Path("trades")
  public BTCTradeTrade[] getTrades();

  /**
   * Returns 500 market transactions which trade ID is greater than {@code since}, in reverse chronological order.
   *
   * @param since the trade ID.
   * @return 500 market transactions which trade ID is grater than the given ID.
   */
  @GET
  @Path("trades")
  public BTCTradeTrade[] getTrades(@QueryParam("since") long since);

  /**
   * Returns the secret for signing.
   *
   * @param passphrase the API private key.
   * @param key the API public key.
   * @return the secret for signing.
   */
  @POST
  @Path("getsecret")
  public BTCTradeSecretResponse getSecret(@FormParam("api_passphrase") String passphrase, @FormParam("key") String key);

  /**
   * Returns the account balance.
   *
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature
   * @return the account balance.
   */
  @POST
  @Path("balance")
  public BTCTradeBalance getBalance(@FormParam("nonce") long nonce, @FormParam("key") String key, @FormParam("signature") ParamsDigest signature);

  /**
   * Returns the deposit address.
   * 
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature.
   * @return the deposit address.
   */
  @POST
  @Path("wallet")
  public BTCTradeWallet getWallet(@FormParam("nonce") long nonce, @FormParam("key") String key, @FormParam("signature") ParamsDigest signature);

  /**
   * Return orders.
   *
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature.
   * @param since unix timestamp(UTC timezone). Default is 0, returns all.
   * @param type the order type: open, all.
   */
  @POST
  @Path("orders")
  public BTCTradeOrder[] getOrders(@FormParam("since") long since, @FormParam("type") String type, @FormParam("nonce") long nonce, @FormParam("key") String key,
      @FormParam("signature") ParamsDigest signature);

  /**
   * Returns order information.
   *
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature.
   * @param id the order ID.
   */
  @POST
  @Path("fetch_order")
  public BTCTradeOrder getOrder(@FormParam("id") String id, @FormParam("nonce") long nonce, @FormParam("key") String key, @FormParam("signature") ParamsDigest signature);

  /**
   * Cancels order.
   *
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature.
   * @param id the order ID.
   */
  @POST
  @Path("cancel_order")
  public BTCTradeResult cancelOrder(@FormParam("id") String id, @FormParam("nonce") long nonce, @FormParam("key") String key, @FormParam("signature") ParamsDigest signature);

  /**
   * Places a buy order.
   *
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature.
   * @param amount the quantity to buy.
   * @param price the price to buy.
   */
  @POST
  @Path("buy")
  public BTCTradePlaceOrderResult buy(@FormParam("amount") String amount, @FormParam("price") String price, @FormParam("nonce") long nonce, @FormParam("key") String key,
      @FormParam("signature") ParamsDigest signature);

  /**
   * Places a sell order.
   *
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature.
   * @param amount the quantity to sell.
   * @param price the price to sell.
   */
  @POST
  @Path("sell")
  public BTCTradePlaceOrderResult sell(@FormParam("amount") String amount, @FormParam("price") String price, @FormParam("nonce") long nonce, @FormParam("key") String key,
      @FormParam("signature") ParamsDigest signature);

}
