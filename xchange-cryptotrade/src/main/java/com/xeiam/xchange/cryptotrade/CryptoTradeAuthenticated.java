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
package com.xeiam.xchange.cryptotrade;

import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeAccountInfo;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeTransactions;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeCancelOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrdering;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradePlaceOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders.CryptoTradeOrder;

@Path("api/1/private")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CryptoTradeAuthenticated extends CryptoTrade {

  @POST
  @Path("getinfo")
  CryptoTradeAccountInfo getInfo(@HeaderParam("AuthKey") String apiKey, @HeaderParam("AuthSign") ParamsDigest signer, @FormParam("nonce") int nonce);

  @POST
  @Path("trade")
  CryptoTradePlaceOrderReturn trade(@FormParam("pair") String pair, @FormParam("type") CryptoTradeOrderType type, @FormParam("rate") BigDecimal rate, @FormParam("amount") BigDecimal amount,
      @HeaderParam("AuthKey") String apiKey, @HeaderParam("AuthSign") ParamsDigest signer, @FormParam("nonce") int nonce);

  @POST
  @Path("cancelorder")
  CryptoTradeCancelOrderReturn cancelOrder(@FormParam("orderid") long orderId, @HeaderParam("AuthKey") String apiKey, @HeaderParam("AuthSign") ParamsDigest signer, @FormParam("nonce") int nonce);

  @POST
  @Path("orderinfo")
  CryptoTradeOrder getOrderInfo(@FormParam("orderid") long orderId, @HeaderParam("AuthKey") String apiKey, @HeaderParam("AuthSign") ParamsDigest signer, @FormParam("nonce") int nonce);

  @POST
  @Path("tradeshistory")
  CryptoTradeTrades getTradeHistory(@FormParam("start_id") Long startId, @FormParam("end_id") Long endId, @FormParam("start_date") Long startDate, @FormParam("end_date") Long endDate, 
      @FormParam("count") int count, @FormParam("order") CryptoTradeOrdering ordering, @FormParam("pair") String currencyPair, @HeaderParam("AuthKey") String apiKey, @HeaderParam("AuthSign") ParamsDigest signer, @FormParam("nonce") int nonce);
 
  @POST
  @Path("ordershistory")
  CryptoTradeOrders getOrderHistory(@FormParam("start_id") Long startId, @FormParam("end_id") Long endId, @FormParam("start_date") Long startDate, @FormParam("end_date") Long endDate, 
      @FormParam("count") int count, @FormParam("order") CryptoTradeOrdering ordering, @FormParam("pair") String currencyPair, @HeaderParam("AuthKey") String apiKey, @HeaderParam("AuthSign") ParamsDigest signer, @FormParam("nonce") int nonce);
 
  @POST
  @Path("transactions")
  CryptoTradeTransactions getTransactionHistory(@FormParam("start_id") Long startId, @FormParam("end_id") Long endId, @FormParam("start_date") Long startDate, @FormParam("end_date") Long endDate, 
      @FormParam("count") int count, @FormParam("order") CryptoTradeOrdering ordering, @HeaderParam("AuthKey") String apiKey, @HeaderParam("AuthSign") ParamsDigest signer, @FormParam("nonce") int nonce);


}
