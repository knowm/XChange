package com.xeiam.xchange.coinfloor.streaming;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinfloor.CoinfloorUtils;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;

/**
 * <p>
 * Class that parses user inputs, and does all the generation of requests
 * </p>
 * @author obsessiveOrange
 */
public class RequestFactory {
	public static class CoinfloorAuthenticationRequest {

	  final String method = "Authenticate";
	  final int tag = 001;
	  
	  @JsonProperty("user_id")
	  final long userId;
	  final String cookie;
	  final byte[] nonce;
	  final String serverNonce;

	  List<String> signature;

	  public CoinfloorAuthenticationRequest(long userId, String cookie, String password, String serverNonce){
	    this.userId = userId;
	    this.cookie = cookie;
	    this.nonce = CoinfloorUtils.buildClientNonce();
	    this.serverNonce = serverNonce;
	    this.signature = CoinfloorUtils.buildSignature(userId, cookie, password, serverNonce, nonce);
	  }

	  public String getMethod() {
	    return method;
	  }
	  
	  public int getTag() {
	    return tag;
	  }

	  public long getUserId() {
	    return userId;
	  }
	  
	  public String getCookie() {
	    return cookie;
	  }

	  public byte[] getNonce() {
	    return nonce;
	  }

	  public String serverNonce() {
	    return serverNonce;
	  }

	  public List<String> getSignature() {
	    return signature;
	  }
	}
	
	public static class GetBalancesRequest{
		private final String method = "GetBalances";
		private final int tag = 101;

		public String getMethod(){
			return method;
		}
		
		public int getTag(){
			return tag;
		}
	}

	public static class GetOrdersRequest{
		private final String method = "GetOrders";
		private final int tag = 301;

		public String getMethod(){
			return method;
		}
		
		public int getTag(){
			return tag;
		}
	}
	
	public static class PlaceOrderRequest{
		private final String method = "PlaceOrder";
		private final int tag = 302;
		private final int base;
		private final int counter;
		private final int quantity;
		private final int price;

		@SuppressWarnings("null")
		public PlaceOrderRequest(Order order){
			this.base = CoinfloorUtils.toCurrencyCode(order.getCurrencyPair().baseCurrency);
			this.counter = CoinfloorUtils.toCurrencyCode(order.getCurrencyPair().counterCurrency);
			
			if(order.getType().equals(OrderType.ASK)){this.quantity = (-1) * CoinfloorUtils.scaleToInt(order.getCurrencyPair().baseCurrency, order.getTradableAmount());}
			else {this.quantity = CoinfloorUtils.scaleToInt(order.getCurrencyPair().baseCurrency, order.getTradableAmount());}
			
			if(order instanceof LimitOrder){
				this.price = CoinfloorUtils.scalePriceToInt(((LimitOrder) order).getLimitPrice());
			}
			else{
				this.price = (Integer) null;
			}
		}
		
		public String getMethod(){
			return method;
		}

		public int getTag(){
			return tag;
		}
		
		public int getBase(){
			return base;
		}
		
		public int getCounter(){
			return counter;
		}
		
		public int getQuantity(){
			return quantity;
		}
		
		public int getPrice(){
			return price;
		}
	}
	
	public static class CancelOrderRequest{
		private final String method = "CancelOrder";
		private final int tag = 303;
		private final int id;
		
		public CancelOrderRequest(int id){
			this.id = id;
		}

		public String getMethod(){
			return method;
		}
		
		public int getTag(){
			return tag;
		}
		
		public int getId(){
			return id;
		}
	}
	
	public static class GetTradeVolumeRequest{
		private final String method = "GetTradeVolume";
		private final int tag;
		private final int asset;
		
		public GetTradeVolumeRequest(String currency){
			currency = currency.toUpperCase();
			this.asset = CoinfloorUtils.toCurrencyCode(currency);
			
			if(currency.equals("BTC")){tag = 102;}
			else{tag = 103;}
		}

		public String getMethod(){
			return method;
		}
		
		public int getTag(){
			return tag;
		}
		
		public int getAsset(){
			return asset;
		}
	}
	
	public static class EstimateMarketOrderRequest{
		private final String method = "EstimateMarketOrder";
		private final int tag = 304;
		private final int base;
		private final int counter;
		private final int quantity;

		public EstimateMarketOrderRequest(MarketOrder order){
			this.base = CoinfloorUtils.toCurrencyCode(order.getCurrencyPair().baseCurrency);
			this.counter = CoinfloorUtils.toCurrencyCode(order.getCurrencyPair().counterCurrency);
			
			if(order.getType().equals(OrderType.ASK)){
				this.quantity = (-1) * CoinfloorUtils.scaleToInt(order.getCurrencyPair().baseCurrency, order.getTradableAmount());
			}
			else {
				this.quantity = CoinfloorUtils.scaleToInt(order.getCurrencyPair().baseCurrency, order.getTradableAmount());
			}
			
		}
		
		public String getMethod(){
			return method;
		}

		public int getTag(){
			return tag;
		}
		
		public int getBase(){
			return base;
		}
		
		public int getCounter(){
			return counter;
		}
		
		public int getQuantity(){
			return quantity;
		}
	}
	
	private static class TickerRequest{
		private final String method = "WatchTicker";
		private final int tag = 202;
		private final int base;
		private final int counter;
		private final boolean watch;

		public TickerRequest(String base, String counter, boolean watch){
			this.base = CoinfloorUtils.toCurrencyCode(base);
			this.counter = CoinfloorUtils.toCurrencyCode(counter);
			this.watch = watch;
		}
		
		public String getMethod(){
			return method;
		}

		public int getTag(){
			return tag;
		}
		
		public int getBase(){
			return base;
		}
		
		public int getCounter(){
			return counter;
		}
		
		public boolean getWatch(){
			return watch;
		}
	}
	
	public static class WatchTickerRequest extends TickerRequest{
		public WatchTickerRequest(String base, String counter) {
			super(base, counter, true);
		}
	}
	
	public static class UnwatchTickerRequest extends TickerRequest{
		public UnwatchTickerRequest(String base, String counter) {
			super(base, counter, false);
		}
	}

	private static class OrdersRequest{
		private final String method = "WatchOrders";
		private final int tag = 201;
		private final int base;
		private final int counter;
		private final boolean watch;

		public OrdersRequest(String base, String counter, boolean watch){
			this.base = CoinfloorUtils.toCurrencyCode(base);
			this.counter = CoinfloorUtils.toCurrencyCode(counter);
			this.watch = watch;
		}
		
		public String getMethod(){
			return method;
		}

		public int getTag(){
			return tag;
		}
		
		public int getBase(){
			return base;
		}
		
		public int getCounter(){
			return counter;
		}
		
		public boolean getWatch(){
			return watch;
		}
	}
	public static class WatchOrdersRequest extends OrdersRequest{
		public WatchOrdersRequest(String base, String counter) {
			super(base, counter, true);
		}
	}
	
	public static class UnwatchOrdersRequest extends OrdersRequest{
		public UnwatchOrdersRequest(String base, String counter) {
			super(base, counter, false);
		}
	}
}
