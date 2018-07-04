package org.knowm.xchange.btcmarkets.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.utils.jackson.MillisecTimestampDeserializer;
import org.knowm.xchange.utils.jackson.SatoshiToBtc;

public class BTCMarketsFundtransfer {

  public static class CryptoPaymentDetail {
    private String txId;
    private String address;

    public CryptoPaymentDetail(
        @JsonProperty("txId") String txId, @JsonProperty("address") String address) {
      this.txId = txId;
      this.address = address;
    }

    public String getTxId() {
      return txId;
    }

    public void setTxId(String txId) {
      this.txId = txId;
    }

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    @Override
    public String toString() {
      return String.format("CryptoPaymentDetail{txId=%s, address=%s}", txId, address);
    }
  }

  private String status;

  @JsonDeserialize(using = MillisecTimestampDeserializer.class)
  private Date lastUpdate;

  @JsonDeserialize(using = SatoshiToBtc.class)
  private BigDecimal fee;

  private String description;
  private String errorMessage;

  @JsonDeserialize(using = MillisecTimestampDeserializer.class)
  private Date creationTime;

  private Long fundTransferId;

  private CryptoPaymentDetail cryptoPaymentDetail;
  private String currency;

  @JsonDeserialize(using = SatoshiToBtc.class)
  private BigDecimal amount;

  private String transferType;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public void setFee(BigDecimal fee) {
    this.fee = fee;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public Long getFundTransferId() {
    return fundTransferId;
  }

  public void setFundTransferId(Long fundTransferId) {
    this.fundTransferId = fundTransferId;
  }

  public CryptoPaymentDetail getCryptoPaymentDetail() {
    return cryptoPaymentDetail;
  }

  public void setCryptoPaymentDetail(CryptoPaymentDetail cryptoPaymentDetail) {
    this.cryptoPaymentDetail = cryptoPaymentDetail;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getTransferType() {
    return transferType;
  }

  public void setTransferType(String transferType) {
    this.transferType = transferType;
  }

  public BTCMarketsFundtransfer(
      @JsonProperty("status") String status,
      @JsonProperty("lastUpdate") Date lastUpdate,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("description") String description,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("creationTime") Date creationTime,
      @JsonProperty("fundTransferId") Long fundTransferId,
      @JsonProperty("cryptoPaymentDetail") CryptoPaymentDetail cryptoPaymentDetail,
      @JsonProperty("currency") String currency,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("transferType") String transferType) {
    this.status = status;
    this.lastUpdate = lastUpdate;
    this.fee = fee;
    this.description = description;
    this.errorMessage = errorMessage;
    this.creationTime = creationTime;
    this.fundTransferId = fundTransferId;
    this.cryptoPaymentDetail = cryptoPaymentDetail;
    this.currency = currency;
    this.amount = amount;
    this.transferType = transferType;
  }

  @Override
  public String toString() {
    return String.format(
        "BTCMarketsFundtransfer{status=%s, lastUpdate=%s, fee=%s, description=%s, "
            + "errorMessage=%s, creationTime=%s, fundTransferId=%s, cryptoPaymentDetail=%s, currency=%s"
            + "amount=%s, transferType=%s",
        status,
        lastUpdate,
        fee,
        description,
        errorMessage,
        creationTime,
        fundTransferId,
        cryptoPaymentDetail,
        currency,
        amount,
        transferType);
  }
}
