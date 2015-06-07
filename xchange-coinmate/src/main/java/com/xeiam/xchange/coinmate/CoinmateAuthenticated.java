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
package com.xeiam.xchange.coinmate;

import com.xeiam.xchange.coinmate.dto.account.CoinmateBalance;
import com.xeiam.xchange.coinmate.dto.account.CoinmateDepositAddresses;
import com.xeiam.xchange.coinmate.dto.trade.CoinmateTradeResponse;
import com.xeiam.xchange.coinmate.dto.trade.CoinmateCancelOrderResponse;
import com.xeiam.xchange.coinmate.dto.trade.CoinmateOpenOrders;
import com.xeiam.xchange.coinmate.dto.trade.CoinmateTransactionHistory;
import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 *
 * @author Martin Stachon
 */
@Path("api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CoinmateAuthenticated extends Coinmate {

  // acount info
  @POST
  @Path("balances")
  public CoinmateBalance getBalances(@FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  // trade
  @POST
  @Path("transactionHistory")
  public CoinmateTransactionHistory getTransactionHistory(@FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("offset") int offset,
      @FormParam("limit") int limit,
      @FormParam("sort") String sort) throws IOException;

  @POST
  @Path("openOrders")
  public CoinmateOpenOrders getOpenOrders(@FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currencyPair") String currencyPair) throws IOException;

  @POST
  @Path("cancelOrder")
  public CoinmateCancelOrderResponse cancelOder(@FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("orderId") String orderId) throws IOException;

  @POST
  @Path("buyLimit")
  public CoinmateTradeResponse buyLimit(@FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price,
      @FormParam("currencyPair") String currencyPair
  ) throws IOException;

  @POST
  @Path("sellLimit")
  public CoinmateTradeResponse sellLimit(@FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price,
      @FormParam("currencyPair") String currencyPair
  ) throws IOException;

  @POST
  @Path("buyInstant")
  public CoinmateTradeResponse buyInstant(@FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("total") BigDecimal total,
      @FormParam("currencyPair") String currencyPair
  ) throws IOException;

  @POST
  @Path("sellInstant")
  public CoinmateTradeResponse sellInstant(@FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("currencyPair") String currencyPair
  ) throws IOException;

  // withdrawal and deposits
  @POST
  @Path("bitcoinWithdrawal")
  public CoinmateTradeResponse bitcoinWithdrawal(@FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address
  ) throws IOException;

  @POST
  @Path("bitcoinDepositAddresses")
  public CoinmateDepositAddresses bitcoinDepositAddresses(@FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce
  ) throws IOException;
}
