package org.knowm.xchange.bitbns.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbns.dto.BItbnsOrderBooks;
import org.knowm.xchange.bitbns.dto.BitbnsOrderBook;
import org.knowm.xchange.bitbns.dto.BitbnsTrade;
import org.knowm.xchange.bitbns.dto.BitbnsTrades;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class BitbnsMarketDataService extends BitbnsMarketDataServiceRaw implements MarketDataService {

	public BitbnsMarketDataService(Exchange exchange) {
		super(exchange);
	}

	
	@Override
	public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
		BItbnsOrderBooks bitbnsOrderBookBuy = getBitbnsOrderBookBuy(currencyPair,args);
		 List<LimitOrder> bids = new ArrayList<>();
		 for(BitbnsOrderBook e:bitbnsOrderBookBuy.getData()){
			 bids.add(new LimitOrder(OrderType.BID, new BigDecimal(e.getBtc()) , currencyPair, null, null, e.getRate()));
		 }
//		 List<LimitOrder> bids =
//				 bitbnsOrderBookBuy.getBids().entrySet().stream()
//			            .map(
//			                e ->
//			                    new LimitOrder(
//			                        OrderType.BID, e.getValue(), currencyPair, null, null, e.getKey()))
//			            .collect(Collectors.toList());
		 
		 BItbnsOrderBooks bitbnsOrderBookSell = getBitbnsOrderBookSell(currencyPair, args);
		 List<LimitOrder> asks = new ArrayList<>();
		 for(BitbnsOrderBook e:bitbnsOrderBookSell.getData()){
			 asks.add(new LimitOrder(OrderType.BID, new BigDecimal(e.getBtc()) , currencyPair, null, null, e.getRate()));
		 }
		
		return new OrderBook(new Date(), asks, bids);
	}
	
	@Override
	public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
		List<BitbnsTrade> bitbnsTrades=getBitbnsTrades(currencyPair, args);
		List<Trade> trades=new ArrayList<>();
		
		for(BitbnsTrade bitbnsTrade:bitbnsTrades){
//			if(coindcxTrade.isMaker()){
//				
//				trades.add(new Trade.Builder()
//		                 .type(null)
//		                 .originalAmount(coindcxTrade.getQuantity())
//		                 .currencyPair(currencyPair)
//		                 .price(coindcxTrade.getPrice())
//		                 .timestamp(new Date(coindcxTrade.getTimestamp()))
//		                 .id(null)
//		                 .build());
// 
//			}
		}
		
		return new Trades(trades,TradeSortType.SortByTimestamp);
	}

	@Override
	public List<Ticker> getTickers(Params params) throws IOException {
		throw new NotYetImplementedForExchangeException();
	}
}
