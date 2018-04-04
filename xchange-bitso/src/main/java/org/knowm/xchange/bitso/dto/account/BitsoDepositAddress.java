package org.knowm.xchange.bitso.dto.account;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import org.knowm.xchange.bitso.dto.BitsoBaseResponse;
import org.knowm.xchange.bitso.dto.account.BitsoDepositAddress.BitsoDepositAddressDeserializer;

@JsonDeserialize(using = BitsoDepositAddressDeserializer.class)
public class BitsoDepositAddress extends BitsoBaseResponse {

  private final String depositAddress;

  protected BitsoDepositAddress(String error, String depositAddress) {

    super(error);
    this.depositAddress = depositAddress;
  }

  public String getDepositAddress() {

    return depositAddress;
  }

  @Override
  public String toString() {

    return "BitsoDepositAddress [depositAddress=" + depositAddress + "]";
  }

  static class BitsoDepositAddressDeserializer extends JsonDeserializer<BitsoDepositAddress> {

    @Override
    public BitsoDepositAddress deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.get("error") != null) {
        return new BitsoDepositAddress(node.path("error").asText(), "");
      }
      return new BitsoDepositAddress(null, node.asText());
    }
  }
}
