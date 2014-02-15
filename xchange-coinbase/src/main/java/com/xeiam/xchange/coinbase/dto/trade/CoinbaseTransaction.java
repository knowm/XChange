package com.xeiam.xchange.coinbase.dto.trade;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.EnumFromStringHelper;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransaction.CoinbaseTransactionStatus.CoinbaseTransactionStatusDeserializer;

public class CoinbaseTransaction {

  private final CoinbaseTransactionInfo transaction;

  public CoinbaseTransaction(@JsonProperty("transaction") final CoinbaseTransactionInfo transaction) {

    this.transaction = transaction;
  }

  public String getId() {

    return transaction.id;
  }

  public Date getCreatedAt() {

    return transaction.createdAt;
  }

  public CoinbaseAmount getAmount() {

    return transaction.amount;
  }

  public boolean getRequest() {

    return transaction.request;
  }

  public CoinbaseTransactionStatus getStatus() {

    return transaction.status;
  }

  public CoinbaseUser getSender() {

    return transaction.sender;
  }

  public CoinbaseUser getRecipient() {

    return transaction.recipient;
  }

  public String getRecipientAddress() {

    return transaction.recipientAddress;
  }

  public String getNotes() {

    return transaction.notes;
  }

  public String getHsh() {
    
    return transaction.hsh;
  }
  
  @Override
  public String toString() {

    return "CoinbaseTransaction [transaction=" + transaction + "]";
  }

  @JsonDeserialize(using = CoinbaseTransactionStatusDeserializer.class)
  public enum CoinbaseTransactionStatus {

    PENDING, COMPLETE;

    static class CoinbaseTransactionStatusDeserializer extends JsonDeserializer<CoinbaseTransactionStatus> {

      private static final EnumFromStringHelper<CoinbaseTransactionStatus> FROM_STRING_HELPER = new EnumFromStringHelper<CoinbaseTransactionStatus>(CoinbaseTransactionStatus.class);

      @Override
      public CoinbaseTransactionStatus deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        String jsonString = node.textValue();
        return FROM_STRING_HELPER.fromJsonString(jsonString);
      }
    }
  }

  private static class CoinbaseTransactionInfo {

    private final String id;
    private final Date createdAt;
    private final CoinbaseAmount amount;
    private final boolean request;
    private final CoinbaseTransactionStatus status;
    private final CoinbaseUser sender;
    private final CoinbaseUser recipient;
    private final String recipientAddress;
    private final String notes;
    private final String hsh;
    
    private CoinbaseTransactionInfo(@JsonProperty("id") final String id, @JsonProperty("created_at") final Date createdAt, @JsonProperty("amount") final CoinbaseAmount amount,
        @JsonProperty("request") final boolean request, @JsonProperty("status") final CoinbaseTransactionStatus status, @JsonProperty("sender") final CoinbaseUser sender,
        @JsonProperty("recipient") final CoinbaseUser recipient, @JsonProperty("recipient_address") final String recipientAddress, @JsonProperty("notes") final String notes,
        @JsonProperty("hsh") final String hsh) {

      this.id = id;
      this.createdAt = createdAt;
      this.amount = amount;
      this.request = request;
      this.status = status;
      this.sender = sender;
      this.recipient = recipient;
      this.recipientAddress = recipientAddress;
      this.notes = notes;
      this.hsh = hsh;
    }

    @Override
    public String toString() {

      return "CoinbaseTransactionInfo [id=" + id + ", createdAt=" + createdAt + ", amount=" + amount + ", request=" + request + ", status=" + status + ", sender=" + sender + ", recipient="
          + recipient + ", recipientAddress=" + recipientAddress + ", notes=" + notes + ", hsh=" + hsh + "]";
    }
  }
}
