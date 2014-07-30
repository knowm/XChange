package com.xeiam.xchange.bitstamp.dto.account;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.bitstamp.dto.BitstampBaseResponse;
import com.xeiam.xchange.bitstamp.dto.account.BitstampDepositAddress.BitstampDepositAddressDeserializer;

@JsonDeserialize(using = BitstampDepositAddressDeserializer.class)
public class BitstampDepositAddress extends BitstampBaseResponse {

  private final String depositAddress;

  protected BitstampDepositAddress(String error, String depositAddress) {

    super(error);
    this.depositAddress = depositAddress;
  }

  public String getDepositAddress() {

    return depositAddress;
  }

  @Override
  public String toString() {

    return "BitstampDepositAddress [depositAddress=" + depositAddress + "]";
  }

  static class BitstampDepositAddressDeserializer extends JsonDeserializer<BitstampDepositAddress> {

    @Override
    public BitstampDepositAddress deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.get("error") != null) {
        return new BitstampDepositAddress(node.path("error").asText(), "");
      }
      return new BitstampDepositAddress(null, node.asText());
    }
  }
}
