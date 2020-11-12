package org.knowm.xchange.wazirx.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.wazirx.dto.WazirxOrderBook;
import org.knowm.xchange.wazirx.dto.WazirxTicker;
import org.knowm.xchange.wazirx.dto.WazirxTrade;
import org.knowm.xchange.wazirx.dto.WazirxTrades;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WazirxMarketDataServiceRaw extends WazirxBaseService {

	protected WazirxMarketDataServiceRaw(Exchange exchange) {
		super(exchange);
	}
	
	public WazirxOrderBook getWazirxOrderBook(CurrencyPair currencyPair,Object... args) throws IOException{
		return wazirx.getOrderBook(getSymbol(currencyPair,args));
	}
	
	 public List<WazirxTrade> getWazirxTrades(CurrencyPair currencyPair,Object... args) throws IOException{
		 return wazirx.getTrade(getSymbol(currencyPair,args));
	 }
	 
	 public Map<String, WazirxTicker> getWazirxTicker() throws IOException{
		 return wazirx.getTicker();
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
	 */
	private String getSymbol(CurrencyPair currencyPair,Object... args){
		return currencyPair.base.getCurrencyCode().toLowerCase()+""+currencyPair.counter.getCurrencyCode().toLowerCase();
		
	}
	
	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		String str="[{p:10838.22,q:0.060051,s:BTCUSDT,T:1600361972204,m:true},{p:10838.23,q:0.004991,s:BTCUSDT,T:1600361971927,m:false}]";
//		String json = new ObjectMapper().writeValueAsString(str);
		WazirxTrades wazirxTrades=new ObjectMapper().readValue(str, WazirxTrades.class);
		System.out.println(wazirxTrades);
		
	}

}
