package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Panchen */
public class CryptoFacilitiesOpenPosition extends CryptoFacilitiesResult {

  private static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  private final Date fillTime;
  private final String symbol;
  private final String side;
  private final BigDecimal size;
  private final BigDecimal price;

  public CryptoFacilitiesOpenPosition(
      @JsonProperty("result") String result,
      @JsonProperty("error") String error,
      @JsonProperty("fillTime") String strfillTime,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") String side,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("price") BigDecimal price)
      throws ParseException {

    super(result, error);

    this.fillTime = strfillTime == null ? null : DATE_FORMAT.parse(strfillTime);
    this.symbol = symbol;
    this.side = side;
    this.size = size;
    this.price = price;
  }

  public String getSymbol() {
    return symbol;
  }

  public Date getFillTime() {
    return fillTime;
  }

  public String getSide() {
    return side;
  }

  public BigDecimal getSize() {
    return size;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "CryptoFacilitiesOpenPosition [fillTime="
        + DATE_FORMAT.format(fillTime)
        + ", symbol="
        + symbol
        + ", side="
        + side
        + ", size="
        + size
        + ", price="
        + price
        + " ]";
  }
}
