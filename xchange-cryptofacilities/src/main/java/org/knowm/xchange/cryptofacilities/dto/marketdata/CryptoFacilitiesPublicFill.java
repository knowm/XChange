package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Panchen */
public class CryptoFacilitiesPublicFill extends CryptoFacilitiesResult {

  private static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  private final Date time;
  private final String trade_id;
  private final BigDecimal price;
  private final BigDecimal size;
  private final String side;
  private final String type;

  public CryptoFacilitiesPublicFill(
      @JsonProperty("result") String result,
      @JsonProperty("error") String error,
      @JsonProperty("time") String strTime,
      @JsonProperty("trade_id") String trade_id,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("side") String side,
      @JsonProperty("type") String type)
      throws ParseException {

    super(result, error);

    this.time = strTime == null ? null : DATE_FORMAT.parse(strTime);
    this.trade_id = trade_id;
    this.price = price;
    this.size = size;
    this.side = side;
    this.type = type;
  }

  public Date getFillTime() {
    return time;
  }

  public String getFillId() {
    return trade_id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public String getSide() {
    return side;
  }

  public String getFillType() {
    return type;
  }

  @Override
  public String toString() {
    return "CryptoFacilitiesPublicFill [fillTime="
        + DATE_FORMAT.format(time)
        + ", trade_id="
        + trade_id
        + ", price="
        + price
        + ", size="
        + size
        + ", side="
        + side
        + ", fillType="
        + type
        + " ]";
  }
}
