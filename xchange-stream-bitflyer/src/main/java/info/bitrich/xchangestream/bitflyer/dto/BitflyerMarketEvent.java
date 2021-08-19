package info.bitrich.xchangestream.bitflyer.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Created by Lukas Zaoralek on 15.11.17. */
public abstract class BitflyerMarketEvent {
  protected final String timestamp;

  BitflyerMarketEvent(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public Date getDate() {
    SimpleDateFormat formatter;
    formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    Date date = null;
    try {
      date = formatter.parse(timestamp.substring(0, 23));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }
}
