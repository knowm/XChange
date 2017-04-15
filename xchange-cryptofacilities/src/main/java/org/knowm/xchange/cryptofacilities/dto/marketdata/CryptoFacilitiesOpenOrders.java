package org.knowm.xchange.cryptofacilities.dto.marketdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesOpenOrders extends CryptoFacilitiesResult {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  private final Date serverTime;
  private final List<CryptoFacilitiesOpenOrder> orders;

  public CryptoFacilitiesOpenOrders(@JsonProperty("result") String result, @JsonProperty("error") String error,
      @JsonProperty("serverTime") String strServerTime, @JsonProperty("openorders") List<CryptoFacilitiesOpenOrder> orders) throws ParseException {

    super(result, error);

    this.serverTime = strServerTime == null ? null : DATE_FORMAT.parse(strServerTime);
    this.orders = orders;
  }

  public List<CryptoFacilitiesOpenOrder> getOrders() {
    return orders;
  }

  @Override
  public String toString() {

    if (isSuccess()) {
      StringBuilder res = new StringBuilder("CryptoFacilitiesOpenOrders [orders=");
      for (CryptoFacilitiesOpenOrder ord : orders)
        res.append(ord.toString()).append(", ");
      res.append(" ]");

      return res.toString();
    } else {
      return super.toString();
    }

  }

}
