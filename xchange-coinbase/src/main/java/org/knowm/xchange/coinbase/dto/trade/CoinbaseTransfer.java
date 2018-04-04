package org.knowm.xchange.coinbase.dto.trade;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.coinbase.dto.CoinbaseBaseResponse;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import org.knowm.xchange.coinbase.dto.serialization.CoinbaseCentsDeserializer;
import org.knowm.xchange.coinbase.dto.serialization.CoinbaseMoneyDeserializer;
import org.knowm.xchange.coinbase.dto.trade.CoinbaseTransfer.CoinbaseTransferDeserializer;
import org.knowm.xchange.utils.DateUtils;

/** @author jamespedwards42 */
@JsonDeserialize(using = CoinbaseTransferDeserializer.class)
public class CoinbaseTransfer extends CoinbaseBaseResponse {

  private final String id;
  private final CoinbaseTransferType type;
  private final String fundingType;
  private final String code;
  private final Date createdAt;
  private final CoinbaseMoney coinbaseFee;
  private final CoinbaseMoney bankFee;
  private final Date payoutDate;
  private final String transactionId;
  private final CoinbaseTransferStatus status;
  private final CoinbaseMoney btcAmount;
  private final CoinbaseMoney subtotal;
  private final CoinbaseMoney total;
  private final String description;

  public CoinbaseTransfer(
      String id,
      final CoinbaseTransferType type,
      final String fundingType,
      final String code,
      final Date createdAt,
      final CoinbaseMoney coinbaseFee,
      final CoinbaseMoney bankFee,
      final Date payoutDate,
      final String transactionId,
      final CoinbaseTransferStatus status,
      final CoinbaseMoney btcAmount,
      final CoinbaseMoney subtotal,
      final CoinbaseMoney total,
      final String description,
      final boolean success,
      final List<String> errors) {

    super(success, errors);
    this.id = id;
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

  public String getId() {

    return id;
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

  public CoinbaseMoney getCoinbaseFee() {

    return coinbaseFee;
  }

  public Date getCreatedAt() {

    return createdAt;
  }

  public CoinbaseMoney getBankFee() {

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

  public CoinbaseMoney getBtcAmount() {

    return btcAmount;
  }

  public CoinbaseMoney getSubtotal() {

    return subtotal;
  }

  public CoinbaseMoney getTotal() {

    return total;
  }

  public String getDescription() {

    return description;
  }

  @Override
  public String toString() {

    return "CoinbaseTransfer [id="
        + id
        + ", type="
        + type
        + ", fundingType="
        + fundingType
        + ", code="
        + code
        + ", createdAt="
        + createdAt
        + ", coinbaseFee="
        + coinbaseFee
        + ", bankFee="
        + bankFee
        + ", payoutDate="
        + payoutDate
        + ", transactionId="
        + transactionId
        + ", status="
        + status
        + ", btcAmount="
        + btcAmount
        + ", subtotal="
        + subtotal
        + ", total="
        + total
        + ", description="
        + description
        + "]";
  }

  public enum CoinbaseTransferStatus {
    PENDING,
    COMPLETED,
    CANCELED,
    REVERSED
  }

  static class CoinbaseTransferDeserializer extends JsonDeserializer<CoinbaseTransfer> {

    @Override
    public CoinbaseTransfer deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode node = oc.readTree(jp);

      final JsonNode successNode = node.path("success");
      boolean success = true;
      final List<String> errors = new ArrayList<>();
      if (successNode.isBoolean()) {
        success = successNode.asBoolean();
        final JsonNode errorsNode = node.path("errors");
        if (errorsNode.isArray())
          for (JsonNode errorNode : errorsNode) errors.add(errorNode.asText());
      }

      final JsonNode transferNode = node.path("transfer");
      final String id = transferNode.path("id").asText();
      final String fundingType = transferNode.path("_type").asText();
      final CoinbaseTransferType type =
          CoinbaseTransferType.valueOf(transferNode.path("type").asText().toUpperCase());
      final String code = transferNode.path("code").asText();
      final Date createdAt =
          DateUtils.fromISO8601DateString(transferNode.path("created_at").asText());
      final JsonNode feesNode = transferNode.path("fees");
      final CoinbaseMoney coinbaseFee =
          CoinbaseCentsDeserializer.getCoinbaseMoneyFromCents(feesNode.path("coinbase"));
      final CoinbaseMoney bankFee =
          CoinbaseCentsDeserializer.getCoinbaseMoneyFromCents(feesNode.path("bank"));
      final Date payoutDate =
          DateUtils.fromISO8601DateString(transferNode.path("payout_date").asText());
      final String transactionId = transferNode.path("transaction_id").asText();
      final CoinbaseTransferStatus status =
          CoinbaseTransferStatus.valueOf(transferNode.path("status").asText().toUpperCase());
      final CoinbaseMoney btcAmount =
          CoinbaseMoneyDeserializer.getCoinbaseMoneyFromNode(transferNode.path("btc"));
      final CoinbaseMoney subtotal =
          CoinbaseMoneyDeserializer.getCoinbaseMoneyFromNode(transferNode.path("subtotal"));
      final CoinbaseMoney total =
          CoinbaseMoneyDeserializer.getCoinbaseMoneyFromNode(transferNode.path("total"));
      final String description = transferNode.path("description").asText();

      return new CoinbaseTransfer(
          id,
          type,
          fundingType,
          code,
          createdAt,
          coinbaseFee,
          bankFee,
          payoutDate,
          transactionId,
          status,
          btcAmount,
          subtotal,
          total,
          description,
          success,
          errors);
    }
  }
}
