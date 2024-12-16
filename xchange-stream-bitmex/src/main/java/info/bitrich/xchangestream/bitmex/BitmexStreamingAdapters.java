package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.bitmex.dto.BitmexTicker;
import info.bitrich.xchangestream.bitmex.dto.BitmexTrade;
import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

@UtilityClass
public class BitmexStreamingAdapters {

  public final Map<String, CurrencyPair> SYMBOL_TO_CURRENCY_PAIR = new HashMap<>();
  private final Map<CurrencyPair, String> CURRENCY_PAIR_TO_SYMBOL = new HashMap<>();

  public void putSymbolMapping(String symbol, CurrencyPair instrument) {
    SYMBOL_TO_CURRENCY_PAIR.put(symbol, instrument);
    CURRENCY_PAIR_TO_SYMBOL.put(instrument, symbol);
  }

  public String toString(CurrencyPair instrument) {
    if (instrument == null) {
      return null;
    }
    return CURRENCY_PAIR_TO_SYMBOL.get(instrument);
  }

  public CurrencyPair toCurrencyPair(String bitmexSymbol) {
    if (bitmexSymbol == null) {
      return null;
    }
    return SYMBOL_TO_CURRENCY_PAIR.get(bitmexSymbol);
  }


  public Ticker toTicker(BitmexTicker bitmexTicker) {
    return new Ticker.Builder()
        .ask(bitmexTicker.getAskPrice())
        .askSize(bitmexTicker.getAskSize())
        .bid(bitmexTicker.getBidPrice())
        .bidSize(bitmexTicker.getBidSize())
        .timestamp(BitmexAdapters.toDate(bitmexTicker.getTimestamp()))
        .instrument(bitmexTicker.getCurrencyPair())
        .build();
  }

  public Trade toTrade(BitmexTrade bitmexTrade) {
    return new Trade.Builder()
        .type(bitmexTrade.getSide())
        .originalAmount(bitmexTrade.getAssetAmount())
        .instrument(bitmexTrade.getCurrencyPair())
        .price(bitmexTrade.getPrice())
        .timestamp(BitmexAdapters.toDate(bitmexTrade.getTimestamp()))
        .id(bitmexTrade.getId())
        .build();
  }

}
