package org.knowm.xchange.quadrigacx.dto.account;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import org.knowm.xchange.quadrigacx.dto.QuadrigaCxBaseResponse;
import org.knowm.xchange.quadrigacx.dto.account.QuadrigaCxDepositAddress.QuadrigaCxDepositAddressDeserializer;

@JsonDeserialize(using = QuadrigaCxDepositAddressDeserializer.class)
public class QuadrigaCxDepositAddress extends QuadrigaCxBaseResponse {

  private final String depositAddress;

  protected QuadrigaCxDepositAddress(String error, String depositAddress) {

    super(error);
    this.depositAddress = depositAddress;
  }

  public String getDepositAddress() {

    return depositAddress;
  }

  @Override
  public String toString() {

    return "QuadrigaCxDepositAddress [depositAddress=" + depositAddress + "]";
  }

  static class QuadrigaCxDepositAddressDeserializer
      extends JsonDeserializer<QuadrigaCxDepositAddress> {

    @Override
    public QuadrigaCxDepositAddress deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.get("error") != null) {
        return new QuadrigaCxDepositAddress(node.path("error").asText(), "");
      }
      return new QuadrigaCxDepositAddress(null, node.asText());
    }
  }
}
