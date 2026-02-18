package org.knowm.xchange.bitstamp.dto.account;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import org.knowm.xchange.bitstamp.dto.BitstampBaseResponse;
import org.knowm.xchange.bitstamp.dto.account.BitstampDepositAddress.BitstampDepositAddressDeserializer;

@JsonDeserialize(using = BitstampDepositAddressDeserializer.class)
public class BitstampDepositAddress extends BitstampBaseResponse {

  private final String depositAddress;
  private final String memoId;
  private final Long destinationTag;
  private final Long transferId;

  protected BitstampDepositAddress(String error, String depositAddress) {
    this(error, depositAddress, null, null, null);
  }

  protected BitstampDepositAddress(
      String error, String depositAddress, String memoId, Long destinationTag, Long transferId) {
    super(error);
    this.depositAddress = depositAddress;
    this.memoId = memoId;
    this.destinationTag = destinationTag;
    this.transferId = transferId;
  }

  public String getDepositAddress() {
    return depositAddress;
  }

  public String getMemoId() {
    return memoId;
  }

  public Long getDestinationTag() {
    return destinationTag;
  }

  public Long getTransferId() {
    return transferId;
  }

  @Override
  public String toString() {

    return "BitstampDepositAddress [depositAddress=" + depositAddress + "]";
  }

  static class BitstampDepositAddressDeserializer extends JsonDeserializer<BitstampDepositAddress> {

    @Override
    public BitstampDepositAddress deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.get("error") != null) {
        return new BitstampDepositAddress(node.path("error").asText(), "");
      } else if (node.get("address") != null) {
        String address = node.get("address").asText();
        String memoId = node.has("memo_id") ? node.get("memo_id").asText() : null;
        Long destinationTag =
            node.has("destination_tag") && !node.get("destination_tag").isNull()
                ? node.get("destination_tag").asLong()
                : null;
        Long transferId =
            node.has("transfer_id") && !node.get("transfer_id").isNull()
                ? node.get("transfer_id").asLong()
                : null;
        return new BitstampDepositAddress(null, address, memoId, destinationTag, transferId);
      } else {
        return new BitstampDepositAddress(null, node.asText());
      }
    }
  }
}
