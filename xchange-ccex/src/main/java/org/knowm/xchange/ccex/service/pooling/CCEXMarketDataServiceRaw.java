package org.knowm.xchange.ccex.service.pooling;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ccex.CCEX;
import org.knowm.xchange.ccex.dto.marketdata.CCEXGetorderbook;
import org.knowm.xchange.ccex.dto.marketdata.CCEXMarket;
import org.knowm.xchange.ccex.dto.marketdata.CCEXMarkets;
import org.knowm.xchange.ccex.dto.marketdata.CCEXTrade;
import org.knowm.xchange.ccex.dto.marketdata.CCEXTrades;
import org.knowm.xchange.currency.CurrencyPair;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author Andraž Prinčič
 */
public class CCEXMarketDataServiceRaw extends CCEXBasePollingService {

	private final CCEX ccex;

	public CCEXMarketDataServiceRaw(Exchange exchange) {
		super(exchange);
		this.ccex = RestProxyFactory.createProxy(CCEX.class, exchange.getExchangeSpecification().getSslUri());
	}

	public CCEXGetorderbook getCCEXOrderBook(CurrencyPair pair) throws IOException {
		return ccex.getOrderBook(new CCEX.Pair(pair));
	}

	public CCEXTrades getCCEXTrades(CurrencyPair pair) throws IOException {
		return ccex.getTrades(new CCEX.Pair(pair));
	}

	public List<CCEXMarket> getConbaseExProducts() throws IOException {
		CCEXMarkets cCEXTrades = ccex.getProducts();
	    return cCEXTrades.getResult();
	  }
	
	public enum CCEXTime {
		DAY, HOUR, MINUTE;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}
}
