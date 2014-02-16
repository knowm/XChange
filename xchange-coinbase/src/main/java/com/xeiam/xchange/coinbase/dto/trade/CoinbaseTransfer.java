package com.xeiam.xchange.coinbase.dto.trade;

import java.io.IOException;
import java.util.Date;

import org.joda.money.BigMoney;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.CoinbaseCentsDeserializer;
import com.xeiam.xchange.coinbase.dto.CoinbaseMoneyDeserializer;
import com.xeiam.xchange.coinbase.dto.CoinbaseTransferType;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfer.CoinbaseTransferDeserializer;
import com.xeiam.xchange.utils.DateUtils;

@JsonDeserialize(using = CoinbaseTransferDeserializer.class)
public class CoinbaseTransfer {

  private final CoinbaseTransferType type;
  private final String fundingType;
  private final String code;
  private final Date createdAt;
  private final BigMoney coinbaseFee;
  private final BigMoney bankFee;
  private final Date payoutDate;
  private final String transactionId;
  private final CoinbaseTransferStatus status;
  private final BigMoney btcAmount;
  private final BigMoney subtotal;
  private final BigMoney total;
  private final String description;

  public CoinbaseTransfer(final CoinbaseTransferType type, final String fundingType, final String code, final Date createdAt, final BigMoney coinbaseFee, final BigMoney bankFee,
      final Date payoutDate, final String transactionId, final CoinbaseTransferStatus status, final BigMoney btcAmount, final BigMoney subtotal, final BigMoney total, final String description) {

    this.type = type;
    this.fundingType = fundingType;
    this.code = code;
    this.createdAt = createdAt;
    this.coinbaseFee = coinbaseFee;
    this.bankFee = bankFee;
    this.payoutDate = payoutDate;
    this.transactionId = transactionId;
    this.status = status;
    this.btcAmount = btcAmount;
    this.subtotal = subtotal;
    this.total = total;
    this.description = description;
  }

  public CoinbaseTransferType getType() {

    return type;
  }

  public String getFundingType() {

    return fundingType;
  }

  public String getCode() {

    return code;
  }

  public BigMoney getCoinbaseFee() {

    return coinbaseFee;
  }

  public Date getCreatedAt() {

    return createdAt;
  }

  public BigMoney getBankFee() {

    return bankFee;
  }

  public Date getPayoutDate() {

    return payoutDate;
  }

  public String getTransactionId() {

    return transactionId;
  }

  public CoinbaseTransferStatus getStatus() {

    return status;
  }

  public BigMoney getBtcAmount() {

    return btcAmount;
  }

  public BigMoney getSubtotal() {

    return subtotal;
  }

  public BigMoney getTotal() {

    return total;
  }

  public String getDescription() {

    return description;
  }

  @Override
  public String toString() {

    return "CoinbaseTransfer [type=" + type + ", fundingType=" + fundingType + ", code=" + code + ", createdAt=" + createdAt + ", coinbaseFee=" + coinbaseFee + ", bankFee=" + bankFee
        + ", payoutDate=" + payoutDate + ", transactionId=" + transactionId + ", status=" + status + ", btcAmount=" + btcAmount + ", subtotal=" + subtotal + ", total=" + total + ", description="
        + description + "]";
  }

  public enum CoinbaseTransferStatus {

    PENDING, COMPLETED, CANCELED, REVERSED;
  }

  static class CoinbaseTransferDeserializer extends JsonDeserializer<CoinbaseTransfer> {

    @Override
    public CoinbaseTransfer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode node = oc.readTree(jp);
      final JsonNode transferNode = node.path("transfer");
      final String fundingType = transferNode.path("_type").asText();
      final CoinbaseTransferType type = CoinbaseTransferType.valueOf(transferNode.path("type").asText().toUpperCase());
      final String code = transferNode.path("code").asText();
      final Date createdAt = DateUtils.fromISO8601DateString(transferNode.path("created_at").asText());
      final JsonNode feesNode = transferNode.path("fees");
      final BigMoney coinbaseFee = CoinbaseCentsDeserializer.getBigMoneyFromCents(feesNode.path("coinbase"));
      final BigMoney bankFee = CoinbaseCentsDeserializer.getBigMoneyFromCents(feesNode.path("bank"));
      final Date payoutDate = DateUtils.fromISO8601DateString(transferNode.path("payout_date").asText());
      final String transactionId = transferNode.path("transaction_id").asText();
      final CoinbaseTransferStatus status = CoinbaseTransferStatus.valueOf(transferNode.path("status").asText().toUpperCase());
      final BigMoney btcAmount = CoinbaseMoneyDeserializer.getBigMoneyFromNode(transferNode.path("btc"));
      final BigMoney subtotal = CoinbaseMoneyDeserializer.getBigMoneyFromNode(transferNode.path("subtotal"));
      final BigMoney total = CoinbaseMoneyDeserializer.getBigMoneyFromNode(transferNode.path("total"));
      final String description = transferNode.path("description").asText();

      return new CoinbaseTransfer(type, fundingType, code, createdAt, coinbaseFee, bankFee, payoutDate, transactionId, status, btcAmount, subtotal, total, description);
    }
  }
}
