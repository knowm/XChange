package info.bitrich.xchangestream.poloniex2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexTicker;

/** Created by Lukas Zaoralek on 11.11.17. */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class PoloniexWebSocketTickerTransaction {
  public String channelId;
  public String timestamp;
  public String[] ticker;

  public PoloniexTicker toPoloniexTicker(CurrencyPair currencyPair) {
    PoloniexMarketData poloniexMarketData = new PoloniexMarketData();
    BigDecimal last = new BigDecimal(ticker[1]);
    BigDecimal lowestAsk = new BigDecimal(ticker[2]);
    BigDecimal highestBid = new BigDecimal(ticker[3]);
    BigDecimal percentChange = new BigDecimal(ticker[4]);
    BigDecimal baseVolume = new BigDecimal(ticker[5]);
    BigDecimal quoteVolume = new BigDecimal(ticker[6]);
    BigDecimal isFrozen = new BigDecimal(ticker[7]);
    BigDecimal high24hr = new BigDecimal(ticker[8]);
    BigDecimal low24hr = new BigDecimal(ticker[9]);
    poloniexMarketData.setLast(last);
    poloniexMarketData.setLowestAsk(lowestAsk);
    poloniexMarketData.setHighestBid(highestBid);
    poloniexMarketData.setPercentChange(percentChange);
    poloniexMarketData.setBaseVolume(baseVolume);
    poloniexMarketData.setQuoteVolume(quoteVolume);
    poloniexMarketData.setHigh24hr(high24hr);
    poloniexMarketData.setLow24hr(low24hr);
    return new PoloniexTicker(poloniexMarketData, currencyPair);
  }

  public int getPairId() {
    return Integer.parseInt(ticker[0]);
  }
}
