package org.knowm.xchange.bittrex.dto.marketdata;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexChartDataResponse {

  private final boolean success;
  private final String message;
  private final ArrayList<BittrexChartData> chartData;

  public BittrexChartDataResponse(@JsonProperty("success") boolean success, @JsonProperty("message") String message,
      @JsonProperty("result") ArrayList<BittrexChartData> result) {
    this.success = success;
    this.message = message;
    this.chartData = result;
  }

  public boolean getSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public ArrayList<BittrexChartData> getChartData() {
    return chartData;
  }

  @Override
  public String toString() {
    return "BittrexChartDataResponse [success=" + success + ", message=" + message + ", chartData=" + chartData + "]";
  }
}
