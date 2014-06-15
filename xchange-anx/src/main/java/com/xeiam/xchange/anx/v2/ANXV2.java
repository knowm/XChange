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
package com.xeiam.xchange.anx.v2;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.anx.v2.dto.ANXException;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXAccountInfoWrapper;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXBitcoinDepositAddressWrapper;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXWalletHistoryWrapper;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXWithdrawalResponseWrapper;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXDepthWrapper;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXDepthsWrapper;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTickerWrapper;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTickersWrapper;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTradesWrapper;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXGenericResponse;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXLagWrapper;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXOpenOrderWrapper;

/**
 * @author timmolter
 */
@Path("api/2")
@Produces(MediaType.APPLICATION_JSON)
public interface ANXV2 {

  @GET
  @Path("money/order/lag")
  ANXLagWrapper getLag() throws ANXException, IOException;

  @GET
  @Path("{ident}{currency}/money/ticker")
  ANXTickerWrapper getTicker(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws ANXException, IOException;

  @GET
  @Path("{ident}{currency}/money/ticker")
  ANXTickersWrapper getTickers(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @QueryParam("extraCcyPairs") String extraCurrencyPairs) throws ANXException,
      IOException;

  @GET
  @Path("{ident}{currency}/money/depth/fetch")
  ANXDepthWrapper getPartialDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws ANXException, IOException;

  @GET
  @Path("{ident}{currency}/money/depth/full")
  ANXDepthWrapper getFullDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws ANXException, IOException;

  @GET
  @Path("{ident}{currency}/money/depth/full")
  ANXDepthsWrapper getFullDepths(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @QueryParam("extraCcyPairs") String extraCurrencyPairs) throws ANXException,
      IOException;

  @GET
  @Path("{ident}{currency}/money/trade/fetch")
  ANXTradesWrapper getTrades(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @QueryParam("since") long since) throws ANXException, IOException;

  // Account Info API

  @POST
  @Path("money/info")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXAccountInfoWrapper getAccountInfo(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce) throws ANXException,
      IOException;

  @POST
  @Path("money/{currency}/address")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXBitcoinDepositAddressWrapper requestDepositAddress(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
      @PathParam("currency") String currency) throws ANXException, IOException;

  @POST
  @Path("money/{currency}/send_simple")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXWithdrawalResponseWrapper withdrawBtc(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
      @PathParam("currency") String currency, @FormParam("address") String address, @FormParam("amount_int") int amount, @FormParam("fee_int") int fee, @FormParam("no_instant") boolean noInstant,
      @FormParam("green") boolean green) throws ANXException, IOException;

  // Trade API
  @POST
  @Path("money/orders")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXOpenOrderWrapper getOpenOrders(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce) throws ANXException,
      IOException;

  /**
   * @param postBodySignatureCreator
   * @param amount can be omitted to place market order
   */
  @POST
  @Path("{tradeIdent}{currency}/money/order/add")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXGenericResponse placeOrder(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
      @PathParam("tradeIdent") String tradableIdentifier, @PathParam("currency") String currency, @FormParam("type") String type, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price) throws ANXException, IOException;

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
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXGenericResponse cancelOrder(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
      @FormParam("oid") String orderId) throws ANXException, IOException;

  /**
   * Returns the History of the selected wallet
   * 
   * @param apiKey
   * @param postBodySignatureCreator
   * @param nonce
   * @param currency
   * @param page to fetch (can be null for first page)
   * @return
   * @throws com.xeiam.xchange.anx.v2.dto.ANXException
   */
  @POST
  @Path("money/wallet/history")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXWalletHistoryWrapper getWalletHistory(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
      @FormParam("currency") String currency, @FormParam("page") Integer page) throws ANXException, IOException;
}
