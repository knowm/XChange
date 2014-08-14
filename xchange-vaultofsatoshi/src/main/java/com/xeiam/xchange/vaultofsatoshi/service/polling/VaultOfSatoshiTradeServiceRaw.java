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
package com.xeiam.xchange.vaultofsatoshi.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiAccount;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiTrading;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiUtils;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosCurrency;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosResponse;
import com.xeiam.xchange.vaultofsatoshi.dto.trade.VosTradeOrder;
import com.xeiam.xchange.vaultofsatoshi.dto.trade.VosOrderId;
import com.xeiam.xchange.vaultofsatoshi.service.VosDigest;

/**
 * @author veken0m
 */
public class VaultOfSatoshiTradeServiceRaw extends VaultOfSatoshiBasePollingService {

  private final VaultOfSatoshiAccount vosAuthenticated;
  private final VaultOfSatoshiTrading vosTrade;
  private final VosDigest signatureCreator;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public VaultOfSatoshiTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.vosAuthenticated = RestProxyFactory.createProxy(VaultOfSatoshiAccount.class, exchangeSpecification.getSslUri());
    this.vosTrade = RestProxyFactory.createProxy(VaultOfSatoshiTrading.class, exchangeSpecification.getSslUri());
    this.signatureCreator = VosDigest.createInstance(exchangeSpecification.getSecretKey(), exchangeSpecification.getApiKey());
  }

  public VosTradeOrder[] getVaultOfSatoshiOpenOrders(int numberOfTransactions) throws IOException {

	  VosResponse<VosTradeOrder[]> response = vosAuthenticated.getOpenOrders(exchangeSpecification.getApiKey(), signatureCreator, VaultOfSatoshiUtils.getNonce(), numberOfTransactions, "open_only");
    
	  checkResult(response);
	  return response.getData();
  }

  public int sellVaultOfSatoshiOrder(CurrencyPair currPair, BigDecimal tradableAmount, BigDecimal price) throws IOException {
	  
	  VosCurrency vosUnits = new VosCurrency(tradableAmount);
	  VosCurrency vosPrice = new VosCurrency(price);

	  VosResponse<VosOrderId> response = vosTrade.placeOrder(exchangeSpecification.getApiKey(), signatureCreator, VaultOfSatoshiUtils.getNonce(), "ask", currPair.baseSymbol, vosUnits.getPrecision(), vosUnits.getValue(), vosUnits.getValueInt(), currPair.counterSymbol, vosPrice.getPrecision(), vosPrice.getValue(), vosPrice.getValueInt());
	    
	  checkResult(response);
	  return response.getData().getOrder_id();
  }
  
  public int buyVaultOfSatoshiOrder(CurrencyPair currPair, BigDecimal tradableAmount, BigDecimal price) throws IOException {

	  VosCurrency vosUnits = new VosCurrency(tradableAmount);
	  VosCurrency vosPrice = new VosCurrency(price);

	  VosResponse<VosOrderId> response =  vosTrade.placeOrder(exchangeSpecification.getApiKey(), signatureCreator, VaultOfSatoshiUtils.getNonce(), "bid", currPair.baseSymbol, vosUnits.getPrecision(), vosUnits.getValue(), vosUnits.getValueInt(), currPair.counterSymbol, vosPrice.getPrecision(), vosPrice.getValue(), vosPrice.getValueInt());

	  checkResult(response);
	  return response.getData().getOrder_id();
  }

  public boolean cancelVaultOfSatoshiOrder(int orderId) throws IOException {

	  VosResponse<Void> response = vosTrade.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, VaultOfSatoshiUtils.getNonce(), orderId);
	
	  return checkResult(response);
  }

  public VosTradeOrder[] getVaultOfSatoshiUserTransactions(int numberOfTransactions) throws IOException {

	  VosResponse<VosTradeOrder[]> response = vosAuthenticated.getOrders(exchangeSpecification.getApiKey(), signatureCreator, VaultOfSatoshiUtils.getNonce(), numberOfTransactions);
	    
	  checkResult(response);
	  return response.getData();
  }
  
}
