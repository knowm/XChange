package org.knowm.xchange.upbit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpbitMarket {

  private String market;

  private String koreanName;
  private String englishName;

  public UpbitMarket(
      @JsonProperty("market") String market,
      @JsonProperty("korean_name") String koreanName,
      @JsonProperty("english_name") String englishName) {
    this.market = market;
    this.koreanName = koreanName;
    this.englishName = englishName;
  }

  public String getMarket() {
    return market;
  }

  public String getKoreanName() {
    return koreanName;
  }

  public String getEnglishName() {
    return englishName;
  }
}
