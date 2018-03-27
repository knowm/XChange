package org.knowm.xchange.itbit.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class ItBitDepositResponse {

  private final String id;
  private final String walletId;
  private final String depositAddress;
  private final Map<String, String> metadata;

  public ItBitDepositResponse(
      @JsonProperty("id") String id,
      @JsonProperty("walletID") String walletId,
      @JsonProperty("depositAddress") String depositAddress,
      @JsonProperty("metadata") Map<String, String> metadata) {

    this.id = id;
    this.walletId = walletId;
    this.depositAddress = depositAddress;
    this.metadata = metadata;
  }

  public String getId() {

    return id;
  }

  public String getWalletId() {

    return walletId;
  }

  public String getDepositAddress() {

    return depositAddress;
  }

  public Map<String, String> getMetadata() {

    return metadata;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ItBitDepositResponse [id=");
    builder.append(id);
    builder.append(", walletID=");
    builder.append(walletId);
    builder.append(", depositAddress=");
    builder.append(depositAddress);
    builder.append(", metadata=");
    builder.append(metadata.toString());
    builder.append("]");
    return builder.toString();
  }
}
