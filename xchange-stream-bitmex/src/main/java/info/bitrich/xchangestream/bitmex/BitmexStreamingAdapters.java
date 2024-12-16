package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.bitmex.dto.BitmexTicker;
import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

@UtilityClass
public class BitmexStreamingAdapters {

  public final Map<String, CurrencyPair> SYMBOL_TO_INSTRUMENT = new HashMap<>();
  private final Map<CurrencyPair, String> INSTRUMENT_TO_SYMBOL = new HashMap<>();

  public void putSymbolMapping(String symbol, CurrencyPair instrument) {
    SYMBOL_TO_INSTRUMENT.put(symbol, instrument);
    INSTRUMENT_TO_SYMBOL.put(instrument, symbol);
  }

  public String toString(CurrencyPair instrument) {
    if (instrument == null) {
      return null;
    }
    return INSTRUMENT_TO_SYMBOL.get(instrument);
  }

  public CurrencyPair toInstrument(String bitmexSymbol) {
    if (bitmexSymbol == null) {
      return null;
    }
    return SYMBOL_TO_INSTRUMENT.get(bitmexSymbol);
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

}
