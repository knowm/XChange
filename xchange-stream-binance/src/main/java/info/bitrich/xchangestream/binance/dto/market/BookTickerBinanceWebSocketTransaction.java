package info.bitrich.xchangestream.binance.dto.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import org.knowm.xchange.binance.dto.marketdata.BinanceBookTicker;

@Getter
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
  }
}
