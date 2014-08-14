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
import com.xeiam.xchange.bitstamp.dto.account.BitstampBooleanResponse.BitstampBooleanResponseDeserializer;

/**
 * @author gnandiga
 */
@JsonDeserialize(using = BitstampBooleanResponseDeserializer.class)
public class BitstampBooleanResponse extends BitstampBaseResponse {

  private final boolean response;

  /**
   * Constructor
   * 
   * @param error
   * @param response
   */
  public BitstampBooleanResponse(String error, boolean response) {

    super(error);
    this.response = response;
  }

  public boolean getResponse() {

    return response;
  }

  @Override
  public String toString() {

    return "BitstampWithdrawResponse [withdrawResult=" + response + "]";
  }

  static class BitstampBooleanResponseDeserializer extends JsonDeserializer<BitstampBooleanResponse> {

    @Override
    public BitstampBooleanResponse deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.isBoolean()) {
        return new BitstampBooleanResponse(null, node.asBoolean());
      }
      else if (node.get("error") != null) {
        return new BitstampBooleanResponse(node.path("error").asText(), false);
      }
      return new BitstampBooleanResponse("Invalid response from Bitstamp Server.", false);
    }
  }

}
