package com.xeiam.xchange.btcchina.service.streaming;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.btcchina.dto.trade.streaming.BTCChinaBalance;
import com.xeiam.xchange.btcchina.dto.trade.streaming.BTCChinaOrder;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;

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
    return TickerBuilder.newInstance()
        .withHigh(new BigDecimal(tickerJsonObject.getString("high")))
        .withLow(new BigDecimal(tickerJsonObject.getString("low")))
        .withBid(new BigDecimal(tickerJsonObject.getString("buy")))
        .withAsk(new BigDecimal(tickerJsonObject.getString("sell")))
        .withLast(new BigDecimal(tickerJsonObject.getString("last")))
        .withVolume(new BigDecimal(tickerJsonObject.getString("vol")))
        .withTimestamp(BTCChinaAdapters.adaptDate(tickerJsonObject.getLong("date")))
        .withCurrencyPair(BTCChinaAdapters.adaptCurrencyPair(tickerJsonObject.getString("market")))
        .build();
  }

  public static Trade adaptTrade(JSONObject jsonObject) {

    try {
      return internalAdaptTrade(jsonObject);
    } catch (JSONException e) {
      throw new IllegalArgumentException("data: " + jsonObject, e);
    }
  }

  private static Trade internalAdaptTrade(JSONObject jsonObject) throws JSONException {

    return new Trade(BTCChinaAdapters.adaptOrderType(jsonObject.getString("type")), new BigDecimal(jsonObject.getString("amount")), BTCChinaAdapters.adaptCurrencyPair(jsonObject.getString("market")),
        new BigDecimal(jsonObject.getString("price")), BTCChinaAdapters.adaptDate(jsonObject.getLong("date")), String.valueOf(jsonObject.getLong("trade_id")));
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
        BTCChinaAdapters.adaptCurrencyPair(orderJsonObject.getString("market")), String.valueOf(orderJsonObject.getLong("id")), BTCChinaAdapters.adaptDate(orderJsonObject.getLong("date")),
        new BigDecimal(orderJsonObject.getString("price")), new BigDecimal(orderJsonObject.getString("amount_original")), BTCChinaAdapters.adaptOrderStatus(orderJsonObject.getString("status")));
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
    return new BTCChinaBalance(new BigDecimal(balanceJsonObject.getString("amount_integer")), new BigDecimal(balanceJsonObject.getString("amount")), balanceJsonObject.getString("symbol"),
        balanceJsonObject.getInt("amount_decimal"), balanceJsonObject.getString("currency"));
  }

}
