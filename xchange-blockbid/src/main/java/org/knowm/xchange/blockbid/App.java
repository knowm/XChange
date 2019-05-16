package org.knowm.xchange.blockbid;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

import org.knowm.xchange.blockbid.BlockbidExchange;
import org.knowm.xchange.blockbid.dto.Health;
import org.knowm.xchange.blockbid.dto.Market;
import org.knowm.xchange.blockbid.dto.Ticker;
import org.knowm.xchange.blockbid.dto.Ohlc;
import org.knowm.xchange.blockbid.dto.Currency;
import org.knowm.xchange.blockbid.dto.Trade;

import si.mazi.rescu.RestProxyFactory;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
    	Exchange blockbidExchange = 
    			ExchangeFactory.INSTANCE.createExchange(BlockbidExchange.class.getName());
    	Blockbid blockbid = 
    			RestProxyFactory.createProxy(Blockbid.class, blockbidExchange.getExchangeSpecification().getHost());
    	
    	Health h = blockbid.getHealth();
    	Ticker tickers[] = blockbid.getTickers();
    	Market markets[] = blockbid.getMarkets();
    	Ohlc ohlc[] = blockbid.getOhlc("btcaud");
    	Currency currencies[] = blockbid.getCurrencies();
    	Trade trades[] = blockbid.getTrades("btcaud");
    	
    	
    	for(int z=0; z<trades.length; z++) {
    		System.out.println(trades[z]);
    	}
    	for(int y=0; y<currencies.length; y++) {
    		System.out.println(currencies[y]);
    	}
    	for (int k=0; k<ohlc.length; k++) {
    		System.out.println(ohlc[k]);
    	}
    	for (int j=0; j<markets.length; j++) {
    		System.out.println(markets[j]);
    	}
    	for(int i=0; i<tickers.length; i++) {
    		System.out.println(tickers[i]);
    	}
    	System.out.println(h);
    }
}
