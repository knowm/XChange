package org.knowm.xchange.cryptocom.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Each entry in {@code bids}/{@code asks} is {@code [price, quantity, numberOfOrders]}. */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoComOrderBookData {

  @JsonProperty("bids")
  private List<List<String>> bids;

  @JsonProperty("asks")
  private List<List<String>> asks;

  @JsonProperty("t")
  private Long timestamp;
}
