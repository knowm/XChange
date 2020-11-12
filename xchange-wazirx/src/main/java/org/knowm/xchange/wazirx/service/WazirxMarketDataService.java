package org.knowm.xchange.wazirx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.wazirx.dto.WazirxOrderBook;
import org.knowm.xchange.wazirx.dto.WazirxTicker;
import org.knowm.xchange.wazirx.dto.WazirxTrade;

public class WazirxMarketDataService extends WazirxMarketDataServiceRaw implements MarketDataService {

	public WazirxMarketDataService(Exchange exchange) {
		super(exchange);
	}

	
	@Override
	public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
		WazirxOrderBook wazirxOrderBook = getWazirxOrderBook(currencyPair,args);
		
		List<LimitOrder> bids =new ArrayList<>();
	 	wazirxOrderBook.getBids().stream().forEach(mapObject ->{
	 		bids.add(new LimitOrder(OrderType.BID, mapObject.get(1), currencyPair, null, null, mapObject.get(0)));
		});
	 	
	 	List<LimitOrder> asks =new ArrayList<>();
	 	wazirxOrderBook.getAsks().stream().forEach(mapObject ->{
	 		
	                asks.add(new LimitOrder(OrderType.ASK, mapObject.get(1), currencyPair, null, null, mapObject.get(0)));
		 });
		
		return new OrderBook(new Date(), asks, bids);
	}
	
	@Override
	public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
		List<WazirxTrade> warzixTrades=getWazirxTrades(currencyPair, args);
		List<Trade> trades=new ArrayList<>();
		
		for(WazirxTrade wazirxTrade:warzixTrades){
				trades.add(new Trade.Builder()
		                 .type(null)
		                 .originalAmount(wazirxTrade.getVolume())
		                 .currencyPair(currencyPair)
		                 .price(wazirxTrade.getPrice())
		                 .timestamp(wazirxTrade.getCreated_at())
		                 .id(String.valueOf(wazirxTrade.getId()))
		                 .build());
 
		}
		
		return new Trades(trades,TradeSortType.SortByTimestamp);
	}

	@Override
	public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
		Map<String, WazirxTicker> ticker=getWazirxTicker();
		String symbol=currencyPair.base.getCurrencyCode().toLowerCase()+""+currencyPair.counter.getCurrencyCode().toLowerCase();
		WazirxTicker wazirxTicker = ticker.get(symbol);
		Ticker retTicker = new Ticker.Builder()
						 .currencyPair(currencyPair)
			             .open(new BigDecimal(wazirxTicker.getOpen()))
			             .ask(wazirxTicker.getSell())
			             .bid(wazirxTicker.getBuy())
			             .last(wazirxTicker.getLast())
			             .high(wazirxTicker.getHigh())
			             .low(wazirxTicker.getLow())
			             .volume(wazirxTicker.getVolume())
			             .build();
		
		return retTicker;
	}
	 
}
