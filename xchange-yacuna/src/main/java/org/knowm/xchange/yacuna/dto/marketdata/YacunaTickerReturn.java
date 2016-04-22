package org.knowm.xchange.yacuna.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yingzhe on 12/23/2014.
 */
@JsonIgnoreProperties({ "pagingInfo", "requestId" })
public class YacunaTickerReturn {

  private final List<YacunaTicker> tickerList;

  private final String status;

  public YacunaTickerReturn(@JsonProperty("markets") List<YacunaTicker> tickerList, @JsonProperty("status") String status) {

    this.tickerList = tickerList;
    this.status = status;
  }

  public List<YacunaTicker> getTickerList() {

    return this.tickerList;
  }

  public String getStatus() {

    return this.status;
  }

  @Override
  public String toString() {

    return String.format("YacunaTickerReturn[status: %s, YacunaTicker: %s]", status,
        tickerList == null || tickerList.size() == 0 ? "null" : tickerList.get(0));
  }
}
