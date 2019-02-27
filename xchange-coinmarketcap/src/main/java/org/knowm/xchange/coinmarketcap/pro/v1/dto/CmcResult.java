package org.knowm.xchange.coinmarketcap.pro.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcStatus;

/**
 * Generic result value structure for every API endpoint. See also:
 * https://coinmarketcap.com/api/documentation/v1/#section/Standards-and-Conventions
 */
public class CmcResult<V> {

  /** "data" contains result of the queried endpoint */
  private final V data;

  /** "status" is always present and contains status info about the returned call */
  private final CmcStatus status;

  public CmcResult(@JsonProperty("data") V data, @JsonProperty("status") CmcStatus status) {

    this.data = data;
    this.status = status;
  }

  public boolean isSuccess() {
    return status.getErrorCode() == 0;
  }

  public V getData() {
    return data;
  }

  public CmcStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "CmcResult{" + "data=" + data + ", status=" + status + '}';
  }
}
