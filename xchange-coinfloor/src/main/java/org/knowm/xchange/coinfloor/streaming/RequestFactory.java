package org.knowm.xchange.coinfloor.streaming;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinfloor.CoinfloorUtils;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;

/**
 * <p>
 * Class that parses user inputs, and does all the generation of requests
 * </p>
 * 
 * @author obsessiveOrange
 */
public class RequestFactory {

  private static AtomicInteger tagCounter = new AtomicInteger(1);

  public static abstract class CoinfloorRequest {

    public abstract int getTag();
  }

  public static class CoinfloorAuthenticationRequest extends CoinfloorRequest {

    final String method = "Authenticate";
    final int tag = 001 + (tagCounter.getAndIncrement() << 10);

    @JsonProperty("user_id")
    final long userId;
    final String cookie;
    final byte[] nonce;
    final String serverNonce;

    List<String> signature;

    public CoinfloorAuthenticationRequest(long userId, String cookie, String password, String serverNonce) {

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

  public static class GetBalancesRequest extends CoinfloorRequest {

    private final String method = "GetBalances";
    private final int tag = 101 + (tagCounter.getAndIncrement() << 10);

    public String getMethod() {

      return method;
    }

    public int getTag() {

      return tag;
    }
  }

  public static class GetOrdersRequest extends CoinfloorRequest {

    private final String method = "GetOrders";
    private final int tag = 301 + (tagCounter.getAndIncrement() << 10);

    public String getMethod() {

      return method;
    }

    public int getTag() {

      return tag;
    }
  }

  public static class PlaceOrderRequest extends CoinfloorRequest {

    private final String method = "PlaceOrder";
    private final int tag = 302 + (tagCounter.getAndIncrement() << 10);
    private final int base;
    private final int counter;
    private final int quantity;
    private final int price;

    @SuppressWarnings("null")
    public PlaceOrderRequest(Order order) {

      this.base = CoinfloorUtils.toCurrencyCode(order.getCurrencyPair().base.getCurrencyCode());
      this.counter = CoinfloorUtils.toCurrencyCode(order.getCurrencyPair().counter.getCurrencyCode());

      if (order.getType().equals(OrderType.ASK)) {
        this.quantity = (-1) * CoinfloorUtils.scaleToInt(order.getCurrencyPair().base.getCurrencyCode(), order.getTradableAmount());
      } else {
        this.quantity = CoinfloorUtils.scaleToInt(order.getCurrencyPair().base.getCurrencyCode(), order.getTradableAmount());
      }

      if (order instanceof LimitOrder) {
        this.price = CoinfloorUtils.scalePriceToInt(order.getCurrencyPair().base.getCurrencyCode(), order.getCurrencyPair().counter.getCurrencyCode(),
            ((LimitOrder) order).getLimitPrice());
      } else {
        this.price = (Integer) null;
      }
    }

    public String getMethod() {

      return method;
    }

    public int getTag() {

      return tag;
    }

    public int getBase() {

      return base;
    }

    public int getCounter() {

      return counter;
    }

    public int getQuantity() {

      return quantity;
    }

    public int getPrice() {

      return price;
    }
  }

  public static class CancelOrderRequest extends CoinfloorRequest {

    private final String method = "CancelOrder";
    private final int tag = 303 + (tagCounter.getAndIncrement() << 10);
    private final int id;

    public CancelOrderRequest(int id) {

      this.id = id;
    }

    public String getMethod() {

      return method;
    }

    public int getTag() {

      return tag;
    }

    public int getId() {

      return id;
    }
  }

  public static class GetTradeVolumeRequest extends CoinfloorRequest {

    private final String method = "GetTradeVolume";
    private final int tag;
    private final int asset;

    public GetTradeVolumeRequest(String currency) {

      currency = currency.toUpperCase();
      this.asset = CoinfloorUtils.toCurrencyCode(currency);

      if (currency.equals("BTC")) {
        tag = 102 + (tagCounter.getAndIncrement() << 10);
      } else {
        tag = 103 + (tagCounter.getAndIncrement() << 10);
      }
    }

    public String getMethod() {

      return method;
    }

    public int getTag() {

      return tag;
    }

    public int getAsset() {

      return asset;
    }
  }

  public static class EstimateMarketOrderRequest extends CoinfloorRequest {

    private final String method = "EstimateMarketOrder";
    private final int tag = 304 + (tagCounter.getAndIncrement() << 10);
    private final int base;
    private final int counter;
    private final int quantity;

    public EstimateMarketOrderRequest(MarketOrder order) {

      this.base = CoinfloorUtils.toCurrencyCode(order.getCurrencyPair().base.getCurrencyCode());
      this.counter = CoinfloorUtils.toCurrencyCode(order.getCurrencyPair().counter.getCurrencyCode());

      if (order.getType().equals(OrderType.ASK)) {
        this.quantity = (-1) * CoinfloorUtils.scaleToInt(order.getCurrencyPair().base.getCurrencyCode(), order.getTradableAmount());
      } else {
        this.quantity = CoinfloorUtils.scaleToInt(order.getCurrencyPair().base.getCurrencyCode(), order.getTradableAmount());
      }

    }

    public String getMethod() {

      return method;
    }

    public int getTag() {

      return tag;
    }

    public int getBase() {

      return base;
    }

    public int getCounter() {

      return counter;
    }

    public int getQuantity() {

      return quantity;
    }
  }

  private static class TickerRequest extends CoinfloorRequest {

    private final String method = "WatchTicker";
    private final int tag = 202 + (tagCounter.getAndIncrement() << 10);
    private final int base;
    private final int counter;
    private final boolean watch;

    public TickerRequest(String base, String counter, boolean watch) {

      this.base = CoinfloorUtils.toCurrencyCode(base);
      this.counter = CoinfloorUtils.toCurrencyCode(counter);
      this.watch = watch;
    }

    public String getMethod() {

      return method;
    }

    public int getTag() {

      return tag;
    }

    public int getBase() {

      return base;
    }

    public int getCounter() {

      return counter;
    }

    public boolean getWatch() {

      return watch;
    }
  }

  public static class WatchTickerRequest extends TickerRequest {

    public WatchTickerRequest(String base, String counter) {

      super(base, counter, true);
    }
  }

  public static class UnwatchTickerRequest extends TickerRequest {

    public UnwatchTickerRequest(String base, String counter) {

      super(base, counter, false);
    }
  }

  private static class OrdersRequest extends CoinfloorRequest {

    private final String method = "WatchOrders";
    private final int tag = 201 + (tagCounter.getAndIncrement() << 10);
    private final int base;
    private final int counter;
    private final boolean watch;

    public OrdersRequest(String base, String counter, boolean watch) {

      this.base = CoinfloorUtils.toCurrencyCode(base);
      this.counter = CoinfloorUtils.toCurrencyCode(counter);
      this.watch = watch;
    }

    public String getMethod() {

      return method;
    }

    public int getTag() {

      return tag;
    }

    public int getBase() {

      return base;
    }

    public int getCounter() {

      return counter;
    }

    public boolean getWatch() {

      return watch;
    }
  }

  public static class WatchOrdersRequest extends OrdersRequest {

    public WatchOrdersRequest(String base, String counter) {

      super(base, counter, true);
    }
  }

  public static class UnwatchOrdersRequest extends OrdersRequest {

    public UnwatchOrdersRequest(String base, String counter) {

      super(base, counter, false);
    }
  }
}
