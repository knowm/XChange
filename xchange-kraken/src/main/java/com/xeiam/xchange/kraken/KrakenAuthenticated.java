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
package com.xeiam.xchange.kraken;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.kraken.dto.account.KrakenBalanceResult;
import com.xeiam.xchange.kraken.dto.account.KrakenLedgerInfoResult;
import com.xeiam.xchange.kraken.dto.account.KrakenQueryLedgerResult;
import com.xeiam.xchange.kraken.dto.account.KrakenTradeBalanceInfoResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenCancelOrderResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenClosedOrdersResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenOpenOrdersResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenQueryOrderResult;

@Path("0/private")
@Produces(MediaType.APPLICATION_JSON)
public interface KrakenAuthenticated {

  @POST
  @Path("Balance")
  public KrakenBalanceResult balance(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;

  @POST
  @Path("TradeBalance")
  public KrakenTradeBalanceInfoResult tradeBalance(@FormParam("aclass") String assetClass, @FormParam("asset") String asset, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;

  @POST
  @Path("Ledgers")
  public KrakenLedgerInfoResult ledgers(@FormParam("aclass") String assetClass, @FormParam("asset") String assets, @FormParam("type") String ledgerType, 
      @FormParam("start") String start, @FormParam("end") String end, @FormParam("ofs") String offset, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;

  @POST
  @Path("QueryLedgers")
  public KrakenQueryLedgerResult queryLedgers(@FormParam("id") String ledgerIds, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;

  @POST
  @Path("AddOrder")
  public KrakenOrderResult addOrder(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("pair") String pair,
      @FormParam("type") String type, @FormParam("ordertype") String ordertype, @FormParam("price") String price, @FormParam("volume") String volume) throws IOException;

  @POST
  @Path("CancelOrder")
  public KrakenCancelOrderResult cancelOrder(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") long nonce,
      @FormParam("txid") String transactionId) throws IOException;

  @POST
  @Path("OpenOrders")
  public KrakenOpenOrdersResult openOrders(@FormParam("trades") boolean includeTrades, @FormParam("userref") String userReferenceId, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;
  
  @POST
  @Path("ClosedOrders")
  public KrakenClosedOrdersResult closedOrders(@FormParam("trades") boolean includeTrades, @FormParam("userref") String userReferenceId, 
      @FormParam("start") String start, @FormParam("end") String end, @FormParam("ofs") String offset, @FormParam("closetime") String closeTime, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;

  @POST
  @Path("QueryOrders")
  public KrakenQueryOrderResult queryOrders(@FormParam("trades") boolean includeTrades, @FormParam("userref") String userReferenceId, @FormParam("txid") String transactionIds, @HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;
  
}
