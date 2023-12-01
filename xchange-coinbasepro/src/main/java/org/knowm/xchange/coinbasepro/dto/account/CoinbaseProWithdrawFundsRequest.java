package org.knowm.xchange.coinbasepro.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinbaseProWithdrawFundsRequest {
  public final @JsonProperty("amount") BigDecimal amount;
  public final @JsonProperty("currency") String currency;
  public final @JsonProperty("crypto_address") String address;
  public final @JsonProperty("destination_tag") String destinationTag;

  /**
   * A boolean flag to opt out of using a destination tag for currencies that support one. This is
   * required when not providing a destination tag.
   */
  public final @JsonProperty("no_destination_tag") boolean noDestinationTag;

  public CoinbaseProWithdrawFundsRequest(
      BigDecimal amount,
      String currency,
      String address,
      String destinationTag,
      boolean noDestinationTag) {
    this.amount = amount;
    this.currency = currency;
    this.address = address;
    this.destinationTag = destinationTag;
    this.noDestinationTag = noDestinationTag;
  }
}
