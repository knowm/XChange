package org.knowm.xchange.coinbase.dto.merchant;

// NOTE: Order of imports matters. put fasterxml ones before xchange. Fails when compiling with Maven for some reason.

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.knowm.xchange.coinbase.dto.CoinbaseBaseResponse;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseButton.CoinbaseButtonInfo;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseOrder.CoinbaseOrderStatus.CoinbaseOrderStatusDeserializer;
import org.knowm.xchange.coinbase.dto.serialization.CoinbaseCentsDeserializer;
import org.knowm.xchange.coinbase.dto.serialization.EnumFromStringHelper;
import org.knowm.xchange.coinbase.dto.serialization.EnumLowercaseJsonSerializer;
import org.knowm.xchange.utils.jackson.ISO8601DateDeserializer;

/**
 * @author jamespedwards42
 */
public class CoinbaseOrder extends CoinbaseBaseResponse {

  @JsonProperty("order")
  private CoinbaseOrderInfo order;

  private CoinbaseOrder(@JsonProperty("order") final CoinbaseOrderInfo order, @JsonProperty("success") final boolean success,
      @JsonProperty("errors") final List<String> errors) {

    super(success, errors);
    this.order = order;
  }

  public String getId() {

    return order.getId();
  }

  public Date getCreatedAt() {

    return order.getCreatedAt();
  }

  public CoinbaseOrderStatus getStatus() {

    return order.getStatus();
  }

  public CoinbaseMoney getTotalBTC() {

    return order.getTotalBTC();
  }

  public CoinbaseMoney getTotalNative() {

    return order.getTotalNative();
  }

  public String getCustom() {

    return order.getCustom();
  }

  public String getReceiveAddress() {

    return order.getReceiveAddress();
  }

  public CoinbaseButton getButton() {

    return order.getButton();
  }

  public CoinbaseOrderTransaction getTransaction() {

    return order.getTransaction();
  }

  @Override
  public String toString() {

    return "CoinbaseOrder [order=" + order + "]";
  }

  @JsonDeserialize(using = CoinbaseOrderStatusDeserializer.class)
  @JsonSerialize(using = EnumLowercaseJsonSerializer.class)
  public enum CoinbaseOrderStatus {

    NEW, COMPLETED, CANCELED;

    static class CoinbaseOrderStatusDeserializer extends JsonDeserializer<CoinbaseOrderStatus> {

      private static final EnumFromStringHelper<CoinbaseOrderStatus> FROM_STRING_HELPER = new EnumFromStringHelper<>(CoinbaseOrderStatus.class);

      @Override
      public CoinbaseOrderStatus deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        String jsonString = node.textValue();
        return FROM_STRING_HELPER.fromJsonString(jsonString);
      }
    }
  }

  private static class CoinbaseOrderInfo {

    private final String id;
    private final Date createdAt;
    private final CoinbaseOrderStatus status;
    private final CoinbaseMoney totalBTC;
    private final CoinbaseMoney totalNative;
    private final String custom;
    private final String receiveAddress;
    private final CoinbaseButton button;
    private final CoinbaseOrderTransaction transaction;

    private CoinbaseOrderInfo(@JsonProperty("id") final String id,
        @JsonProperty("created_at") @JsonDeserialize(using = ISO8601DateDeserializer.class) final Date createdAt,
        @JsonProperty("status") final CoinbaseOrderStatus status,
        @JsonProperty("total_btc") @JsonDeserialize(using = CoinbaseCentsDeserializer.class) final CoinbaseMoney totalBTC,
        @JsonProperty("total_native") @JsonDeserialize(using = CoinbaseCentsDeserializer.class) final CoinbaseMoney totalNative,
        @JsonProperty("custom") final String custom, @JsonProperty("receive_address") final String receiveAddress,
        @JsonProperty("button") final CoinbaseButtonInfo button, @JsonProperty("transaction") final CoinbaseOrderTransaction transaction) {

      this.id = id;
      this.createdAt = createdAt;
      this.status = status;
      this.totalBTC = totalBTC;
      this.totalNative = totalNative;
      this.custom = custom;
      this.receiveAddress = receiveAddress;
      this.button = new CoinbaseButton(button);
      this.transaction = transaction;
    }

    public String getId() {

      return id;
    }

    public Date getCreatedAt() {

      return createdAt;
    }

    public CoinbaseOrderStatus getStatus() {

      return status;
    }

    public CoinbaseMoney getTotalBTC() {

      return totalBTC;
    }

    public CoinbaseMoney getTotalNative() {

      return totalNative;
    }

    public String getCustom() {

      return custom;
    }

    public String getReceiveAddress() {

      return receiveAddress;
    }

    public CoinbaseButton getButton() {

      return button;
    }

    public CoinbaseOrderTransaction getTransaction() {

      return transaction;
    }

    @Override
    public String toString() {

      return "CoinbaseOrderInfo [id=" + id + ", createdAt=" + createdAt + ", status=" + status + ", totalBTC=" + totalBTC + ", totalNative="
          + totalNative + ", custom=" + custom + ", receiveAddress=" + receiveAddress + ", button=" + button + ", transaction=" + transaction + "]";
    }

  }

  public static class CoinbaseOrderTransaction {

    private final String id;
    private final String hash;
    private final int confirmations;

    private CoinbaseOrderTransaction(@JsonProperty("id") final String id, @JsonProperty("hash") final String hash,
        @JsonProperty("confirmations") final int confirmations) {

      this.id = id;
      this.hash = hash;
      this.confirmations = confirmations;
    }

    public String getId() {

      return id;
    }

    public String getHash() {

      return hash;
    }

    public int getConfirmations() {

      return confirmations;
    }

    @Override
    public String toString() {

      return "CoinbaseOrderTransaction [id=" + id + ", hash=" + hash + ", confirmations=" + confirmations + "]";
    }
  }
}
