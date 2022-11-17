package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.marketdata.BinanceBookTicker;
import org.knowm.xchange.instrument.Instrument;

public class BookTickerBinanceWebSocketTransaction extends BaseBinanceWebSocketTransaction {

  private final BinanceBookTicker ticker;

  public BookTickerBinanceWebSocketTransaction(
      @JsonProperty("u") long updateId,
      @JsonProperty("s") String symbol,
      @JsonProperty("b") BigDecimal bidPrice,
      @JsonProperty("B") BigDecimal bidQty,
      @JsonProperty("a") BigDecimal askPrice,
      @JsonProperty("A") BigDecimal askQty) {
    super(BinanceWebSocketTypes.BOOK_TICKER, new Date());
    ticker = new BinanceBookTicker(bidPrice, bidQty, askPrice, askQty, symbol);
    ticker.setUpdateId(updateId);
    ticker.setInstrument(BinanceAdapters.adaptSymbol(symbol));
  }

  public Instrument getInstrument() {
    return ticker.getInstrument();
  }

  public BinanceBookTicker getTicker() {
    return ticker;
  }
}
