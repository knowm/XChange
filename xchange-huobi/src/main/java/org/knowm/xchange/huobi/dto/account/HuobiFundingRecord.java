package org.knowm.xchange.huobi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.dto.account.FundingRecord;

public class HuobiFundingRecord {

  private final long id;
  private final FundingRecord.Type type;
  private final String currency;
  private final String txhash;
  private final BigDecimal amount;
  private final String address;
  private final String addressTag;
  private final BigDecimal fee;
  private final String state;
  private final Date createdAt;
  private final Date updatedAt;

  public HuobiFundingRecord(
      @JsonProperty("id") long id,
      @JsonProperty("type") String type,
      @JsonProperty("currency") String currency,
      @JsonProperty("tx-hash") String txhash,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("address") String address,
      @JsonProperty("address-tag") String addressTag,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("state") String state,
      @JsonProperty("created-at") Date created,
      @JsonProperty("updated-at") Date updated) {
    this.id = id;
    // type must be 'deposit' or 'withdraw'.
    // Don't use FundingRecord.Type.fromString as it expects 'withdrawal' and not 'withdraw'
    this.type =
        "deposit".equals(type.toLowerCase())
            ? FundingRecord.Type.DEPOSIT
            : FundingRecord.Type.WITHDRAWAL;
    this.currency = currency;
    this.txhash = txhash;
    this.amount = amount;
    this.address = address;
    this.addressTag = addressTag;
    this.fee = fee;
    this.state = state;
    this.createdAt = created;
    this.updatedAt = updated;
  }

  public long getBalance() {
    return id;
  }

  public String getCurrency() {
    return currency;
  }

  public FundingRecord.Type getType() {
    return type;
  }

  public long getId() {
    return id;
  }

  public String getTxhash() {
    return txhash;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getAddress() {
    return address;
  }

  public String getAddressTag() {
    return addressTag;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public String getState() {
    return state;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public String toString() {
    return String.format(
        "[id = %s, currency = %s, type = %s, tx-hash = %s, amount = %s, address = %s, address-tag = %s, fee = %s, state = %s, created-at = %s, updated-at = %s]",
        id,
        currency,
        type,
        txhash,
        amount.toString(),
        address,
        addressTag,
        fee.toString(),
        state,
        createdAt,
        updatedAt);
  }
}
