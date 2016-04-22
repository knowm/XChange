package org.knowm.xchange.btcchina.service.streaming;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.knowm.xchange.btcchina.BTCChinaAdapters;
import org.knowm.xchange.btcchina.dto.trade.streaming.BTCChinaBalance;
import org.knowm.xchange.btcchina.dto.trade.streaming.BTCChinaOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;

/**
 * Various adapters for converting from JSONObject to XChange DTOs.
 */
public class BTCChinaJSONObjectAdapters {

  public static Ticker adaptTicker(JSONObject jsonObject) {

    try {
      return internalAdaptTicker(jsonObject);
    } catch (JSONException e) {
      throw new IllegalArgumentException("data: " + jsonObject, e);
    }
  }

  private static Ticker internalAdaptTicker(JSONObject jsonObject) throws JSONException {

    JSONObject tickerJsonObject = jsonObject.getJSONObject("ticker");
    return new Ticker.Builder().high(new BigDecimal(tickerJsonObject.getString("high"))).low(new BigDecimal(tickerJsonObject.getString("low")))
        .bid(new BigDecimal(tickerJsonObject.getString("buy"))).ask(new BigDecimal(tickerJsonObject.getString("sell")))
        .last(new BigDecimal(tickerJsonObject.getString("last"))).volume(new BigDecimal(tickerJsonObject.getString("vol")))
        .timestamp(BTCChinaAdapters.adaptDate(tickerJsonObject.getLong("date")))
        .currencyPair(BTCChinaAdapters.adaptCurrencyPair(tickerJsonObject.getString("market"))).build();
  }

  public static Trade adaptTrade(JSONObject jsonObject) {

    try {
      return internalAdaptTrade(jsonObject);
    } catch (JSONException e) {
      throw new IllegalArgumentException("data: " + jsonObject, e);
    }
  }

  private static Trade internalAdaptTrade(JSONObject jsonObject) throws JSONException {

    return new Trade(BTCChinaAdapters.adaptOrderType(jsonObject.getString("type")), new BigDecimal(jsonObject.getString("amount")),
        BTCChinaAdapters.adaptCurrencyPair(jsonObject.getString("market")), new BigDecimal(jsonObject.getString("price")),
        BTCChinaAdapters.adaptDate(jsonObject.getLong("date")), String.valueOf(jsonObject.getLong("trade_id")));
  }

  public static BTCChinaOrder adaptOrder(JSONObject jsonObject) {

    try {
      return internalAdaptOrder(jsonObject);
    } catch (JSONException e) {
      throw new IllegalArgumentException("data: " + jsonObject, e);
    }
  }

  private static BTCChinaOrder internalAdaptOrder(JSONObject jsonObject) throws JSONException {

    JSONObject orderJsonObject = jsonObject.getJSONObject("order");
    return new BTCChinaOrder(BTCChinaAdapters.adaptOrderType(orderJsonObject.getString("type")), new BigDecimal(orderJsonObject.getString("amount")),
        BTCChinaAdapters.adaptCurrencyPair(orderJsonObject.getString("market")), String.valueOf(orderJsonObject.getLong("id")),
        BTCChinaAdapters.adaptDate(orderJsonObject.getLong("date")), new BigDecimal(orderJsonObject.getString("price")),
        new BigDecimal(orderJsonObject.getString("amount_original")), BTCChinaAdapters.adaptOrderStatus(orderJsonObject.getString("status")));
  }

  public static BTCChinaBalance adaptBalance(JSONObject jsonObject) {

    try {
      return internalAdaptBalance(jsonObject);
    } catch (JSONException e) {
      throw new IllegalArgumentException("data: " + jsonObject, e);
    }
  }

  private static BTCChinaBalance internalAdaptBalance(JSONObject jsonObject) throws JSONException {

    JSONObject balanceJsonObject = jsonObject.getJSONObject("balance");
    return new BTCChinaBalance(new BigDecimal(balanceJsonObject.getString("amount_integer")), new BigDecimal(balanceJsonObject.getString("amount")),
        balanceJsonObject.getString("symbol"), balanceJsonObject.getInt("amount_decimal"), balanceJsonObject.getString("currency"));
  }

  /**
   * Adapts the {@link JSONObject} to {@link OrderBook}.
   *
   * @param json the {@link JSONObject}.
   * @return the {@link OrderBook}.
   * @throws JSONException indicates read JSONObject failure.
   * @since <a href= "http://btcchina.org/websocket-api-market-data-documentation-en#websocket_api_v122" >WebSocket API v1.2.2</a>
   */
  public static OrderBook adaptDepth(JSONObject jsonObject) {

    try {
      return internalAdaptDepth(jsonObject);
    } catch (JSONException e) {
      throw new IllegalArgumentException("data: " + jsonObject, e);
    }
  }

  private static OrderBook internalAdaptDepth(JSONObject jsonObject) throws JSONException {

    JSONObject grouporder = jsonObject.getJSONObject("grouporder");

    String market = grouporder.getString("market");
    CurrencyPair currencyPair = BTCChinaAdapters.adaptCurrencyPair(market);

    JSONArray askArray = grouporder.getJSONArray("ask");
    JSONArray bidArray = grouporder.getJSONArray("bid");

    int askLength = askArray.length();
    int bidLength = bidArray.length();

    List<LimitOrder> asks = new ArrayList<LimitOrder>(askLength);
    List<LimitOrder> bids = new ArrayList<LimitOrder>(bidLength);

    for (int i = askLength - 1; i >= 0; i--) {
      JSONObject groupedOrder = askArray.getJSONObject(i);
      String price = groupedOrder.getString("price");
      // String type = groupedOrder.getString("type");
      String totalamount = groupedOrder.getString("totalamount");
      asks.add(
          new LimitOrder.Builder(OrderType.ASK, currencyPair).limitPrice(new BigDecimal(price)).tradableAmount(new BigDecimal(totalamount)).build());
    }

    for (int i = 0; i < bidLength; i++) {
      JSONObject groupedOrder = bidArray.getJSONObject(i);
      String price = groupedOrder.getString("price");
      // String type = groupedOrder.getString("type");
      String totalamount = groupedOrder.getString("totalamount");
      bids.add(
          new LimitOrder.Builder(OrderType.BID, currencyPair).limitPrice(new BigDecimal(price)).tradableAmount(new BigDecimal(totalamount)).build());
    }

    return new OrderBook(null, asks, bids);
  }

}
