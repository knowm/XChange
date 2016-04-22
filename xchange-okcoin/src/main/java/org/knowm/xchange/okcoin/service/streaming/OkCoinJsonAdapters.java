package org.knowm.xchange.okcoin.service.streaming;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.okcoin.OkCoinUtils;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinStreamingTicker;

public class OkCoinJsonAdapters {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  public static Ticker adaptTicker(OkCoinStreamingTicker ticker, CurrencyPair currencyPair) {
    return new Ticker.Builder().currencyPair(currencyPair).high(ticker.getHigh()).low(ticker.getLow()).bid(ticker.getBuy()).ask(ticker.getSell())
        .last(ticker.getLast()).volume(new BigDecimal(ticker.getVol().replaceAll(",", ""))).timestamp(new Date(ticker.getTimestamp())).build();
  }

  public static Trade adaptTrade(JsonNode trade, CurrencyPair currencyPair) {
    String id = trade.get(0).asText();
    double price = trade.get(1).asDouble();
    double amount = trade.get(2).asDouble();
    String dateString = (trade.get(3).asText());
    OrderType orderType = trade.get(4).asText().equals("bid") ? OrderType.BID : OrderType.ASK;

    Date tradeDate = null;
    try {

      tradeDate = dateFormat.parse(dateString);
      //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

      // dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Hong Kong"));
      // dateFormat.setTimeZone("Asia/Hong Kong");

      tradeDate.setTime(OkCoinUtils.toEpoch(tradeDate, "VST"));
    } catch (ParseException e) {
      tradeDate = null;
    }

    return new Trade(orderType, BigDecimal.valueOf(amount), currencyPair, BigDecimal.valueOf(price), tradeDate, id);
  }

}
