package org.knowm.xchange.ftx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;

public class FtxMarketsDto {

  private final List<FtxMarketDto> marketList;

  @JsonCreator
  public FtxMarketsDto(List<FtxMarketDto> marketList) {
    this.marketList = marketList;
  }

  public List<FtxMarketDto> getMarketList() {
    return marketList;
  }

  @Override
  public String toString() {
    return "FtxMarketsDto{" + "marketList=" + marketList + '}';
  }
}
