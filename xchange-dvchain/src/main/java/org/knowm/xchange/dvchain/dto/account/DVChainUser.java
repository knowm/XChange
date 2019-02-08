package org.knowm.xchange.dvchain.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DVChainUser {
  private final String id;
  private final String firstName;
  private final String lastName;

  public DVChainUser(
      @JsonProperty("_id") String id,
      @JsonProperty("firstName") String firstName,
      @JsonProperty("lastName") String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DVChain User {id=");
    builder.append(id);
    builder.append(", firstName=");
    builder.append(firstName);
    builder.append(", lastName=");
    builder.append(lastName);
    builder.append("}");
    return builder.toString();
  }
}
