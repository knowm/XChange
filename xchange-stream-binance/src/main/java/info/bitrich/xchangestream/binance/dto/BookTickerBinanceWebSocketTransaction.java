package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.marketdata.BinanceBookTicker;
import org.knowm.xchange.currency.CurrencyPair;

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
    ticker.setCurrencyPair(BinanceAdapters.adaptSymbol(symbol));
  }

  public CurrencyPair getCurrencyPair() {
    return ticker.getCurrencyPair();
  }

  public BinanceBookTicker getTicker() {
    return ticker;
  }
}
