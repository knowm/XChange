package org.knowm.xchange.gatehub.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Payment extends BaseResponse {
  private String accountUuid;

  private String sendingAddress;

  private String address;

  private Network network;

  private BigDecimal amount;

  private Date createdAt;

  private String name;

  private String uuid;

  private String vaultUuid;

  private Wallet sendingWallet;

  private Wallet receivingWallet;

  private Integer type;

  protected Payment() { }

  protected Payment(@JsonProperty("error_id") String errorId) {
    super(errorId);
  }

  private Payment(
      String sendingAddress, BigDecimal amount, String vaultUuid, String destAccountUuid,
      Network network, Integer type
  ) {
    this.accountUuid = destAccountUuid;
    this.sendingAddress = sendingAddress;
    this.amount = amount;
    this.network = network;
    this.vaultUuid = vaultUuid;
    this.type = type;
  }

  public static Payment request(
      String sendingAddress, BigDecimal amount,
      String vaultUuid, Network network,
      String destAccountUuid
  ) {
    return new Payment(sendingAddress, amount, vaultUuid, destAccountUuid, network, 0); // todo: what is type?
  }

  public String getAccountUuid() {
    return accountUuid;
  }

  public String getSendingAddress() {
    return sendingAddress;
  }

  public String getAddress() {
    return address;
  }

  public Network getNetwork() {
    return network;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public String getName() {
    return name;
  }

  public String getUuid() {
    return uuid;
  }

  public String getVaultUuid() {
    return vaultUuid;
  }

  public Wallet getSendingWallet() {
    return sendingWallet;
  }

  public Wallet getReceivingWallet() {
    return receivingWallet;
  }

  public Integer getType() {
    return type;
  }
}
