package org.knowm.xchange.bitbns.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbns.dto.BItbnsOrderBooks;
import org.knowm.xchange.bitbns.dto.BitbnsOrderBook;
import org.knowm.xchange.bitbns.dto.BitbnsTrade;
import org.knowm.xchange.bitbns.dto.BitbnsTrades;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BitbnsMarketDataServiceRaw extends BitbnsBaseService {

	protected BitbnsMarketDataServiceRaw(Exchange exchange) {
		super(exchange);
	}
	
	public BItbnsOrderBooks getBitbnsOrderBookBuy(CurrencyPair currencyPair,Object... args) throws IOException{
		return bitBns.getOrderBookBuy(getSymbol(currencyPair,args),"F4D525935E2FC19900C89DB649537F0E");
	}
	
	public BItbnsOrderBooks getBitbnsOrderBookSell(CurrencyPair currencyPair,Object... args) throws IOException{
		return bitBns.getOrderBookSell(getSymbol(currencyPair,args),"F4D525935E2FC19900C89DB649537F0E");
	}
	
	 public List<BitbnsTrade> getBitbnsTrades(CurrencyPair currencyPair,Object... args) throws IOException{
		 return bitBns.getTrade(getSymbol(currencyPair,args));
	 }
	
	/**
	 * @param currencyPair
	 * @param args
	 * @return
	 *  this is used to specify the exchange for the given market. Valid values for ecode include:
	 *  B: Binance
	 *	I: CoinDCX
	 *	HB: HitBTC
	 *	H: Huobi
	 *	BM: BitMEX
	 *  BB : Bitbns
	 */
	private String getSymbol(CurrencyPair currencyPair,Object... args){
		
			return currencyPair.base.getCurrencyCode().toUpperCase()+""+currencyPair.counter.getCurrencyCode();
		
	}
	
	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		String str="[{p:10838.22,q:0.060051,s:BTCUSDT,T:1600361972204,m:true},{p:10838.23,q:0.004991,s:BTCUSDT,T:1600361971927,m:false}]";
//		String json = new ObjectMapper().writeValueAsString(str);
		BitbnsTrades bitBnsTrades=new ObjectMapper().readValue(str, BitbnsTrades.class);
		System.out.println(bitBnsTrades);
		
	}

}
