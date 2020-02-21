package org.knowm.xchange.gatehub.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account extends BaseResponse {
  private String address;

  private String name;

  private String uuid;

  private Date createdAt;

  private Network network;

  private Account(Network network, String address) {
    this.network = network;
    this.address = address;
  }

  protected Account(@JsonProperty("error_id") String errorId) {
    super(errorId);
  }

  public static Account createQuery(Network network, String address) {
    return new Account(network, address);
  }

  public String getAddress() {
    return address;
  }

  public String getName() {
    return name;
  }

  public String getUuid() {
    return uuid;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Network getNetwork() {
    return network;
  }
}
