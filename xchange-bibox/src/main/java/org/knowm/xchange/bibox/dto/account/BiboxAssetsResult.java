package org.knowm.xchange.bibox.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

public class BiboxAssetsResult {

  @JsonProperty("total_btc")
  @Getter
  private String total_btc;

  @JsonProperty("total_cny")
  @Getter
  private String total_cny;

  @JsonProperty("total_usd")
  @Getter
  private String total_usd;

  @JsonProperty("assets_list")
  @Getter
  private List<BiboxAsset> assets_list;
}
