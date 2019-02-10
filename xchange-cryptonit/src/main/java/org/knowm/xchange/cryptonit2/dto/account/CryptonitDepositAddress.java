package org.knowm.xchange.cryptonit2.dto.account;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import org.knowm.xchange.cryptonit2.dto.CryptonitBaseResponse;

@JsonDeserialize(using = CryptonitDepositAddress.CryptonitDepositAddressDeserializer.class)
public class CryptonitDepositAddress extends CryptonitBaseResponse {

  private final String depositAddress;

  protected CryptonitDepositAddress(String error, String depositAddress) {

    super(error);
    this.depositAddress = depositAddress;
  }

  public String getDepositAddress() {

    return depositAddress;
  }

  @Override
  public String toString() {

    return "CryptonitDepositAddress [depositAddress=" + depositAddress + "]";
  }

  static class CryptonitDepositAddressDeserializer
      extends JsonDeserializer<CryptonitDepositAddress> {

    @Override
    public CryptonitDepositAddress deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.get("error") != null) {
        return new CryptonitDepositAddress(node.path("error").asText(), "");
      } else if (node.get("address") != null) {
        return new CryptonitDepositAddress(null, node.get("address").asText());
      } else {
        return new CryptonitDepositAddress(null, node.asText());
      }
    }
  }
}
