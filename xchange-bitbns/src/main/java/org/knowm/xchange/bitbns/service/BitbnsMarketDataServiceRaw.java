package org.knowm.xchange.bitbns.service;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbns.dto.BItbnsOrderBooks;
import org.knowm.xchange.bitbns.dto.BitbnsTicker;
import org.knowm.xchange.currency.CurrencyPair;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BitbnsMarketDataServiceRaw extends BitbnsBaseService {

	protected BitbnsMarketDataServiceRaw(Exchange exchange) {
		super(exchange);
	}
	
	public BItbnsOrderBooks getBitbnsOrderBookBuy(CurrencyPair currencyPair,Object... args) throws IOException{
		
		return bitBns.getOrderBookBuy(getSymbol(currencyPair,args),args[0].toString());
	}
	
	public BItbnsOrderBooks getBitbnsOrderBookSell(CurrencyPair currencyPair,Object... args) throws IOException{
		return bitBns.getOrderBookSell(getSymbol(currencyPair,args),args[0].toString());
	}
	
//	public List<BitbnsTrade> getBitbnsTrades(CurrencyPair currencyPair, Object... args) throws IOException {
//		return bitBns.getTrade(getSymbol(currencyPair, args));
//	}
	
	public BitbnsTicker getBitbnsTicker(CurrencyPair currencyPair, Object... args) throws IOException {
		Map<String, BitbnsTicker> tickerMap = bitBns.getTicker(args[0].toString());
		return tickerMap.get(getSymbol(currencyPair));
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
	
	public static void main(String[] args) {
		try {
 
			URL obj = new URL("https://api.bitbns.com/api/trade/v1/orderbook/sell/BNSUSDT");
			URLConnection conn = obj.openConnection();
			Map<String, List<String>> map = conn.getHeaderFields();
 
			System.out.println("Printing All Response Header for URL: " + obj.toString() + "\n");
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
			}
			
			System.out.println("\nGet Response Header By Key ...\n");
			List<String> contentLength = map.get("Content-Length");
			if (contentLength == null) {
				System.out.println("'Content-Length' doesn't present in Header!");
			} else {
				for (String header : contentLength) {
					System.out.println("Content-Lenght: " + header);
				}
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
