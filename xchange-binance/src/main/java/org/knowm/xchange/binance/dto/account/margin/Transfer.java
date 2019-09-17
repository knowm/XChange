package org.knowm.xchange.binance.dto.account.margin;

import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;

public class Transfer {

  private BigDecimal amount;
  private Currency asset;
  private TransferStatus status;
  private Long timestamp;
  private Long txId;
  private HistoryTransferType type;

  public Transfer(
      BigDecimal amount, String asset, String status, Long timestamp, Long txId, String type) {
    this.amount = amount;
    this.asset = Currency.getInstance(asset);
    this.status = TransferStatus.valueOf(status);
    this.timestamp = timestamp;
    this.txId = txId;
    this.type = HistoryTransferType.valueOf(type);
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Currency getAsset() {
    return asset;
  }

  public void setAsset(Currency asset) {
    this.asset = asset;
  }

  public TransferStatus getStatus() {
    return status;
  }

  public void setStatus(TransferStatus status) {
    this.status = status;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public Long getTxId() {
    return txId;
  }

  public void setTxId(Long txId) {
    this.txId = txId;
  }

  public HistoryTransferType getType() {
    return type;
  }

  public void setType(HistoryTransferType type) {
    this.type = type;
  }
}
