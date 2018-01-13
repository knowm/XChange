package org.xchange.bitz.service;

import java.io.IOException;
import java.util.Date;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.xchange.bitz.BitZ;
import org.xchange.bitz.BitZAuthenticated;
import org.xchange.bitz.dto.marketdata.BitZPublicOrder;
import org.xchange.bitz.dto.trade.result.BitZTradeAddResult;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitZTradeServiceRaw extends BitZBaseService {

  private BitZAuthenticated bitzAuthenticated;
  
  private String apiKey;
  private String secretKey;
  private String tradePwd;
  private BitZDigest signer;
  private SynchronizedValueFactory<Long> nonceFactory;
   
	public BitZTradeServiceRaw(Exchange exchange) {
		super(exchange);
		
		this.bitz = RestProxyFactory.createProxy(BitZ.class, exchange.getExchangeSpecification().getSslUri());
		this.bitzAuthenticated = RestProxyFactory.createProxy(BitZAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
	
		// TODO: Implement Password
		this.tradePwd = "";
		
		this.apiKey = exchange.getExchangeSpecification().getApiKey();
		this.secretKey = exchange.getExchangeSpecification().getSecretKey();
    
		this.signer = BitZDigest.createInstance();
		this.nonceFactory = exchange.getNonceFactory();
	}
	
	//TODO: Implement Method
	public boolean cancelBitZTrade(int orderId) throws IOException {
	  throw new NotYetImplementedForExchangeException();
	}
	
	//TODO: Implement Method
	public BitZTradeAddResult placeBitZTrade(CurrencyPair currencyPair, BitZPublicOrder limitOrder, Date time, boolean sell) throws IOException {
	  throw new NotYetImplementedForExchangeException();
	}
	
}
