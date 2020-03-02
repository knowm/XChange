package info.bitrich.xchangestream.poloniex2.dto;

import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trade;

/** @author Nikita Belenkiy on 04/11/2019. */
public class PoloniexWebSocketAdapter {
  private PoloniexWebSocketAdapter() {}

  public static Trade convertPoloniexWebSocketTradeEventToTrade(
      PoloniexWebSocketTradeEvent poloniexTradeEvent, CurrencyPair currencyPair) {
    TradeEvent tradeEvent = poloniexTradeEvent.getTradeEvent();
    Date timestamp = new Date(tradeEvent.getTimestampSeconds() * 1000L);
    Trade trade =
        new Trade.Builder()
            .type(tradeEvent.getType())
            .price(tradeEvent.getPrice())
            .originalAmount(tradeEvent.getSize())
            .currencyPair(currencyPair)
            .id(tradeEvent.getTradeId())
            .timestamp(timestamp)
            .build();
    return trade;
  }
}
