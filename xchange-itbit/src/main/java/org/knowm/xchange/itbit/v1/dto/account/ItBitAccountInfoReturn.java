package org.knowm.xchange.itbit.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

public class ItBitAccountInfoReturn {

  private final String id;
  private final String userId;
  private final String name;
  private final ItBitAccountBalance[] balances;

  public ItBitAccountInfoReturn(
      @JsonProperty("id") String id,
      @JsonProperty("userId") String userId,
      @JsonProperty("name") String name,
      @JsonProperty("balances") ItBitAccountBalance[] balances) {

    this.id = id;
    this.userId = userId;
    this.name = name;
    this.balances = balances;
  }

  public String getId() {

    return id;
  }

  public String getUserId() {

    return userId;
  }

  public String getName() {

    return name;
  }

  public ItBitAccountBalance[] getBalances() {

    return balances;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("ItBitAccountInfoReturn [id=");
    builder.append(id);
    builder.append(", userId=");
    builder.append(userId);
    builder.append(", name=");
    builder.append(name);
    builder.append(", balances=");
    builder.append(Arrays.toString(balances));
    builder.append("]");
    return builder.toString();
  }
}
