package org.knowm.xchange.bitcoinaverage.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/** Data object representing List of Tickers from BitcoinAverage */
public final class BitcoinAverageTickers {

  private Map<String, BitcoinAverageTicker> tickers = new HashMap<>();
  private Date timestamp;

  // Could alternatively add setters, but since these are mandatory
  public BitcoinAverageTickers(@JsonProperty("timestamp") String timestamp) {

    try {
      // Parse the timestamp into a Date object
      this.timestamp =
          new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.getDefault()).parse(timestamp);
    } catch (Exception e) {
      this.timestamp = null;
    }
  }

  @JsonAnySetter
  public void setTickers(String name, BitcoinAverageTicker ticker) {

    this.tickers.put(name, ticker);
  }

  public Map<String, BitcoinAverageTicker> getTickers() {

    return tickers;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "BitcoinAverageTicker [tickers=" + tickers + ", timestamp=" + timestamp + "]";
  }
}
