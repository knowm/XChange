/**
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
package com.xeiam.xchange.mtgox.v2;

import java.math.BigDecimal;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.mtgox.v2.dto.MtGoxException;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxAccountInfoWrapper;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxBitcoinDepositAddressWrapper;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxWalletHistoryWrapper;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxWithdrawalResponseWrapper;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxDepthWrapper;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTickerWrapper;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTradesWrapper;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxGenericResponse;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxLagWrapper;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxOpenOrderWrapper;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxOrderResultWrapper;

/**
 * @author timmolter
 */
@Path("api/2")
public interface MtGoxV2 {

  @GET
  @Path("money/order/lag")
  MtGoxLagWrapper getLag() throws MtGoxException;

  @GET
  @Path("{ident}{currency}/money/ticker")
  MtGoxTickerWrapper getTicker(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws MtGoxException;

  @GET
  @Path("{ident}{currency}/money/depth/fetch")
  MtGoxDepthWrapper getDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws MtGoxException;

  @GET
  @Path("{ident}{currency}/money/depth/full")
  MtGoxDepthWrapper getFullDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws MtGoxException;

  @GET
  @Path("{ident}{currency}/money/trades/fetch")
  MtGoxTradesWrapper getTrades(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws MtGoxException;

  @GET
  @Path("{ident}{currency}/money/trades/fetch")
  MtGoxTradesWrapper getTrades(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @QueryParam("since") long since) throws MtGoxException;

  // Account Info API

  @POST
  @Path("money/info")
  MtGoxAccountInfoWrapper getAccountInfo(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce) throws MtGoxException;

  @POST
  @Path("money/bitcoin/address")
  MtGoxBitcoinDepositAddressWrapper requestDepositAddress(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
      @FormParam("description") String description, @FormParam("ipn") String notificationUrl) throws MtGoxException;

  @POST
  @Path("money/bitcoin/send_simple")
  MtGoxWithdrawalResponseWrapper withdrawBtc(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
      @FormParam("address") String address, @FormParam("amount_int") int amount, @FormParam("fee_int") int fee, @FormParam("no_instant") boolean noInstant, @FormParam("green") boolean green)
      throws MtGoxException;

  // Trade API

  @POST
  @Path("money/orders")
  MtGoxOpenOrderWrapper getOpenOrders(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce) throws MtGoxException;

  /**
   * @param type "ask" or "bid"
   * @param orderId (Returned from placeOrder)
   */
  @POST
  @Path("money/order/result")
  MtGoxOrderResultWrapper getOrderResult(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
	      @FormParam("type") String type, @FormParam("order") String orderId) throws MtGoxException;
  
  /**
   * @param postBodySignatureCreator
   * @param amount can be omitted to place market order
   */
  @POST
  @Path("{tradeIdent}{currency}/money/order/add")
  MtGoxGenericResponse placeOrder(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
      @PathParam("tradeIdent") String tradableIdentifier, @PathParam("currency") String currency, @FormParam("type") String type, @FormParam("amount_int") BigDecimal amount,
      @FormParam("price_int") String price) throws MtGoxException;

  /**
   * Note: I know it's weird to have BTCEUR hardcoded in the URL, but it really doesn't seems to matter. BTCUSD works too.
   * <p>
   * 
   * @param apiKey
   * @param postBodySignatureCreator
   * @param nonce
   * @param orderId
   * @return
   */
  @POST
  @Path("BTCEUR/money/order/cancel")
  MtGoxGenericResponse cancelOrder(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
      @FormParam("oid") String orderId) throws MtGoxException;

  /**
   * Returns the History of the selected wallet
   *
   * @param apiKey
   * @param postBodySignatureCreator
   * @param nonce
   * @param currency
   * @param page to fetch (can be null for first page)
   * @return
   * @throws MtGoxException
   */
  @POST
  @Path("money/wallet/history")
  MtGoxWalletHistoryWrapper getWalletHistory(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
          @FormParam("currency") String currency, @FormParam("page") Integer page)
          throws MtGoxException;
}
