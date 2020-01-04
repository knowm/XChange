package org.knowm.xchange.globitex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class GlobitexTickers implements Serializable {

  @JsonProperty("instruments")
  private final List<GlobitexTicker> globitexTickerList;

  public GlobitexTickers(@JsonProperty("instruments") List<GlobitexTicker> globitexTickerList) {
    this.globitexTickerList = globitexTickerList;
  }

  public List<GlobitexTicker> getGlobitexTickerList() {
    return globitexTickerList;
  }

  @Override
  public String toString() {
    return "GlobitexTickers{" + "globitexTickerList=" + globitexTickerList + '}';
  }
}
