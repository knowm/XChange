package org.knowm.xchange.cryptofacilities.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.cryptofacilities.Util;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

public class BatchOrderResult extends CryptoFacilitiesResult {

  private final Date serverTime;
  private final List<BatchStatus> batchStatus;

  public BatchOrderResult(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error,
      @JsonProperty("batchStatus") List<BatchStatus> batchStatus)
      throws ParseException {
    super(result, error);
    this.serverTime = Util.parseDate(strServerTime);
    this.batchStatus = batchStatus;
  }

  public List<BatchStatus> getBatchStatus() {
    return batchStatus;
  }

  @Override
  public String toString() {

    if (isSuccess() && serverTime != null) {
      String res =
          "CryptoFacilitiesOrder [result="
              + this.getResult()
              + ", serverTime="
              + serverTime
              + ", batchStatus="
              + batchStatus
              + "]";
      return res;
    } else return super.toString();
  }
}
